package com.sandusky.retailer.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CustomerRewardRequest {
	public String customerId;
	public Double amountSpent;
}
