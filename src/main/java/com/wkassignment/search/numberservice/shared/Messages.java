package com.wkassignment.search.numberservice.shared;
/**
 * this class will hold all the possible set of messages which the application will use
 * @author ankitarambole
 *
 */
public enum Messages {

	ERROR_MESSAGE_INVALID_FILE_FORMAT("Received invalid file format"),
	ERROR_MESSAGE_FILE_EMPTY("Received an Empty file");
	

	private String message;

	Messages(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
