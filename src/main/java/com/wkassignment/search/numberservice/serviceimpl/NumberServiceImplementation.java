package com.wkassignment.search.numberservice.serviceimpl;

/**
 * This class will hold the business logic required to perform the
 * number search and to gather its placement details.
 * 
 * @author ankitarambole
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wkassignment.search.numberservice.model.FragmentInfo;
import com.wkassignment.search.numberservice.responsemodel.NumberPosDetails;
import com.wkassignment.search.numberservice.responsemodel.NumberServiceModel;
import com.wkassignment.search.numberservice.service.NumberService;
import com.wkassignment.search.numberservice.shared.Utility;

@Service
public class NumberServiceImplementation implements NumberService {

	Logger logger = LogManager.getLogger(NumberServiceImplementation.class);

	@Override
	public List<NumberServiceModel> getNumberSerachDetails(MultipartFile file) {
		logger.info("NumberServiceImplementation::getNumberSerachDetails starts");

		List<NumberServiceModel> mapSearchDetailsToResponseModel = null;

		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());

			String fileToString = IOUtils.toString(stream, StandardCharsets.UTF_8);

			Map<Integer, List<NumberPosDetails>> numberAndPositionDetails = getNumberAndPositionDetails(fileToString);

			mapSearchDetailsToResponseModel = mapSearchDetailsToResponseModel(numberAndPositionDetails);

		} catch (IOException e) {

			logger.error("IO exception reading file {} reason : {}", file.getName(), e.getMessage());
		}

		logger.info("NumberServiceImplementation::getNumberSerachDetails ends");

		return mapSearchDetailsToResponseModel;
	}
	
	

	/**
	 * This method will identify if given line contains any Number and returns the
	 * result in HashMap format, where the key is the number and Value is the list
	 * of all the positions where the number has occurred.
	 * 
	 * @param fileToString
	 * @return
	 * @author ankitarambole
	 */
	private Map<Integer, List<NumberPosDetails>> getNumberAndPositionDetails(String fileToString) {
		logger.debug("NumberServiceImplementation::getNumberAndPositionDetails starts");

		LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(fileToString));

		Pattern numPattern = Pattern.compile(Utility.SEARCH_NUM_FIND_IF_NUM_PRESENT);

		Map<Integer, List<NumberPosDetails>> numberAndPosDetailsReturnList = new HashMap<>();

		String textLine = null;

		try {

			while ((textLine = lineNumberReader.readLine()) != null) {

				Matcher matcher = numPattern.matcher(textLine);

				if (matcher.find()) {

					getNumberAndPositionDetails(textLine, numberAndPosDetailsReturnList,
							lineNumberReader.getLineNumber());

				}

			}

		} catch (IOException e) {

			logger.error("IO exception reading textline {} reason : {}", textLine, e.getMessage());
		}

		logger.debug("NumberServiceImplementation::getNumberAndPositionDetails ends");
		
		return numberAndPosDetailsReturnList;

	}
	
	

	/**
	 * This method will set Number occurrence details in NumberPosDetails class. The
	 * details includes LineNumber, The Number Position in the line, and its
	 * fragment.
	 * 
	 * @param textLine
	 * @param numberAndPosDetailsReturnList
	 * @param lineNumber
	 * @author ankitarambole
	 */

	private void getNumberAndPositionDetails(String textLine,
			Map<Integer, List<NumberPosDetails>> numberAndPosDetailsReturnList, int lineNumber) {
		logger.debug("NumberServiceImplementation::getNumberAndPositionDetails starts");

		Pattern strictNum = Pattern.compile(Utility.SEARCH_NUM_FIND_IF_STRICTLY_NUMBER);

		String[] wordsArray = textLine.split(Utility.SEARCH_NUM_BLANK_SPACE);

		int position = 0;

		for (String word : wordsArray) {
			

			position++;
			Matcher matcher = strictNum.matcher(word);
			
			if (matcher.find()) {
				
				logger.trace("numeric word is {}",word);

				NumberPosDetails numberPosDetails = new NumberPosDetails();

				numberPosDetails.setLineNumber(lineNumber);
				numberPosDetails.setNumberPosition(position);

				String fragment = getFragment(wordsArray, position - 1);
				numberPosDetails.setFragment(fragment);

				List<NumberPosDetails> list = numberAndPosDetailsReturnList.get(Integer.parseInt(word));

				if (list != null && !list.isEmpty()) {

					list.add(numberPosDetails);
					numberAndPosDetailsReturnList.put(Integer.parseInt(word), list);

				} else {

					List<NumberPosDetails> numPosDetailList = new ArrayList<>();
					numPosDetailList.add(numberPosDetails);
					numberAndPosDetailsReturnList.put(Integer.parseInt(word), numPosDetailList);
				}

			}
		}

		logger.debug("NumberServiceImplementation::getNumberAndPositionDetails ends");

	}
	
	

	/**
	 * This method will return the Fragment of the searched number.
	 * 
	 * @param wordsArray
	 * @param pos
	 * @return
	 * @author ankitarambole
	 */

	private String getFragment(String[] wordsArray, int pos) {
		logger.debug("NumberServiceImplementation::getFragment starts");

		StringJoiner fragment = new StringJoiner(Utility.SEARCH_NUM_BLANK_SPACE);

		FragmentInfo fragInfo = getPosStartValueForFragment(wordsArray.length, pos);

		int positionStart = fragInfo.getPositionStart();
		int positionEnd = fragInfo.getPositionEnd();

		for (int i = positionStart; i <= positionEnd; i++) {

			fragment.add(wordsArray[i]);
		}

		logger.debug("NumberServiceImplementation::getFragment ends");
		return fragment.toString();

	}
	
	

	/**
	 * This method will return the start position and end position of the Fragment
	 * 
	 * @param wordArrayLength
	 * @param pos
	 * @return
	 * @author ankitarambole
	 */
	private FragmentInfo getPosStartValueForFragment(int wordArrayLength, int pos) {
		logger.debug("NumberServiceImplementation::getPosStartValueForFragment starts");

		FragmentInfo fragInfo = new FragmentInfo();

		if (wordArrayLength <= 3) {

			fragInfo.setPositionStart(0);
			fragInfo.setPositionEnd(wordArrayLength - 1);

		} else if (pos == 0) {

			fragInfo.setPositionStart(0);
			fragInfo.setPositionEnd(2);

		} else if (pos > wordArrayLength - 4) {

			fragInfo.setPositionStart(pos);
			fragInfo.setPositionEnd(wordArrayLength - 1);

		} else {

			fragInfo.setPositionStart(pos - 1);
			fragInfo.setPositionEnd(pos + 1);
		}

		logger.debug("NumberServiceImplementation::getPosStartValueForFragment ends");

		return fragInfo;

	}
	
	

	/**
	 * This method will map the HasMap result of number search into the
	 * NumberServiceModel class
	 * 
	 * @param numberAndPosDetails
	 * @return
	 * @author ankitarambole
	 */
	private List<NumberServiceModel> mapSearchDetailsToResponseModel(
			Map<Integer, List<NumberPosDetails>> numberAndPosDetails) {

		logger.debug("NumberServiceImplementation::mapSearchDetailsToResponseModel starts");

		List<NumberServiceModel> numberServiceModelReturn = new ArrayList<>();

		numberAndPosDetails.entrySet().stream().forEach(e -> {

			NumberServiceModel numberServiceModel = new NumberServiceModel();

			numberServiceModel.setNumValue(e.getKey());
			numberServiceModel.setNumberPositionDetails(e.getValue());
			numberServiceModelReturn.add(numberServiceModel);

		});

		logger.debug("NumberServiceImplementation::mapSearchDetailsToResponseModel ends");

		return numberServiceModelReturn;

	}

}
