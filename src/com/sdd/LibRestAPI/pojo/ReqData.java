package com.sdd.LibRestAPI.pojo;

import com.sdd.LibRestAPI.domain.Mmahasiswa;

public class ReqData<T> {
	private T content;

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}