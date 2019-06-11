package com.diy.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.diy.challenge.dto.ZipCodeRangeDTO;
import com.diy.challenge.exception.InvalidFormatException;
import com.diy.challenge.processor.ZipCodeRangeProcessor;

/**
 * @author Thondapu Deepthi
 *
 */
public class ZipCodeRangeCalculator {
	
	final static Logger logger = Logger.getLogger(ZipCodeRangeCalculator.class);


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Zip Code ranges in the following format [5-digit-zipcode-lowerbound,5-digit-zipcode-upperbound] [5-digit-zipcode-lowerbound,5-digit-zipcode-upperbound]..");
		
		try {
			String userInput = sc.nextLine();
			ZipCodeRangeProcessor zipCodeProcessor = new ZipCodeRangeProcessor();
			zipCodeProcessor.validateZipCodeRangeArray(userInput);
		} catch (InvalidFormatException e) {
			logger.error(e.getMessage());
		}

	}

	
}
