/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.domains;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.domains
 * 
 *  File Name       : File.java
 * 
 *  Creation Date   : 2019年2月23日
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
public class File extends BaseObject {
	private String serial_no;
	private String checksum;
	private String filename;
	private String file_id;
	private String link_id;
	private String public_uri;
	private String batch_number;
	private String commodity_id;

	public File() {
		super();
	}

	/**
	 * @return the serial_no
	 */
	public String getSerial_no() {
		return serial_no;
	}

	/**
	 * @param serial_no the serial_no to set
	 */
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the file_id
	 */
	public String getFile_id() {
		return file_id;
	}

	/**
	 * @param file_id the file_id to set
	 */
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	/**
	 * @return the link_id
	 */
	public String getLink_id() {
		return link_id;
	}

	/**
	 * @param link_id the link_id to set
	 */
	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}

	/**
	 * @return the public_uri
	 */
	public String getPublic_uri() {
		return public_uri;
	}

	/**
	 * @param public_uri the public_uri to set
	 */
	public void setPublic_uri(String public_uri) {
		this.public_uri = public_uri;
	}

	/**
	 * @return the batch_number
	 */
	public String getBatch_number() {
		return batch_number;
	}

	/**
	 * @param batch_number the batch_number to set
	 */
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}

	/**
	 * @return the commodity_id
	 */
	public String getCommodity_id() {
		return commodity_id;
	}

	/**
	 * @param commodity_id the commodity_id to set
	 */
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "File [serial_no=" + serial_no + ", checksum=" + checksum + ", filename=" + filename + ", file_id="
				+ file_id + ", link_id=" + link_id + ", public_uri=" + public_uri + ", batch_number=" + batch_number
				+ ", commodity_id=" + commodity_id + "]";
	}
}
