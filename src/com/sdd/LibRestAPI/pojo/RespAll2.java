package com.sdd.LibRestAPI.pojo;

public class RespAll2 extends RespError {
	private String responseType;
	private String responseMessage;
	private RespData2 responseBody;

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

	public RespData2 getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(RespData2 responseBody) {
		this.responseBody = responseBody;
	}

}
