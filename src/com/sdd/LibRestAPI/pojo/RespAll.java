package com.sdd.LibRestAPI.pojo;

public class RespAll extends RespError {
	private String responseType;
	private String responseMessage;
	private RespData responseBody;

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public RespData getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(RespData responseBody) {
		this.responseBody = responseBody;
	}

}
