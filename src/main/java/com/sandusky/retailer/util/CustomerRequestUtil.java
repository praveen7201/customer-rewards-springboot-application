package com.sandusky.retailer.util;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.enums.RewardPointConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerRequestUtil {

	public boolean validAddRewardPointsRequest(CustomerRewardRequest customerRewardRequest) {
		boolean validRequest = true;
		if (customerRewardRequest == null || ("null".equalsIgnoreCase(customerRewardRequest.getCustomerId())
				|| StringUtils.isBlank(customerRewardRequest.getCustomerId())
				|| customerRewardRequest.getAmountSpent() == null || customerRewardRequest.getAmountSpent() == 0)) {
			validRequest = false;
			log.info(RewardPointConstants.INVALID_CUSTOMER_ADD_REWARDS_REQUEST.getValue(), customerRewardRequest);
		}
		return validRequest;
	}

	public boolean validMonthlyRewardsRequest(String customerId, int month, int year) {
		int currentYear = LocalDate.now().getYear();
		boolean validRequest = true;
		if (StringUtils.isBlank(customerId) || (month < 1 || month > 12) || (year < 1900 && year > currentYear)) {
			validRequest = false;
			log.info(RewardPointConstants.INVALID_GET_MONTHLY_REWARDS_REQUEST.getValue(), customerId, month, year);
		}
		return validRequest;
	}

	public boolean validRewardsTotalRequest(String customerId) {
		boolean validRequest = true;
		if ("null".equalsIgnoreCase(customerId) || StringUtils.isBlank(customerId.replace("\"", ""))) {
			validRequest = false;
			log.info(RewardPointConstants.THREE_MONTHLY_REWARDS_ERROR_MSG.getValue());
		}
		return validRequest;
	}
}