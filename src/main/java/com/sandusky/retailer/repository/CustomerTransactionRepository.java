package com.sandusky.retailer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sandusky.retailer.model.CustomerTransaction;

public interface CustomerTransactionRepository extends MongoRepository<CustomerTransaction, String> {
	void deleteByCustomerId(String customerId);

	@Query("{'customerId': ?0, 'transactionDate': { $gte: ?1 } }")
	List<CustomerTransaction> findTransactionsByCustomerIdAndDate(String customerId, LocalDateTime fromDate);

	@Query("{'customerId': ?0, 'transactionDate' : { $gte: ?1, $lte: ?2 } }")
	List<CustomerTransaction> findTransactionsByCustomerIdAndDateBetween(String customerId, LocalDateTime startDate,
			LocalDateTime endDate);
}
