package com.sandusky.retailer.enums;

public enum RewardPointConstants {	
	THREE_MONTHLY_REWARDS_ERROR_MSG("Customer Id is null or empty"),
	EXCEPTION_DURING_PROCESSING_REQUEST("Application Exception during processing request"),
	INVALID_CUSTOMER_ADD_REWARDS_REQUEST("Invalid Customer Add Rewards Request, please recheck request: {}"),
	INVALID_GET_MONTHLY_REWARDS_REQUEST("Invalid Get Monthly Rewards Request, CustomerId {}, Month {}, Year {}"),
	REWARD_POINTS_SUCCESSFULLY_ADDED_RESPONSE("Reward Points successfully added to Customer"),
	INVALID_CUSTOMER_REQUEST_RESPONSE("Not a valid Request, either some or the fields are null or 0"),
	
	DUPLICATE_KEY_ERROR("Duplicate key error: "),
	ENDPOINT_NOT_FOUND_MSG("Endpoint not found: "),
	MONGO_QUERY_EXCEPTION_MSG("Mongo Database Query Exception"),
	METHOD_ARGUMENT_NOT_VALID_EXCEPTION("Method Argument Not valid exception"),

	CUSTOMER_REWARDS_CUSTOMER_MONTHLY_URI("/customerRewards/customerOne/monthly/"),
	CUSTOMER_REWARDS_CUSTOMER_TOTAL_URI("/customerRewards/customerOne/total"),
	CUSTOMER_REWARDS_CUSTOMER_TOTAL_NULL_URI("/customerRewards/null/total"),
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