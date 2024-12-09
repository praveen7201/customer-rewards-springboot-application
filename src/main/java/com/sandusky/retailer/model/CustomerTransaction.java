package com.sandusky.retailer.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Document(collection = "customerTransaction")
public class CustomerTransaction {
	@Id
    private String id;
    private String customerId;
    private double amountSpent;
    private double rewardPoints;
    private LocalDateTime transactionDate;
	public CustomerTransaction(String customerId, double amountSpent, double rewardPoints) {
		this.customerId = customerId;
		this.amountSpent = amountSpent;
		this.rewardPoints = rewardPoints;
		transactionDate = LocalDateTime.now();
	}
}