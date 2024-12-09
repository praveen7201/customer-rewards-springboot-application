package com.sandusky.retailer.enums;

public enum RewardPointConstants {
	THREE_MONTHLY_REWARDS_ERROR_MSG_MSG("Customer Id is null or empty"),
	REWARD_POINTS_SUCCESSFULLY_ADDED_MSG("Reward Points successfully added to Customer"),
	INVALID_CUSTOMER_REQUEST_OBJECT_MSG("Not a valid Request, either some or the fields are null or 0"),

	CUSTOMER_REWARDS_CUSTOMER_MONTHLY_URI("/customerRewards/customerOne/monthly/"),
	CUSTOMER_REWARDS_CUSTOMER_TOTAL_URI("/customerRewards/customerOne/total"),
	CUSTOMER_REWARDS_ADD_POINTS_URI("/customerRewards/addPoints"),
	
	HTTP_LOCALHOST("http://localhost:"),
	
	CUSTOMER_THREE("customerThree"),
	CUSTOMER_TWO("customerTwo"),
	CUSTOMER_ONE("customerOne");
	
	private String value;

	public String getValue() {
		return value;
	}

	RewardPointConstants(String value) {
		this.value=value;
	}
}