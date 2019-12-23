/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils.http;

import java.io.InputStream;

import org.apache.http.entity.mime.content.InputStreamBody;

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
public class InputStreamFileObject extends InputStreamBody {
	private long contentLength;

	/**
	 * Since the mime type is not specified, the default value is
	 * "application/octet-stream"
	 */
	public InputStreamFileObject(InputStream in, long contentLength,
			String filename) {
		super(in, filename);
		this.contentLength = contentLength;
	}

	/**
	 * @param in
	 *            : Then inputstream of the file.
	 * @param mimeType
	 *            : For example, image/png
	 * @param contentLength
	 *            : The length of the file content, in bytes
	 * @param filename
	 *            : For example, test.png
	 */
	public InputStreamFileObject(InputStream in, String mimeType,
			long contentLength, String filename) {
		super(in, mimeType, filename);
		this.contentLength = contentLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.http.entity.mime.content.InputStreamBody#getContentLength()
	 */
	public long getContentLength() {
		return contentLength;
	}
}
