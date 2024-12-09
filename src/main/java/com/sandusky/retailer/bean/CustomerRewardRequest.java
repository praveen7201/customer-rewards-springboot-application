package com.sandusky.retailer.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomerRewardRequest {
	public String customerId;
	public Double amountSpent;
	public Double rewardPoints;
}
