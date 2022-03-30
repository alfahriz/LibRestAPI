package com.sdd.LibRestAPI.pojo;

public class ReqAll<T> {
	private String operationType;
	private String operationDesc;
	private T request;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public T getRequest() {
		return request;
	}

	public void setRequest(T request) {
		this.request = request;
	}
	
	
}