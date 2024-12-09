package com.sandusky.retailer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sandusky.retailer.bean.CustomerRewardRequest;
import com.sandusky.retailer.enums.RewardPointConstants;
import com.sandusky.retailer.model.CustomerTransaction;
import com.sandusky.retailer.repository.CustomerTransactionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRewardControllerIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	private CustomerRewardRequest customerRewardRequestOne;
	private CustomerRewardRequest customerRewardRequestTwo;
	private CustomerRewardRequest badCustomerRewardRequest;
	@Autowired
	private CustomerTransactionRepository customerTransactionRepository;

	@BeforeEach
	void setUp() throws Exception {
		// Setup test data
		customerRewardRequestOne = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_ONE.getValue(), 200d, 2d);
		customerRewardRequestTwo = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_TWO.getValue(), 100d, 2d);
		badCustomerRewardRequest = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_THREE.getValue(), null,
				null);
	}

	@AfterEach
	void tearDown() throws Exception {
		customerRewardRequestOne = null;
		customerRewardRequestTwo = null;
		customerTransactionRepository.deleteByCustomerId(RewardPointConstants.CUSTOMER_ONE.getValue());
		customerTransactionRepository.deleteByCustomerId(RewardPointConstants.CUSTOMER_TWO.getValue());
	}

	@Test
	public void testAddRewardPoints() {
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_ADD_POINTS_URI.getValue();
		ResponseEntity<String> addRewardPointsResponse = restTemplate.postForEntity(url, customerRewardRequestTwo,
				String.class);
		assertEquals(HttpStatus.CREATED, addRewardPointsResponse.getStatusCode());
	}

	@Test
	public void testAddRewardPointsBadRequest() {
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_ADD_POINTS_URI.getValue();
		ResponseEntity<String> addRewardPointsResponse = restTemplate.postForEntity(url, badCustomerRewardRequest,
				String.class);
		assertEquals(HttpStatus.BAD_REQUEST, addRewardPointsResponse.getStatusCode());
	}

	@Test
	public void testMonthlyRewardPoints() {
		CustomerTransaction customerTransactionOne = new CustomerTransaction(customerRewardRequestOne.getCustomerId(),
				customerRewardRequestOne.getAmountSpent(), customerRewardRequestOne.getRewardPoints());
		CustomerTransaction customerTransactionTwo = new CustomerTransaction(customerRewardRequestTwo.customerId,
				customerRewardRequestTwo.getAmountSpent(), customerRewardRequestTwo.getRewardPoints());
		customerTransactionRepository.save(customerTransactionOne);
		customerTransactionRepository.save(customerTransactionTwo);

		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_MONTHLY_URI.getValue()
				+ LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
		ResponseEntity<Integer> rewardPointsResponse = restTemplate.getForEntity(url, Integer.class);

		// Verify
		assertThat(rewardPointsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testThreeMonthTotalRewardPoints() {
		// Setup test data
		CustomerTransaction customerTransactionOne = new CustomerTransaction(customerRewardRequestOne.customerId,
				customerRewardRequestOne.getAmountSpent(), customerRewardRequestOne.getRewardPoints());
		CustomerTransaction customerTransactionTwo = new CustomerTransaction(customerRewardRequestTwo.customerId,
				customerRewardRequestTwo.getAmountSpent(), customerRewardRequestTwo.getRewardPoints());
		customerTransactionRepository.save(customerTransactionOne);
		customerTransactionRepository.save(customerTransactionTwo);
		customerRewardRequestOne = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_ONE.getValue(), 250d, 2d);
		customerTransactionOne = new CustomerTransaction(customerRewardRequestOne.customerId,
				customerRewardRequestOne.getAmountSpent(), customerRewardRequestOne.getRewardPoints());
		customerTransactionRepository.save(customerTransactionOne);
		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_TOTAL_URI.getValue();
		ResponseEntity<Integer> rewardPointsResponse = restTemplate.getForEntity(url, Integer.class);

		// Verify
		assertThat(rewardPointsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}