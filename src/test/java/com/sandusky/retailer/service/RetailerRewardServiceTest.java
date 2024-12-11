package com.sandusky.retailer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.enums.RewardPointConstants;
import com.sandusky.retailer.model.CustomerTransaction;
import com.sandusky.retailer.repository.CustomerTransactionRepository;

@SpringBootTest
class RetailerRewardServiceTest {
	@Autowired
	private RetailerRewardService retailerRewardService;
	@MockBean
	private CustomerTransactionRepository customerTransactionRepository;
	private List<CustomerTransaction> customerTransactionsList;
	private static List<CustomerTransaction> customerTransactionsTotalList;

	@BeforeEach
	void setUp() throws Exception {
		customerTransactionsList = new ArrayList<>();
		customerTransactionsList.add(new CustomerTransaction(RewardPointConstants.CUSTOMER_TWO.getValue(), 100d));
	}

	@AfterEach
	void tearDown() throws Exception {
		customerTransactionsList.clear();
		customerTransactionsList = null;
	}

	@BeforeAll
	static void setupBeforeClass() {
		CustomerTransaction customerTransactionTotal = new CustomerTransaction(
				RewardPointConstants.CUSTOMER_ONE.getValue(), 110d);
		customerTransactionsTotalList = new ArrayList<>();
		customerTransactionsTotalList.add(customerTransactionTotal);
		customerTransactionTotal = new CustomerTransaction(RewardPointConstants.CUSTOMER_ONE.getValue(), 120d);
		LocalDateTime localDateTime = customerTransactionTotal.getTransactionDate();
		customerTransactionTotal.setTransactionDate(localDateTime.minusMonths(1));
		customerTransactionsTotalList.add(customerTransactionTotal);
	}

	@AfterAll
	static void tearDownAfterClass() {
		customerTransactionsTotalList.clear();
		customerTransactionsTotalList = null;
	}

	@Test
	public void testPostRewardsRequest() {
		CustomerTransaction customerTransaction = new CustomerTransaction(RewardPointConstants.CUSTOMER_ONE.getValue(),
				50d);
		customerTransaction.setId("1");
		when(customerTransactionRepository.save(Mockito.any(CustomerTransaction.class)))
				.thenReturn(customerTransaction);
		CustomerRewardRequest customerRewardRequest = new CustomerRewardRequest(
				RewardPointConstants.CUSTOMER_ONE.getValue(), 50d);
		CustomerTransaction savedCustomerTransaction = retailerRewardService.postRewardPoints(customerRewardRequest);
		assertEquals("1", savedCustomerTransaction.getId());
		assertEquals(RewardPointConstants.CUSTOMER_ONE.getValue(), savedCustomerTransaction.getCustomerId());
	}

	@Test
	public void testMonthlyRewardsTotal() {
		when(customerTransactionRepository.findTransactionsByCustomerIdAndDateBetween(Mockito.anyString(),
				Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
				.thenReturn(customerTransactionsList);
		double monthlyRewardsTotal = retailerRewardService
				.getMonthlyRewardsTotal(RewardPointConstants.CUSTOMER_ONE.getValue(), 12, 2024);
		assertEquals(50d, monthlyRewardsTotal);
	}

	@Test
	public void testRewardsTotal() {
		when(customerTransactionRepository.findTransactionsByCustomerIdAndDate(Mockito.anyString(),
				Mockito.any(LocalDateTime.class))).thenReturn(customerTransactionsTotalList);
		double rewardsTotal = retailerRewardService.getRewardsTotal(RewardPointConstants.CUSTOMER_ONE.getValue());
		assertEquals(310d, rewardsTotal);
	}

	@Test
	public void testRewardsTotalZero() {
		List<CustomerTransaction> customerTransactionsZeroTotalList = new ArrayList<>();
		customerTransactionsZeroTotalList
				.add(new CustomerTransaction(RewardPointConstants.CUSTOMER_THREE.getValue(), 50d));
		when(customerTransactionRepository.findTransactionsByCustomerIdAndDate(Mockito.anyString(),
				Mockito.any(LocalDateTime.class))).thenReturn(customerTransactionsZeroTotalList);
		double rewardsTotal = retailerRewardService.getRewardsTotal(RewardPointConstants.CUSTOMER_THREE.getValue());
		assertEquals(0d, rewardsTotal);
	}
}