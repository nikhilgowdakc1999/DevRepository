package com.revolusys.Registration_api.advice;

public class ResponseType {

	private String message;
	private boolean success;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public ResponseType(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	
	public ResponseType() {
		super();
	}
	
	@Override
	public String toString() {
		return "ResponseType [message=" + message + ", success=" + success + "]";
	}
	
	
}
