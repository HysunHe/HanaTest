/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import java.io.File;

import org.apache.http.entity.mime.content.FileBody;

/***************************************************************************
 *<PRE>
 *  Project Name    : PpmSyncMgr
 * 
 *  Package Name    : com.oracle.sccpoc.doosan.utils.http
 * 
 *  File Name       : HttpUtil.java
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
 *</PRE>
 ***************************************************************************/
public class SimpleFileObject extends FileBody {
	/**
	 * @param file
	 */
	public SimpleFileObject(File file) {
		this(file.getName(), file, "application/octet-stream", "UTF-8");
	}

	/**
	 * @param file
	 * @param mimeType
	 */
	public SimpleFileObject(File file, String mimeType) {
		this(file.getName(), file, mimeType, "UTF-8");
	}

	/**
	 * @param file
	 * @param mimeType
	 * @param charset
	 */
	public SimpleFileObject(File file, String mimeType, String charset) {
		this(file.getName(), file, mimeType, charset);
	}

	/**
	 * @param file
	 * @param filename
	 * @param mimeType
	 * @param charset
	 */
	public SimpleFileObject(String fileFieldName, File file, String mimeType,
			String charset) {
		super(file, fileFieldName, mimeType, charset);
	}
}
