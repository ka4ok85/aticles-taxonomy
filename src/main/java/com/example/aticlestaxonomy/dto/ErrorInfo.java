package com.example.aticlestaxonomy.dto;

import java.util.Date;

public class ErrorInfo {

	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;

	public ErrorInfo(String title, int status, String detail, String developerMessage) {
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.developerMessage = developerMessage;
		setTimestamp(new Date().getTime());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	@Override
	public String toString() {
		return "ErrorInfo [title=" + title + ", status=" + status + ", detail=" + detail + ", timestamp=" + timestamp
				+ ", developerMessage=" + developerMessage + "]";
	}

}
