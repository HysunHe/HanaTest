/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.soap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/***************************************************************************
 * <PRE>
 *  Project Name    : PpmSyncMgr
 * 
 *  Package Name    : com.oracle.sccpoc.doosan.utils.soap
 * 
 *  File Name       : SoapConfigBean.java
 * 
 *  Creation Date   : 2016-11-03
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class SoapConfigBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapConfigBean.class);

	/*
	 * Package name. All xml files in this package will be loaded. Multiple
	 * packages are supported, using comma (,) as the delimiter if you want to
	 * specify more than one package. White spaces will be trimmed.
	 */
	private String packages;

	// Character set to be used for URL Decoding.
	private String charset;

	private final Map<String, String> soapMap;
	private volatile boolean isInitialized = false;
	private final ReentrantLock lock;
	private static final String DELIMITER = "\\,";

	/**
	 * The bean should be managed by application itself or IoC container as a
	 * singleton.
	 */
	public SoapConfigBean() {
		this.isInitialized = false;
		this.soapMap = new HashMap<String, String>();
		this.lock = new ReentrantLock();
		this.charset = "UTF-8";
	}

	/**
	 * @return the packages
	 */
	public String getPackages() {
		return packages;
	}

	/**
	 * @param packages
	 *            the packages to set
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Initialize the soapConfigBean. This will load all the soap definition XML
	 * files (specified by the packages property) into memory.
	 */
	public void initialize() {
		// Avoid duplicate load.
		if (this.isInitialized) {
			return;
		}

		if (this.packages == null || this.packages.isEmpty()) {
			LOGGER.error("soap file localtion(s) are not specified!");
			return;
		}

		lock.lock();
		try {
			// Double check lock.
			if (!this.isInitialized) {
				loadAll();
			}
			this.isInitialized = true;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Get the soap body, which is defined in some soap configuration file, by
	 * specifying the name.
	 * 
	 * @param name
	 * @return
	 */
	public String findsoap(String name) {
		if (!this.isInitialized) {
			this.initialize();
		}
		return this.soapMap.get(name);
	}

	/**
	 * Load all soap configuration (XML files) into memory.
	 */
	private void loadAll() {
		String[] pkgArray = this.packages.split(DELIMITER);
		for (String pkg : pkgArray) {
			String canonicalPkg = pkg.replace('.', '/').trim();
			String absPath = SoapConfigBean.class.getResource("/").getPath() + canonicalPkg;
			// Handler URL encode issue
			try {
				absPath = URLDecoder.decode(absPath, this.charset);
			} catch (UnsupportedEncodingException e) {
				LOGGER.warn("Unsupported charset for URL decoding: " + this.charset + " | " + absPath);
			}

			LOGGER.info("Loading soap definitions from location: " + absPath);
			File dir = new File(absPath);
			File[] files = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".xml");
				}
			});

			if (files == null || files.length == 0) {
				LOGGER.warn("No soap file found in package: " + dir.getAbsolutePath());
				continue;
			}

			for (File file : files) {
				LOGGER.info("Loading file: " + file.getName());
				Document dom = loadsoapConfigFile(file);
				if (dom != null) {
					parseDocument(dom);
					LOGGER.info("File loaded: " + file.getName());
				}
			}
		}
	}

	/**
	 * Load the soap configuration file (specified) and transform it to w3c DOM.
	 * 
	 * @param file
	 * @return
	 */
	private Document loadsoapConfigFile(File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found " + file);
			return null;
		}

		Document dom = null;
		try {
			dom = factory.newDocumentBuilder().parse(inputStream);
		} catch (SAXException e) {
			LOGGER.error("The File is not parsable " + file, e);
		} catch (IOException e) {
			LOGGER.error("Failed to load the configuration file due to IOException" + file, e);
		} catch (ParserConfigurationException e) {
			LOGGER.error("Configuration error (Syntax error) " + file, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// We cannot do anything here. Just log it and ignore.
					LOGGER.error("Possibly failed to close the inputstream: " + file);
				}
			}
		}
		return dom;
	}

	/**
	 * Parse the specified document. The soap statements defined in the
	 * corresponding configuration file will be loaded.
	 * 
	 * @param dom
	 */
	private void parseDocument(Document dom) {
		Element root = dom.getDocumentElement();
		NodeList statements = root.getElementsByTagName("soap");
		if (statements == null || statements.getLength() == 0) {
			return;
		}

		for (int i = 0; i < statements.getLength(); i++) {
			if (!(statements.item(i) instanceof Element)) {
				continue;
			}
			Element statement = (Element) statements.item(i);
			String name = statement.getAttribute("name");
			if (name == null || name.trim().isEmpty()) {
				LOGGER.warn("soap name is mandatory");
				continue;
			}
			String body = statement.getFirstChild().getNodeValue();
			if (body == null || body.trim().isEmpty()) {
				LOGGER.warn("No body defined for soap " + name);
				continue;
			}
			this.soapMap.put(name, body);
		}
	}
}
