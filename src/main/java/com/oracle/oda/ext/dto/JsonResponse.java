/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.dto
 * 
 *  File Name       : JsonResponse.java
 * 
 *  Creation Date   : 2019年2月20日
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
public class JsonResponse {
	private String status;
	private int code;
	private Object payload;
	private String error;
	private int offset = 0;
	private int limit = 0;

	public JsonResponse() {
	}

	public JsonResponse(String status, HttpStatus oCode) {
		this.status = status;
		this.code = oCode.value();
		this.payload = null;
	}

	public JsonResponse(String status, HttpStatus oCode, String error) {
		this.status = status;
		this.code = oCode.value();
		this.payload = null;
		this.error = error;
	}

	public JsonResponse(String status, HttpStatus oCode, Object payload) {
		this.status = status;
		this.code = oCode.value();
		this.payload = payload;
	}

	public static JsonResponse inst(String status, HttpStatus oCode) {
		return new JsonResponse(status, oCode);
	}

	public static JsonResponse inst(String status, HttpStatus oCode, Object payload) {
		return new JsonResponse(status, oCode, payload);
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the payload
	 */
	public Object getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public ResponseEntity<JsonResponse> toResponseEntity() {
		return ResponseEntity.status(this.getCode()).body(this);
	}

	public ResponseEntity<String> toResponseText() {
		return ResponseEntity.status(this.getCode()).body(this.status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonResponse [status=" + status + ", code=" + code + ", payload=" + payload + "]";
	}
}
