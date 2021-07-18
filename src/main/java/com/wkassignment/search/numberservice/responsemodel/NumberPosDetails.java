package com.wkassignment.search.numberservice.responsemodel;

/**
 * This class will hold the granular details of searched number,
 * which includes line number, its position and fragmented text.
 * @author ankitarambole
 * 
 */

import java.io.Serializable;

public class NumberPosDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3928670440130387983L;

	/**
	 * the line number where number is present
	 */
	private Integer lineNumber;
	/**
	 * the word no in the respective line number
	 */
	private Integer numberPosition;

	/**
	 * small text fragment around the number
	 */
	private String fragment;

	public NumberPosDetails() {
		super();

	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public Integer getNumberPosition() {
		return numberPosition;
	}

	public void setNumberPosition(Integer numberPosition) {
		this.numberPosition = numberPosition;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}

}
