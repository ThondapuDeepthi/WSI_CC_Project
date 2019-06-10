package com.diy.challenge.test;

import java.util.ArrayList;

import com.diy.challenge.ZipCodeRangeCalculator;
import com.diy.challenge.ZipCodeRangeDTO;

import junit.framework.TestCase;

/**
 * @author Thondapu Deepthi
 *
 */
public class ZipcodeRangeCalculatorTest extends TestCase {

	public void test_type() throws Exception {
		assertNotNull(ZipCodeRangeCalculator.class);
	}

	public void test_instantiation() throws Exception {
		ZipCodeRangeCalculator target = new ZipCodeRangeCalculator();
		assertNotNull(target);
	}

	/**
	 * Test with zipcode input number greater than 5 digits.
	 */
	public void test_invalidInput1() {
		boolean exceptionThrown = false;
		 
		String userInput = "[133353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeCalculator.validateZipCodeRangeArray(userInput);
		} catch (Exception e) {
			if(e.getMessage().contains("input is invalid") )
				exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * Test with alpha numeric input.
	 */
	public void test_invalidInput2() {
		boolean exceptionThrown = false;
		 
		String userInput = "[133asd53,11393] [11348,11352] [11420,11460], [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeCalculator.validateZipCodeRangeArray(userInput);
		} catch (Exception e) {
			if(e.getMessage().contains("input is invalid") )
				exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	/**
	 * Test with of zip code range whose lower Bound value is greater than upper bound.
	 */
	public void test_inputFormatValidation() {
		ArrayList<ZipCodeRangeDTO> expectedResultsArray = new ArrayList<ZipCodeRangeDTO>();
		ZipCodeRangeDTO zipCodeRange1 = new ZipCodeRangeDTO(new Integer(11348), new Integer(11352));
		ZipCodeRangeDTO zipCodeRange2 = new ZipCodeRangeDTO(new Integer(11396), new Integer(11470));
		expectedResultsArray.add(zipCodeRange1);
		expectedResultsArray.add(zipCodeRange2);

		String userInput = "[13353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] ";
		try {
			ZipCodeRangeCalculator.validateZipCodeRangeArray(userInput);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertTrue(expectedResultsArray.equals(ZipCodeRangeCalculator.optimizedZipCodeArray) );
	}
	
	/**
	 * Test with valid zipcode range inputs for the final optimizedZipcodeRangeArray.
	 */
		public void test_zipCodeRangeOptimization() {
			ArrayList<ZipCodeRangeDTO> expectedResults = new ArrayList<>();
			ZipCodeRangeDTO zipCodeRange1 = new ZipCodeRangeDTO(new Integer(11348), new Integer(11352));
			ZipCodeRangeDTO zipCodeRange2 = new ZipCodeRangeDTO(new Integer(11396), new Integer(11470));
			ZipCodeRangeDTO zipCodeRange3 = new ZipCodeRangeDTO(new Integer(11353), new Integer(11393));
			expectedResults.add(zipCodeRange3);
			expectedResults.add(zipCodeRange1);
			expectedResults.add(zipCodeRange2);
			
			String userInput = "[11353,11393] [11348,11352] [11420,11460] [11396,11430] [11400,11470] ";
			try {
				ZipCodeRangeCalculator.validateZipCodeRangeArray(userInput);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			assertTrue(expectedResults.equals(ZipCodeRangeCalculator.optimizedZipCodeArray));
		}
	
	

}
