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
    private LocalDateTime transactionDate;
	public CustomerTransaction(String customerId, double amountSpent) {
		this.customerId = customerId;
		this.amountSpent = amountSpent;
		transactionDate = LocalDateTime.now();
	}
}