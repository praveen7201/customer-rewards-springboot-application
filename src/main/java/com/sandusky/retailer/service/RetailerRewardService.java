package com.sandusky.retailer.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.model.CustomerTransaction;
import com.sandusky.retailer.repository.CustomerTransactionRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class RetailerRewardService {
	private final CustomerTransactionRepository customerTransactionRepository;

	public void postRewardPoints(CustomerRewardRequest customerRewardRequest) {
		CustomerTransaction customerTransaction = new CustomerTransaction(customerRewardRequest.getCustomerId(),
				customerRewardRequest.getAmountSpent(), customerRewardRequest.getRewardPoints());
		customerTransactionRepository.save(customerTransaction);
	}
	
	public int getMonthlyRewardPoints(String customerId, int month, int year) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plus(1, ChronoUnit.MONTHS);

        List<CustomerTransaction> transactions = customerTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

        return transactions.stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmountSpent(), transaction.getRewardPoints()))
                .sum();
    }

    public int getThreeMonthTotal(String customerId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = now.minus(3, ChronoUnit.MONTHS);

        List<CustomerTransaction> transactions = customerTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, threeMonthsAgo, now);

        return transactions.stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmountSpent(), transaction.getRewardPoints()))
                .sum();
    }
    
    private int calculatePoints(double amount, double rewardPoints) {
        if (amount > 100) {
            return (int) ((amount - 100) * rewardPoints + 50); // 2 points for every dollar over 100 + 1 point for 50-100
        } else if (amount > 50) {
            return (int) ((amount - 50)); // 1 point for every dollar over 50
        } else {
            return 0; // No points
        }
    }
}