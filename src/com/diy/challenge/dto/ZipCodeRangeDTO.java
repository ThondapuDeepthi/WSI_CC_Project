package com.diy.challenge.dto;

import java.util.Objects;
/**
 * @author Thondapu Deepthi
 *
 */
public class ZipCodeRangeDTO {

	Integer lowerBound;
	Integer upperBound;

	public ZipCodeRangeDTO(Integer lb, Integer ub) {
		lowerBound = lb;
		upperBound = ub;
	}

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;
		if (!(o instanceof ZipCodeRangeDTO)) {
			return false;
		}
		ZipCodeRangeDTO zipCodeRange = (ZipCodeRangeDTO) o;
		return Objects.equals(lowerBound, zipCodeRange.lowerBound)
				&& Objects.equals(upperBound, zipCodeRange.upperBound);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lowerBound, upperBound);
	}
	// //Comparator class for sorting the zipCodeRangeArraylist by lowerBound Value
	// public static class OrderByLowerBound implements Comparator<ZipCodeRangeDTO>
	// {
	//
	// public int compare(ZipCodeRangeDTO o1,
	// ZipCodeRangeDTO o2) {
	// return o1.getLowerBound().compareTo(o2.getLowerBound());
	// }
	// }

}
