package com.wkassignment.search.numberservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wkassignment.search.numberservice.responsemodel.NumberServiceModel;

public interface NumberService {
	/**
	 * This method will return the list of Numbers present in the
	 * text file along with all the details of its position in the file.
	 * @param file
	 * @return
	 * @author ankitaramboles
	 */
	
	List<NumberServiceModel> getNumberSerachDetails(MultipartFile file);

}
