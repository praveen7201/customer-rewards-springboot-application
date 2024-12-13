package com.sandusky.retailer.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.model.CustomerTransaction;
import com.sandusky.retailer.repository.CustomerTransactionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RetailerRewardService {
	private final CustomerTransactionRepository customerTransactionRepository;

	public CustomerTransaction postRewardPoints(CustomerRewardRequest customerRewardRequest) {
		CustomerTransaction customerTransaction = new CustomerTransaction(customerRewardRequest.getCustomerId(),
				customerRewardRequest.getAmountSpent());
		return customerTransactionRepository.save(customerTransaction);
	}

	public double getMonthlyRewardsTotal(String customerId, int month, int year) {
		LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime endDate = startDate.plus(1, ChronoUnit.MONTHS);

		List<CustomerTransaction> transactions = customerTransactionRepository
				.findTransactionsByCustomerIdAndDateBetween(customerId, startDate, endDate);
		double rewardPointsTotal = 0;
		if (!transactions.isEmpty()) {
			rewardPointsTotal = calculatePoints(
					(transactions.stream().mapToDouble(transaction -> transaction.getAmountSpent()).sum()));
		}
		return rewardPointsTotal;
	}

	public double getRewardsTotal(String customerId) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime threeMonthsAgo = now.minus(3, ChronoUnit.MONTHS);
		double rewardsTotal = 0;
		List<CustomerTransaction> transactions = customerTransactionRepository
				.findTransactionsByCustomerIdAndDate(customerId, threeMonthsAgo);
		if (!transactions.isEmpty()) {
			Map<Integer, Double> groupedByMonth = transactions.stream()
					.collect(Collectors.groupingBy(t -> t.getTransactionDate().getMonthValue(), // Group by Month
							Collectors.summingDouble(CustomerTransaction::getAmountSpent) // Sum the amounts by Month
					));
			double rewardsTotalGrouped = groupedByMonth.entrySet().stream().map(Map.Entry::getValue)
					.collect(Collectors.summingDouble(Double::valueOf));
			rewardsTotal = calculatePoints(rewardsTotalGrouped);
		}
		return rewardsTotal;
	}

	private double calculatePoints(double amount) {
		if (amount > 100) {
			return ((amount - 100) * 2 + 50); // 2 points for every dollar over 100 + 1 point for
												// 50-100
		} else if (amount > 50) {
			return (amount - 50); // 1 point for every dollar over 50
		} else {
			return 0; // No points
		}
	}
}