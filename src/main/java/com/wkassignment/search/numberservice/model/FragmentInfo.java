package com.wkassignment.search.numberservice.model;
/**
 * This class represents the start position and end position of String Fragment
 * 
 * @author ankitarambole
 *
 */

public class FragmentInfo {

	private int positionStart;
	private int positionEnd;

	public FragmentInfo() {
		super();
	}

	public int getPositionStart() {
		return positionStart;
	}

	public void setPositionStart(int positionStart) {
		this.positionStart = positionStart;
	}

	public int getPositionEnd() {
		return positionEnd;
	}

	public void setPositionEnd(int positionEnd) {
		this.positionEnd = positionEnd;
	}

}
