package com.sdd.LibRestAPI.pojo;

public class RespError {
	private Integer errorCode;
	private String errorMessage;
	private String errorLink;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorLink() {
		return errorLink;
	}

	public void setErrorLink(String errorLink) {
		this.errorLink = errorLink;
	}
}
