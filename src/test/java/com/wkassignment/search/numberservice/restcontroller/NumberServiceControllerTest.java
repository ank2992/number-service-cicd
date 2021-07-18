package com.wkassignment.search.numberservice.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.wkassignment.search.numberservice.exceptions.NumberServiceException;
import com.wkassignment.search.numberservice.responsemodel.NumberPosDetails;
import com.wkassignment.search.numberservice.responsemodel.NumberServiceModel;
import com.wkassignment.search.numberservice.service.NumberService;

@SpringBootTest
class NumberServiceControllerTest {
	@MockBean
	NumberService numberService;

	@Autowired
	NumberServiceController numberServiceController;


	@BeforeEach
	void setUp() throws Exception {

	}

	private List<NumberServiceModel> getNumberServiceModelList() {
		List<NumberServiceModel> numServModel = new ArrayList<>();

		List<NumberPosDetails> detail1List = new ArrayList<>();
		NumberPosDetails detail1 = new NumberPosDetails();
		detail1.setFragment("am. 80 But");
		detail1.setLineNumber(17);
		detail1.setNumberPosition(23);
		detail1List.add(detail1);
		NumberServiceModel numServicemodel1 = new NumberServiceModel();
		numServicemodel1.setNumValue(80);
		numServicemodel1.setNumberPositionDetails(detail1List);
		numServModel.add(numServicemodel1);

		return numServModel;
	}

	@Test
	void testGetNumberSerachDetails() {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
		List<NumberServiceModel> numberServiceModelList = getNumberServiceModelList();
		when(numberService.getNumberSerachDetails(file)).thenReturn(numberServiceModelList);
		ResponseEntity<List<NumberServiceModel>> numberSerachDetails = numberServiceController
				.getNumberSerachDetails(file);
		System.out.println(numberSerachDetails);
		assertNotNull(numberSerachDetails);
		assertEquals(1, numberSerachDetails.getBody().size());
		assertEquals(80, numberSerachDetails.getBody().get(0).getNumValue());
		assertEquals(1, numberSerachDetails.getBody().get(0).getNumberPositionDetails().size());

	}
	
	@Test
	void testGetNumberSerachDetailsNotText() {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "", "Hello, World!".getBytes());
		List<NumberServiceModel> numberServiceModelList = getNumberServiceModelList();
		when(numberService.getNumberSerachDetails(file)).thenReturn(numberServiceModelList);
		assertThrows(NumberServiceException.class, (()->{
			numberServiceController
			.getNumberSerachDetails(file);
		}));
		

	}

}
