package com.sandusky.retailer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sandusky.retailer.model.CustomerTransaction;

public interface CustomerTransactionRepository extends MongoRepository<CustomerTransaction, String> {
	List<CustomerTransaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDateTime startDate,
			LocalDateTime endDate);
}
