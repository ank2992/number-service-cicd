package com.wkassignment.search.numberservice.restcontroller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wkassignment.search.numberservice.exceptions.NumberServiceException;
import com.wkassignment.search.numberservice.responsemodel.NumberServiceModel;
import com.wkassignment.search.numberservice.service.NumberService;
import com.wkassignment.search.numberservice.shared.Messages;
import com.wkassignment.search.numberservice.shared.Utility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(Utility.SEARCH_NUM_REQUEST_MAPPING)
@Api(value = "Number Service", description = "Operations performed to search the Numbers present in text document")
public class NumberServiceController {

	Logger logger = LogManager.getLogger(NumberServiceController.class);

	@Autowired
	NumberService numberService;

	/**
	 * This method will return the list of all the numbers present in the text along
	 * with the details of its position
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @author ankitarambole
	 */
	@ApiOperation(value = "Returns the list of numbers present in the text document with its position details", response = List.class)
	@PostMapping(value = Utility.SEARCH_NUM_POST_MAPPING, consumes = "multipart/form-data")
	public ResponseEntity<List<NumberServiceModel>> getNumberSerachDetails(
			@RequestPart(Utility.SEARCH_NUM_REQUEST_PARAM_FILE) @ApiParam(value = "File", required = true) MultipartFile file)
			throws NumberServiceException {

		logger.info("NumberServiceController:getNumberSerachDetails starts");

		if (file.isEmpty())
			throw new NumberServiceException(Messages.ERROR_MESSAGE_FILE_EMPTY.message());

		// throw invalid file format exception if not text file
		if (!Utility.SEARCH_NUM_TEXTFILE_CONTENT_TYPE.equals(file.getContentType()))
			throw new NumberServiceException(Messages.ERROR_MESSAGE_INVALID_FILE_FORMAT.message());

		List<NumberServiceModel> numberCountList = numberService.getNumberSerachDetails(file);

		if (numberCountList == null || numberCountList.isEmpty()) {

			logger.debug("NumberServiceController:getNumberSerachDetails ends");

			logger.info("No Numbers are present in file {} ", file.getName());

			return new ResponseEntity<>(numberCountList, HttpStatus.NO_CONTENT);

		} else {
			logger.info("NumberServiceController:getNumberSerachDetails ends");

			return new ResponseEntity<>(numberCountList, HttpStatus.OK);

		}

	}

}
