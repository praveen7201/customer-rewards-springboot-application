package com.sandusky.retailer.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.sandusky.retailer.bean.CustomerRewardRequest;

import io.micrometer.common.util.StringUtils;
@Component
public class CustomerRequestUtil {

	public boolean validAddRewardPointsRequest(CustomerRewardRequest customerRewardRequest) {
		boolean validRequest = true;
		if (customerRewardRequest == null || (StringUtils.isBlank(customerRewardRequest.getCustomerId())
				|| customerRewardRequest.getAmountSpent() == null || customerRewardRequest.getAmountSpent() == 0
				|| customerRewardRequest.getRewardPoints() == null || customerRewardRequest.getRewardPoints() == 0)) {
			validRequest = false;
		}
		return validRequest;
	}

	public boolean validMonthlyRewardsRequest(String customerId, int month, int year) {
		int currentYear = LocalDate.now().getYear();
		boolean validRequest = true;
		if (StringUtils.isBlank(customerId) || (month < 1 && month > 12) || (year < 1900 && year > currentYear)) {
			validRequest = false;
		}
		return validRequest;
	}
}