/**
 * 
 */
package com.diy.challenge.util;

import java.util.ArrayList;

import com.diy.challenge.dto.ZipCodeRangeDTO;

/**
 * @author Thondapu Deepthi
 *
 */
public interface ZipCodeRangeGlobals {
	public  ArrayList<ZipCodeRangeDTO> optimizedZipCodeArray = new ArrayList<>();
	public  ArrayList<ZipCodeRangeDTO> deletedZipCodesArray = new ArrayList<>();
	public boolean [] addZipCodeRange = new boolean[1];
}
