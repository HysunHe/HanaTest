/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csvreader.CsvWriter;
import com.oracle.oda.ext.constants.Constants;
import com.oracle.oda.ext.domains.Blacklist;
import com.oracle.oda.ext.domains.Category;
import com.oracle.oda.ext.domains.Config;
import com.oracle.oda.ext.domains.File;
import com.oracle.oda.ext.domains.Question;
import com.oracle.oda.ext.domains.Topic;
import com.oracle.oda.ext.dto.JsonResponse;
import com.oracle.oda.ext.dto.TopicTO;
import com.oracle.oda.ext.services.BlacklistService;
import com.oracle.oda.ext.services.CategoryService;
import com.oracle.oda.ext.services.ConfigService;
import com.oracle.oda.ext.services.FileService;
import com.oracle.oda.ext.services.QuestionService;
import com.oracle.oda.ext.services.TopicService;
import com.oracle.oda.ext.utils.DateUtil;
import com.oracle.oda.ext.utils.StringUtil;
import com.oracle.oda.ext.utils.XlsUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.controllers
 * 
 *  File Name       : BotGatewayController.java
 * 
 *  Creation Date   : 2019年2月19日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@RestController
@RequestMapping("/bot")
public class BotGatewayController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BotGatewayController.class);

	@Autowired
	private ConfigService config;

	@RequestMapping(value = "/listSettings", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listSettings(@RequestParam("kind") String kind) {
		LOGGER.info("*** Got listSettings request: " + kind);
		List<Config> list = config.listByKind(kind);
		return JsonResponse.inst("OK", HttpStatus.CREATED, list).toResponseEntity();
	}

	@RequestMapping(value = "/getProperty", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> getProperty(@RequestParam("kind") String kind,
			@RequestParam("key") String key) {
		LOGGER.info("*** Got getProperty request: " + kind + "|" + key);
		Config o = config.getProperty(kind, key);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@RequestMapping(value = { "/settings" }, method = RequestMethod.PUT)
	public ResponseEntity<JsonResponse> replaceSettings(@RequestBody Map<String, String> settings) {
		LOGGER.info("*** Got replaceSettings request: " + settings);
		List<Config> list = new ArrayList<>();
		for (Map.Entry<String, String> item : settings.entrySet()) {
			LOGGER.info("--- " + item.getKey() + " = " + item.getValue());
			list.add(new Config(null, item.getKey(), Constants.CONFIG_TYPE_SYSTEM, item.getValue()));
		}
		config.replaceSystemSettings(list);
		return JsonResponse.inst("OK", HttpStatus.OK, settings).toResponseEntity();
	}

	@RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> getSystemSettings() { // Verified
		LOGGER.info("*** Got getSystemSettings request");
		List<Config> list = config.listByKind(Constants.CONFIG_TYPE_SYSTEM);
		Map<String, String> map = new HashMap<>();
		for (Config item : list) {
			map.put(item.getDisplay(), item.getValue());
		}
		return JsonResponse.inst("OK", HttpStatus.OK, map).toResponseEntity();
	}

	@Autowired
	private BlacklistService blacklist;

	@RequestMapping(value = "/blacklist", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> saveBlacklistItem(@RequestBody Blacklist o) { // Verified
		LOGGER.info("*** Got block request: " + o);
		if (StringUtil.isBlank(o.getUser_id()) || StringUtil.isBlank(o.getUser_agent())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		blacklist.save(o);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@RequestMapping(value = "/blacklist", method = RequestMethod.GET) // Verified
	public ResponseEntity<JsonResponse> listBlacklist(
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit) {
		LOGGER.info("*** Got listBlacklist request:(offset|limit) " + offset + "|" + limit);
		List<Blacklist> list = blacklist.listBlacklist(offset, limit);
		LOGGER.info("*** Number of blacklist returned: " + list.size());
		return JsonResponse.inst("OK", HttpStatus.OK, list).toResponseEntity();
	}

	@RequestMapping(value = "/blacklist/unblock", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> unblock(@RequestBody Blacklist o) { // Verified
		LOGGER.info("*** Got unblock request: " + o);
		if (StringUtil.isBlank(o.getUser_id())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		blacklist.unblock(o.getUser_id());
		return JsonResponse.inst("OK", HttpStatus.NO_CONTENT).toResponseEntity();
	}

	@RequestMapping(value = "/blacklist/check", method = RequestMethod.POST)
	public ResponseEntity<?> checkBlacklist(@RequestBody Blacklist o) { // Verified
		LOGGER.info("*** Got checkBlacklist request: " + o);
		if (StringUtil.isBlank(o.getUser_id())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		Blacklist oe = blacklist.get(o.getUser_id());
		if (oe == null) {
			return JsonResponse.inst("OK", HttpStatus.OK).toResponseText();
		}
		Config conf = config.getProperty(Constants.CONFIG_TYPE_SYSTEM, "恶意用户每次禁言多少分钟");
		int diff = DateUtil.getTimeDiffMins(DateUtil.now(), oe.getUpdated_at());
		if (diff > Integer.valueOf(conf.getValue()).intValue()) {
			return JsonResponse.inst("OK", HttpStatus.OK).toResponseText();
		} else {
			return JsonResponse.inst("BLOCKED", HttpStatus.OK).toResponseText();
		}
	}

	@RequestMapping(value = { "/download/blacklist", "/download/blacklist.xls",
			"/download/blacklist.xlsx" }, method = RequestMethod.GET)
	public ResponseEntity<?> downloadBlacklist(@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to) {
		LOGGER.info("*** Got request: downloadBlacklist(from|to): " + from + "|" + to);
		if (StringUtil.isBlank(from)) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required parameters").toResponseEntity();
		}
		if (StringUtil.isBlank(to)) {
			to = DateUtil.date2String(DateUtil.now(), "yyyy-MM-dd");
		}

		Date dtFrom = DateUtil.string2Date(from, "yyyy-MM-dd");
		Date dtTo = DateUtil.string2Date(to, "yyyy-MM-dd");
		List<Blacklist> list = blacklist.queryByTime(dtFrom, DateUtil.getTomorrow(dtTo));

		final String[] header = { "用户ID", "用户浏览器标识", "禁言开始时间" };
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Blacklist");
			XlsUtil.writeTitlesToExcel(workbook, sheet, header);

			int rowNum = 1;
			for (Blacklist item : list) {
				int colNum = 0;
				Row row = XlsUtil.createRow(sheet, rowNum++);
				XlsUtil.strCell(row, colNum++).setCellValue(item.getUser_id());
				XlsUtil.strCell(row, colNum++).setCellValue(item.getUser_agent());
				XlsUtil.strCell(row, colNum++)
						.setCellValue(DateUtil.date2String(item.getCreated_at(), "yyyy-MM-dd HH:mm:ss"));
			}
			XlsUtil.autoSizeColumns(sheet, header.length);
			workbook.write(bos);
			ByteArrayResource res = new ByteArrayResource(bos.toByteArray());
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"blacklist.xls\"").body(res);
		} catch (Exception e) {
			LOGGER.error("Export blacklist error", e);
			return JsonResponse.inst("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.toString()).toResponseEntity();
		}
	}

	@Autowired
	private CategoryService category;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listCategories() { // Verified
		LOGGER.info("*** Got listCategories request.");
		List<Category> list = category.listCategories();
		for (Category c : list) {
			LOGGER.info("*** Got category: " + c);
		}
		return JsonResponse.inst("OK", HttpStatus.OK, list).toResponseEntity();
	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> saveCategory(@RequestBody Category o) { // Verified
		LOGGER.info("*** Got saveCategory request: " + o);
		if (StringUtil.isBlank(o.getName())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		category.save(o);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@Autowired
	private TopicService topic;

	@RequestMapping(value = "/topics", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> insertTopic(@RequestBody Topic o) { // Verified
		LOGGER.info("*** Got insertTopic request: " + o);
		if (StringUtil.isBlank(o.getCategory()) || StringUtil.isBlank(o.getQuestion())
				|| StringUtil.isBlank(o.getAnswer()) || StringUtil.isBlank(String.valueOf(o.getHeat()))) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		topic.insert(o);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@RequestMapping(value = "/topics/{topic_id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResponse> updateTopic(@PathVariable("topic_id") String topicId, @RequestBody Topic o) { // Verified
		LOGGER.info("*** Got updateTopic request: " + topicId);
		if (StringUtil.isBlank(o.getCategory()) || StringUtil.isBlank(o.getQuestion())
				|| StringUtil.isBlank(o.getAnswer()) || StringUtil.isBlank(String.valueOf(o.getHeat()))) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		o.setId(topicId);
		topic.update(o);
		return JsonResponse.inst("OK", HttpStatus.OK, o).toResponseEntity();
	}

	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listTopics(
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit) { // Verified
		LOGGER.info("*** Got listTopics request:(offset|limit) " + offset + "|" + limit);
		List<Topic> list = topic.list(null, offset, limit);
		LOGGER.info("*** Number of topics returned: " + list.size());
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, list);
		jrsp.setOffset(offset);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = "/categories/{category_id}/topics", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listTopicsByCategory(@PathVariable("category_id") String categoryId) { // Verified
		LOGGER.info("*** Got listTopicsByCategory request: " + categoryId);
		if (category.get(categoryId) == null) {
			return JsonResponse.inst("ERROR", HttpStatus.NOT_FOUND, "Category not found").toResponseEntity();
		}
		Config conf = config.getProperty(Constants.CONFIG_TYPE_SYSTEM, "显示多少个热门问题");
		int limit = Integer.valueOf(conf.getValue()).intValue();
		List<Topic> list = topic.list(categoryId, 0, limit);
		for (Topic t : list) {
			LOGGER.info("*** Got topic: " + t);
		}
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, list);
		jrsp.setOffset(0);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = "/trendingTopics", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listTrendingTopics( // Verified
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "0") int limit) {
		LOGGER.info("*** Got list_trending_topics request:(offset|limit) " + offset + "|" + limit);
		if (limit == 0) {
			Config conf = config.getProperty(Constants.CONFIG_TYPE_SYSTEM, "显示多少个热门问题");
			LOGGER.info("***config: " + conf);
			limit = Integer.valueOf(conf.getValue()).intValue();
		}
		List<Topic> list = topic.list(null, offset, limit);
		LOGGER.info("*** Number of topics returned: " + list.size());
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, list);
		jrsp.setOffset(0);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = "/trendingTopics-light", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listTrendingTopicsLight( // Verified
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "0") int limit) {
		LOGGER.info("*** Got listTrendingTopicsLight request:(offset|limit) " + offset + "|" + limit);
		if (limit == 0) {
			Config conf = config.getProperty(Constants.CONFIG_TYPE_SYSTEM, "显示多少个热门问题");
			LOGGER.info("***config: " + conf);
			limit = Integer.valueOf(conf.getValue()).intValue();
		}
		List<Topic> list = topic.list(null, offset, limit);
		LOGGER.info("*** Number of topics returned: " + list.size());
		List<TopicTO> llist = new ArrayList<>();
		for (Topic o : list) {
			TopicTO to = new TopicTO(o.getId(), o.getQuestion(), o.getAnswer());
			llist.add(to);
		}
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, llist);
		jrsp.setOffset(0);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = { "/download/template", "/download/template.xls",
			"/download/template.xlsx" }, method = RequestMethod.GET)
	public ResponseEntity<?> downloadTopics() {
		LOGGER.info("*** Got downloadTopics request");
		List<Topic> list = topic.list(null, 0, Integer.MAX_VALUE);

		final String[] header = { "category", "question", "answer", "heat" };
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Topics");
			XlsUtil.writeTitlesToExcel(workbook, sheet, header);

			int rowNum = 1;
			for (Topic item : list) {
				int colNum = 0;
				Row row = XlsUtil.createRow(sheet, rowNum++);
				XlsUtil.strCell(row, colNum++).setCellValue(item.getCategory());
				XlsUtil.strCell(row, colNum++).setCellValue(item.getQuestion());
				XlsUtil.strCell(row, colNum++).setCellValue(item.getAnswer());
				XlsUtil.numCell(row, colNum++).setCellValue(item.getHeat());
			}
			XlsUtil.autoSizeColumns(sheet, header.length);
			workbook.write(bos);
			ByteArrayResource res = new ByteArrayResource(bos.toByteArray());

			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"template.xls\"").body(res);
		} catch (Exception e) {
			LOGGER.error("Export topics error", e);
			return JsonResponse.inst("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.toString()).toResponseEntity();
		}
	}

	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> uploadTopics(
			@RequestParam(name = "file", required = false) MultipartFile file) {
		LOGGER.info("*** Got request: uploadTopics: " + (file == null ? "NA" : file.getName()));
		if (file == null || StringUtil.isBlank(file.getName())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing xlsx file").toResponseEntity();
		}
		int totalRecords = 0;
		try (InputStream is = file.getInputStream(); Workbook wb = new HSSFWorkbook(is)) {
			// Delete existing records first
			topic.deleteAll();
			category.deleteAll();

			Sheet sheet = wb.getSheetAt(0);
			for (int i = 1, size = sheet.getLastRowNum(); i <= size; i++) {
				Row row = sheet.getRow(i);
				int inserted = processUploadTopicRow(row);
				totalRecords += inserted;
			}
		} catch (Exception e) {
			LOGGER.error("uploadTopics error", e);
		}
		if (totalRecords > 0) {
			return JsonResponse.inst("OK", HttpStatus.CREATED).toResponseEntity();
		} else {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Bad file format/content!").toResponseEntity();
		}
	}

	private int processUploadTopicRow(Row row) throws Exception {
		StringJoiner sj = new StringJoiner(" | ");
		try {
			String categoryStr = XlsUtil.getStringCellValue(row.getCell(0));
			sj.add(categoryStr);
			String questionStr = XlsUtil.getStringCellValue(row.getCell(1));
			sj.add(questionStr);
			String answerStr = XlsUtil.getStringCellValue(row.getCell(2));
			sj.add(answerStr);
			int heat = XlsUtil.getIntCellValue(row.getCell(3));
			sj.add(heat + "");
			if (StringUtil.isBlank(categoryStr)) {
				categoryStr = "通用";
			}
			String rowStr = categoryStr + "|" + questionStr + "|" + answerStr + "|" + heat;
			LOGGER.info("*** Row: " + rowStr);
			if (StringUtil.isBlank(questionStr) || StringUtil.isBlank(answerStr)) {
				LOGGER.warn("Question or answer is blank, ignored: " + rowStr);
				return 0;
			}
			Category c = category.get(categoryStr);
			if (c == null) {
				c = category.save(new Category(categoryStr));
			}
			Topic newTopic = new Topic(c.getId(), questionStr, answerStr, heat);
			topic.insert(newTopic);
			return 1;
		} catch (Exception e) {
			LOGGER.error("!!! Error in processing row: " + sj.toString(), e);
			return 0;
		}
	}

	@RequestMapping(value = { "/download/faqs", "/download/faqs.csv" }, method = RequestMethod.GET)
	public ResponseEntity<?> downloadFaq() {
		LOGGER.info("*** Got request: downloadFaq");
		String[] headers = { "category_path", "questions", "content" };
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			CsvWriter writer = new CsvWriter(stream, ',', Charset.forName("UTF-8"));
			writer.writeRecord(headers);
			List<Topic> list = topic.list(null, 0, Integer.MAX_VALUE);
			for (Topic item : list) {
				writer.writeRecord(new String[] { item.getCategory(), item.getQuestion(), item.getAnswer() });
			}
			writer.close();
			ByteArrayResource res = new ByteArrayResource(stream.toByteArray());
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"faq.csv\"").body(res);
		} catch (Exception e) {
			LOGGER.error("Export faq csv error", e);
			return JsonResponse.inst("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.toString()).toResponseEntity();
		}
	}

	@Autowired
	private QuestionService question;

	@RequestMapping(value = "/questions", method = RequestMethod.POST) // Verified
	public ResponseEntity<JsonResponse> saveQuestion(@RequestBody Question o) {
		LOGGER.info("*** Got saveQuestion request: " + o);
		if (StringUtil.isBlank(o.getUser_id()) || StringUtil.isBlank(o.getQuestion())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		question.save(o);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listQuestions( // Verified
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit) {
		LOGGER.info("*** Got listQuestions request:(offset|limit) " + offset + "|" + limit);
		List<Question> list = question.list(offset, limit);
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, list);
		jrsp.setOffset(offset);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = { "/download/questions", "/download/questions.xls",
			"/download/questions.xlsx" }, method = RequestMethod.GET)
	public ResponseEntity<?> downloadQuestions(@RequestParam("from") String from,
			@RequestParam(name = "to", required = false) String to) throws IOException {
		LOGGER.info("*** Got request: downloadQuestions");
		if (StringUtil.isBlank(from)) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required parameters").toResponseEntity();
		}
		if (StringUtil.isBlank(to)) {
			to = DateUtil.date2String(DateUtil.now(), "yyyy-MM-dd");
		}
		Date dtFrom = DateUtil.string2Date(from, "yyyy-MM-dd");
		Date dtTo = DateUtil.string2Date(to, "yyyy-MM-dd");
		List<Question> list = question.queryByTime(dtFrom, DateUtil.getTomorrow(dtTo));

		final String[] header = { "用户ID", "问题", "提问时间" };
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Questions");
			XlsUtil.writeTitlesToExcel(workbook, sheet, header);

			int rowNum = 1;
			for (Question item : list) {
				int colNum = 0;
				Row row = XlsUtil.createRow(sheet, rowNum++);
				XlsUtil.strCell(row, colNum++).setCellValue(item.getUser_id());
				XlsUtil.strCell(row, colNum++).setCellValue(item.getQuestion());
				XlsUtil.strCell(row, colNum++)
						.setCellValue(DateUtil.date2String(item.getCreated_at(), "yyyy-MM-dd HH:mm:ss"));
			}
			XlsUtil.autoSizeColumns(sheet, header.length);
			workbook.write(bos);
			ByteArrayResource res = new ByteArrayResource(bos.toByteArray());

			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"questions.xls\"").body(res);
		} catch (Exception e) {
			LOGGER.error("Export question error", e);
			return JsonResponse.inst("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.toString()).toResponseEntity();
		}
	}

	@Autowired
	private FileService fileSvc;

	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public ResponseEntity<JsonResponse> synchronizeFile(@RequestBody File o) { // Verified
		LOGGER.info("*** Got synchronizeFile request: " + o);
		if (StringUtil.isBlank(o.getSerial_no()) || StringUtil.isBlank(o.getChecksum())
				|| StringUtil.isBlank(o.getFilename()) || StringUtil.isBlank(o.getFile_id())
				|| StringUtil.isBlank(o.getLink_id()) || StringUtil.isBlank(o.getPublic_uri())
				|| StringUtil.isBlank(o.getBatch_number()) || StringUtil.isBlank(o.getCommodity_id())) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required fields").toResponseEntity();
		}
		fileSvc.save(o);
		return JsonResponse.inst("OK", HttpStatus.CREATED, o).toResponseEntity();
	}

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> listAllFiles( // Verified
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit) {
		LOGGER.info("*** Got listAllFiles request:(offset|limit) " + offset + "|" + limit);
		List<File> list = fileSvc.query(null, null, offset, limit);
		JsonResponse jrsp = JsonResponse.inst("OK", HttpStatus.OK, list);
		jrsp.setOffset(offset);
		jrsp.setLimit(limit);
		return jrsp.toResponseEntity();
	}

	@RequestMapping(value = "/query/files", method = RequestMethod.GET)
	public ResponseEntity<JsonResponse> queryFile( // Verified
			@RequestParam(name = "commodity_id", required = false) String commodityId,
			@RequestParam(name = "batch_number", required = false) String batchNumber) {
		LOGGER.info("*** Got queryFile request:(commodity_id|batch_number) " + commodityId + "|" + batchNumber);
		if (StringUtil.isBlank(commodityId) || StringUtil.isBlank(batchNumber)) {
			return JsonResponse.inst("ERROR", HttpStatus.BAD_REQUEST, "Missing required query parameters")
					.toResponseEntity();
		}
		List<File> list = fileSvc.query(commodityId, batchNumber, 0, Integer.MAX_VALUE);
		if (list == null || list.isEmpty()) {
			return JsonResponse.inst("ERROR", HttpStatus.NOT_FOUND, "Record not found").toResponseEntity();
		}
		return JsonResponse.inst("OK", HttpStatus.OK, list).toResponseEntity();
	}
}
