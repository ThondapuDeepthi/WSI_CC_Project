
package com.diy.challenge.processor;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.diy.challenge.dto.ZipCodeRangeDTO;
import com.diy.challenge.exception.InvalidFormatException;
import com.diy.challenge.util.ZipCodeRangeGlobals;

/**
 * @author Thondapu Deepthi
 *
 */
public class ZipCodeRangeProcessor implements ZipCodeRangeGlobals {
	
	final static Logger logger = Logger.getLogger(ZipCodeRangeProcessor.class);

	/**
	 * Validates whether the user input string contains only 5 digit numbers,Comma,square brackets[] in the
	 * following format : [<5-digit_number>,<5-digit_number>]<space>
	 * Throws an invalid format exception if the string does not comply to the above pattern.
	 * @param userInput  String
	 * @return
	 * @throws InvalidFormatException.
	 */
	public void validateZipCodeRangeArray(String userInput) throws InvalidFormatException {
		userInput = userInput.trim().concat(" ");
		if (userInput.matches("(\\[\\d{5},\\d{5}\\]\\s)+")) {
			String[] zipCodeRangeArray = userInput.split(" ");
			optimizeZipCodeRange(zipCodeRangeArray);
			if(logger.isDebugEnabled()) {
				logger.debug("zipcode range arrray length: " + zipCodeRangeArray.length);
				logger.debug("ZipCode Range Inputs: " + Arrays.toString(zipCodeRangeArray));
				}
		} else {
			throw new InvalidFormatException(
					"The input is invalid.Please re-enter a valid 5 digit zipcode in the given format :[zipCodelowerbound,zipcodeUpperBoundvalue] [zipCodelowerbound,zipcodeUpperBoundvalue]...etc");
		}
	}

	/**
	 * This method validates each zipCode range whether the lowerBound value is less
	 * than upper Bound value, and updates the optimized zipcode range array with
	 * valid ones.
	 * 
	 * @param zipCodeRangeArray - this is the string array obtained from the user entry.
	 */
	private void optimizeZipCodeRange(String[] zipCodeRangeArray) {
		optimizedZipCodeArray.clear();
		ArrayList<String> zipCodeArray = new ArrayList<>(Arrays.asList(zipCodeRangeArray));
		zipCodeArray.forEach(zipcodeRange -> {
			Integer[] zipCodes = Arrays.stream(zipcodeRange.substring(1, zipcodeRange.length() - 1).split(","))
					.map(Integer::parseInt).toArray(Integer[]::new);
			if (zipCodes[0] > zipCodes[1]) {
				logger.error("The lower bound value is greater than the upper bound value for the zipcode range : "
								+ zipcodeRange);
			} else {
				ZipCodeRangeDTO validZipCodeRange = new ZipCodeRangeDTO(zipCodes[0], zipCodes[1]);
				updateOptimizedZipCodeArray(validZipCodeRange);
			}
		});
		deletedZipCodesArray.clear();
		logger.info("Final Optimised Zip Code Range Array is: ");
		StringBuilder sb = new StringBuilder("");
		optimizedZipCodeArray.forEach(obj -> {
			sb.append("[").append(obj.getLowerBound().toString()).append(",").append(obj.getUpperBound().toString()).append("] ");
		});
		logger.info(sb.toString());
	}

	/**
	 * This method updates the optimized zipcode range array with a given valid
	 * zipcode range by comparing the lowerbound/upperBound values with the existing
	 * ones in the array.
	 * 
	 * @param currentZipCodeRange
	 *            - this is a valid zipcode range with valid lower and upperbound
	 *            values to be added to the final list.
	 * @return
	 */
	private void updateOptimizedZipCodeArray(ZipCodeRangeDTO newZipCodeRange) {
		addZipCodeRange[0] = true;
		if (optimizedZipCodeArray.size() > 0) {
			optimizedZipCodeArray.forEach(zipCodeObj -> {
				Integer lbValue = 0;
				Integer ubValue = 0;

				if (newZipCodeRange.getLowerBound().compareTo(zipCodeObj.getLowerBound()) < 0) {
					lbValue = newZipCodeRange.getLowerBound();
					if (newZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) < 0
							&& newZipCodeRange.getUpperBound().compareTo(zipCodeObj.getLowerBound()) > 0) {
						zipCodeObj.setLowerBound(lbValue);// update an existing zipcode range and do not add the new
															// zipCodeRange;
						addZipCodeRange[0] = false;
					} else if (newZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) > 0) {
						ubValue = newZipCodeRange.getUpperBound();
						deletedZipCodesArray.add(zipCodeObj);// remove an existing zipcode range and add the new
																// zipCodeRange
					}

				} else if (newZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) > 0) {
					ubValue = newZipCodeRange.getUpperBound();
					if (newZipCodeRange.getLowerBound().compareTo(zipCodeObj.getUpperBound()) <= 0) {
						zipCodeObj.setUpperBound(ubValue);
						addZipCodeRange[0] = false;
					}

				} else {
					addZipCodeRange[0] = false; // dont add the new zipcode range since the it range falls within the
												// existing zipcode range.
				}
			});
			if (deletedZipCodesArray.size() > 0)
				optimizedZipCodeArray.removeAll(deletedZipCodesArray);
			if (addZipCodeRange[0])
				optimizedZipCodeArray.add(newZipCodeRange);

		} else {
			optimizedZipCodeArray.add(newZipCodeRange);
		}

	}

}
