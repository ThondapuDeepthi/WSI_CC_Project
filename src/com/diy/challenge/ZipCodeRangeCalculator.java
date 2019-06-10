package com.diy.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.diy.challenge.exception.InvalidFormatException;

/**
 * @author Thondapu Deepthi
 *
 */
public class ZipCodeRangeCalculator {
	public static ArrayList<ZipCodeRangeDTO> optimizedZipCodeArray = new ArrayList<>();
	public static ArrayList<ZipCodeRangeDTO> deletedZipCodesArray = new ArrayList<>();
	public static Integer lbValue = 0;
	public static Integer ubValue = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Zip Code ranges: ");
		String userInput = sc.nextLine();

		try {
			validateZipCodeRangeArray(userInput);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}

	}

	/**
	 * Validates the user input array, to contain only numbers, ',', '[', ']' in the
	 * following format : [<5-digit_number>,<5-digit_number>]<space>
	 * 
	 * @param userInput
	 *            String
	 * @return
	 * @throws InvalidFormatException.
	 */
	public static void validateZipCodeRangeArray(String userInput) throws Exception {
		// TODO Auto-generated method stub
		userInput = userInput.trim().concat(" ");
		if (userInput.matches("(\\[\\d{5},\\d{5}\\]\\s)+")) {
			String[] zipCodeRangeArray = userInput.split(" ");
//			System.out.println("zipcode range arrray length: " + zipCodeRangeArray.length);
//			System.out.println("ZipCode Range Inputs: " + Arrays.toString(zipCodeRangeArray));
			optimizeZipCodeRange(zipCodeRangeArray);
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
	 * @param zipCodeRangeArray
	 *            - this is the string array obtained from the user entry.
	 * @return
	 */
	private static void optimizeZipCodeRange(String[] zipCodeRangeArray) {
		optimizedZipCodeArray.clear();
		ArrayList<String> zipCodeArray = new ArrayList<>(Arrays.asList(zipCodeRangeArray));
		zipCodeArray.forEach(zipcodeRange -> {
			Integer[] zipCodes = Arrays.stream(zipcodeRange.substring(1, zipcodeRange.length() - 1).split(","))
					.map(Integer::parseInt).toArray(Integer[]::new);
			if (zipCodes[0] > zipCodes[1]) {
				System.out
						.println("The lower bound value is greater than the upper bound value for the zipcode range : "
								+ zipcodeRange);
			} else {
				ZipCodeRangeDTO validZipCodeRange = new ZipCodeRangeDTO(zipCodes[0], zipCodes[1]);
				updateOptimizedZipCodeArray(validZipCodeRange);
			}
			// System.out.println("Optimised Arraylist for each item added:
			// "+Arrays.toString(optimizedZipCodeArray.toArray()));
		});
		deletedZipCodesArray.clear();
		System.out.println("Final Optimised Zip Code Range Array is: ");
		optimizedZipCodeArray.forEach(obj -> {
			System.out.print("[" + obj.getLowerBound().toString() + "," + obj.getUpperBound().toString() + "] ");
		});

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
	private static void updateOptimizedZipCodeArray(ZipCodeRangeDTO currentZipCodeRange) {
		if (optimizedZipCodeArray.size() > 0) {
			optimizedZipCodeArray.forEach(zipCodeObj -> {
				lbValue = 0;
				ubValue = 0;
				if (currentZipCodeRange.getLowerBound().compareTo(zipCodeObj.getLowerBound()) < 0) {
					lbValue = currentZipCodeRange.getLowerBound();
					if (currentZipCodeRange.getUpperBound().compareTo(zipCodeObj.getLowerBound()) < 0) {
						ubValue = currentZipCodeRange.getUpperBound();
						// add to the list and return;

						return;
					} else if (currentZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) < 0) {
						zipCodeObj.setLowerBound(lbValue);
						return;
					} else if (currentZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) > 0) {
						ubValue = currentZipCodeRange.getUpperBound();
						deletedZipCodesArray.add(zipCodeObj);
						return;
					}

				} else if (currentZipCodeRange.getUpperBound().compareTo(zipCodeObj.getUpperBound()) > 0) {
					ubValue = currentZipCodeRange.getUpperBound();
					if (currentZipCodeRange.getLowerBound().compareTo(zipCodeObj.getUpperBound()) > 0) {
						lbValue = currentZipCodeRange.getLowerBound();
						// add to the list and return
						// optimizedZipCodeArray.add(currentZipCodeRange);
						return;
					} else {
						zipCodeObj.setUpperBound(ubValue);
						return;
					}

				}
			});
			if (deletedZipCodesArray.size() > 0)
				optimizedZipCodeArray.removeAll(deletedZipCodesArray);
			if (lbValue.compareTo(currentZipCodeRange.getLowerBound()) == 0
					&& ubValue.compareTo(currentZipCodeRange.getUpperBound()) == 0)
				optimizedZipCodeArray.add(currentZipCodeRange);

		} else {
			optimizedZipCodeArray.add(currentZipCodeRange);
		}

	}

}
