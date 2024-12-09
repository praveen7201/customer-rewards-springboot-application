package com.sandusky.retailer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.service.RetailerRewardService;
import com.sandusky.retailer.util.CustomerRequestUtil;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/customerRewards")
public class CustomerRewardController {
	private static final String THREE_MONTHLY_REWARDS_ERROR_MSG = "Customer Id is null or empty";
	private static final String REWARD_POINTS_SUCCESSFULLY_ADDED = "Reward Points successfully added to Customer";
	private static final String INVALID_CUSTOMER_REQUEST_OBJECT = "Not a valid Request, either some or the fields are null or 0";
	private final RetailerRewardService retailerRewardService;
	private final CustomerRequestUtil customerRequestUtil;
	@PostMapping("/addPoints")
	public ResponseEntity<String> addRewardPoints(@RequestBody CustomerRewardRequest customerRewardRequest) {
		ResponseEntity<String> responseEntity;
		if(customerRequestUtil.validAddRewardPointsRequest(customerRewardRequest)) {
			retailerRewardService.postRewardPoints(customerRewardRequest);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED)
					.body(REWARD_POINTS_SUCCESSFULLY_ADDED);
		} else {
			responseEntity = ResponseEntity.badRequest().body(INVALID_CUSTOMER_REQUEST_OBJECT);
		}
		return responseEntity;
	}

	@GetMapping("/{customerId}/monthly/{month}/{year}")
	public ResponseEntity<Object> getMonthlyRewards(@PathVariable String customerId, @PathVariable int month, @PathVariable int year) {
		ResponseEntity<Object> responseEntity;
		if(customerRequestUtil.validMonthlyRewardsRequest(customerId, month, year)) {
			int monthlyRewards = retailerRewardService.getMonthlyRewardPoints(customerId, month, year);
			responseEntity = ResponseEntity.ok(monthlyRewards);
		} else {
			responseEntity = ResponseEntity.badRequest().body(INVALID_CUSTOMER_REQUEST_OBJECT);
		}
		return responseEntity; 
	}

    @GetMapping("/{customerId}/total")
    public ResponseEntity<Object> getThreeMonthRewards(@PathVariable String customerId) {
    	ResponseEntity<Object> responseEntity;
    	if(StringUtils.isNotBlank(customerId)) {
    		responseEntity = ResponseEntity.ok(retailerRewardService.getThreeMonthTotal(customerId));
    	} else {
    		responseEntity = ResponseEntity.badRequest().body(THREE_MONTHLY_REWARDS_ERROR_MSG);
    	}
        return responseEntity;
    }
}