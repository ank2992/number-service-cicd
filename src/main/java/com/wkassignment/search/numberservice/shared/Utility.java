package com.wkassignment.search.numberservice.shared;

public class Utility {
	
	public static final String SEARCH_NUM_REQUEST_MAPPING="/search";
	public static final String SEARCH_NUM_POST_MAPPING="/numbers";
	
	
	public static final String SEARCH_NUM_REQUEST_PARAM_FILE="file";
	
	public static final String SEARCH_NUM_TEXTFILE_CONTENT_TYPE="text/plain";
	
	public static final String SEARCH_NUM_FIND_IF_NUM_PRESENT="\\d+";
	
	public static final String SEARCH_NUM_BLANK_SPACE=" ";
	
	public static final String SEARCH_NUM_FIND_IF_STRICTLY_NUMBER="^[0-9]*$";
	
	
	
	
	 private Utility() {
		    throw new IllegalStateException("Utility class");
		  }

}
