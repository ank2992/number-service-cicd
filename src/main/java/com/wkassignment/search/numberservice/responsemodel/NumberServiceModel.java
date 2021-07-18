/**
 * 
 */
package com.wkassignment.search.numberservice.responsemodel;
/**
 * This class represent the searched number and the details of its position 
 * in different lines.
 * @author ankitarambole
 */

import java.io.Serializable;
import java.util.List;

public class NumberServiceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889535423548141118L;

	private Integer numValue;
	
	private List<NumberPosDetails> numberPositionDetails;

	public NumberServiceModel() {
		super();

	}

	public Integer getNumValue() {
		return numValue;
	}

	public void setNumValue(Integer numValue) {
		this.numValue = numValue;
	}

	public List<NumberPosDetails> getNumberPositionDetails() {
		return numberPositionDetails;
	}

	public void setNumberPositionDetails(List<NumberPosDetails> numberPositionDetails) {
		this.numberPositionDetails = numberPositionDetails;
	}

	
}
