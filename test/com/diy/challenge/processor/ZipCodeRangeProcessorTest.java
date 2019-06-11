package com.diy.challenge.processor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import com.diy.challenge.ZipCodeRangeCalculator;
import com.diy.challenge.dto.ZipCodeRangeDTO;
import com.diy.challenge.exception.InvalidFormatException;
import com.diy.challenge.util.ZipCodeRangeGlobals;

public class ZipCodeRangeProcessorTest implements ZipCodeRangeGlobals {

	
	public void test_type() throws Exception {
		assertNotNull(ZipCodeRangeCalculator.class);
	}

	public void test_instantiation() throws Exception {
		ZipCodeRangeCalculator target = new ZipCodeRangeCalculator();
		assertNotNull(target);
	}
	
	/**
	 * Test method for {@link com.diy.challenge.ZipCodeRangeCalculator#validateZipCodeRangeArray(java.lang.String)}.
	 * Test case - for input with zipcode greater than 5 digits
	 */
	@Test
	public void testValidateZipCodeRangeArray() {
		boolean exceptionThrown = false;
		 
		String userInput = "[133353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeProcessor zipCodeRangeProcessor = new ZipCodeRangeProcessor();
			zipCodeRangeProcessor.validateZipCodeRangeArray(userInput);
		} catch (InvalidFormatException e) {
			if(e.getMessage().contains("input is invalid") )
				exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * Test with alpha numeric input.
	 */
	@Test
	public void test_invalidInput2() {
		boolean exceptionThrown = false;
		 
		String userInput = "[133asd53,11393] [11348,11352] [11420,11460], [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeProcessor zipCodeRangeProcessor = new ZipCodeRangeProcessor();
			zipCodeRangeProcessor.validateZipCodeRangeArray(userInput);
		} catch (InvalidFormatException e) {
			if(e.getMessage().contains("input is invalid") )
				exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * Test with of zip code range whose lower Bound value is greater than upper bound.
	 */
	@Test
	public void test_inputFormatValidation() {
		ArrayList<ZipCodeRangeDTO> expectedResultsArray = new ArrayList<ZipCodeRangeDTO>();
		ZipCodeRangeDTO zipCodeRange1 = new ZipCodeRangeDTO(new Integer(11348), new Integer(11352));
		ZipCodeRangeDTO zipCodeRange2 = new ZipCodeRangeDTO(new Integer(11396), new Integer(11470));
		expectedResultsArray.add(zipCodeRange1);
		expectedResultsArray.add(zipCodeRange2);

		String userInput = "[13353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeProcessor zipCodeRangeProcessor = new ZipCodeRangeProcessor();
			zipCodeRangeProcessor.validateZipCodeRangeArray(userInput);
		} catch (InvalidFormatException e) {
			System.out.println(e.getMessage());
		}
		assertTrue(expectedResultsArray.equals(optimizedZipCodeArray) );
	}
	
	/**
	 * Test with valid zipcode range inputs for the final optimizedZipcodeRangeArray.
	 */
	@Test
		public void test_zipCodeRangeOptimization() {
			ArrayList<ZipCodeRangeDTO> expectedResults = new ArrayList<>();
			ZipCodeRangeDTO zipCodeRange1 = new ZipCodeRangeDTO(new Integer(11348), new Integer(11352));
			ZipCodeRangeDTO zipCodeRange2 = new ZipCodeRangeDTO(new Integer(11395), new Integer(11471));
			ZipCodeRangeDTO zipCodeRange3 = new ZipCodeRangeDTO(new Integer(11353), new Integer(11393));
			expectedResults.add(zipCodeRange3);
			expectedResults.add(zipCodeRange1);
			expectedResults.add(zipCodeRange2);
			
			String userInput = "[11353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] [11395,11471]";
			try {
				ZipCodeRangeProcessor zipCodeRangeProcessor = new ZipCodeRangeProcessor();
				zipCodeRangeProcessor.validateZipCodeRangeArray(userInput);
			} catch (InvalidFormatException e) {
				System.out.println(e.getMessage());
			}
			assertTrue(expectedResults.equals(optimizedZipCodeArray));
		}

}
