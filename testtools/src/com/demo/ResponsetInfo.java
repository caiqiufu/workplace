package com.demo;

public class ResponsetInfo {
	public RequestHeader ResponseHeader;
	public RequestBody responseBody;
	public RequestHeader getResponseHeader() {
		return ResponseHeader;
	}
	public void setResponseHeader(RequestHeader ResponseHeader) {
		this.ResponseHeader = ResponseHeader;
	}
	public RequestBody getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(RequestBody responseBody) {
		this.responseBody = responseBody;
	}
	
	
}
