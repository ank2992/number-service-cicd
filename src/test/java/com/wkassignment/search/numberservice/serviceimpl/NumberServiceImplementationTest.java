package com.wkassignment.search.numberservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.wkassignment.search.numberservice.responsemodel.NumberServiceModel;

@SpringBootTest
class NumberServiceImplementationTest {
	@Autowired
	NumberServiceImplementation numberService;

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * test method will test method numberSerachDetails with input containing 1 numeric value
	 * @author akitarambole
	 */
	@Test
	void testOneNumGetNumberSerachDetails() {

		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain",
				"Hello, World! 100".getBytes());

		List<NumberServiceModel> numberSerachDetails = numberService.getNumberSerachDetails(file);

		assertNotNull(numberSerachDetails);
		assertEquals(1, numberSerachDetails.size());
		assertEquals(100, numberSerachDetails.get(0).getNumValue());
		assertEquals(1, numberSerachDetails.get(0).getNumberPositionDetails().size());
	}

	/**
	 * test method will test method numberSerachDetails with input containing no numeric value
	 * @author akitarambole
	 */
	@Test
	void testNoNumGetNumberSerachDetails() {

		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

		List<NumberServiceModel> numberSerachDetails = numberService.getNumberSerachDetails(file);

		assertEquals(0, numberSerachDetails.size());

	}
	
	/**
	 * test method will test method numberSerachDetails with input of string read from file
	 * @author akitarambole
	 */
	@Test
	void testReadFile() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("testText.txt").getFile());
		byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", readFileToByteArray);
		List<NumberServiceModel> numberSerachDetails = numberService.getNumberSerachDetails(multipartFile);
		assertTrue(file.exists());
		assertNotNull(numberSerachDetails);
		assertEquals(6, numberSerachDetails.size());

	}

}
