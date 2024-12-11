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
		customerRewardRequestOne = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_ONE.getValue(), 200d);
		customerRewardRequestTwo = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_TWO.getValue(), 100d);
		badCustomerRewardRequest = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_THREE.getValue(), null);
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
				customerRewardRequestOne.getAmountSpent());
		CustomerTransaction customerTransactionTwo = new CustomerTransaction(customerRewardRequestTwo.customerId,
				customerRewardRequestTwo.getAmountSpent());
		customerTransactionRepository.save(customerTransactionOne);
		customerTransactionRepository.save(customerTransactionTwo);

		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_MONTHLY_URI.getValue()
				+ LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
		ResponseEntity<Double> rewardPointsResponse = restTemplate.getForEntity(url, Double.class);

		// Verify
		assertThat(rewardPointsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testMonthlyRewardPointsBadRequest() {
		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_MONTHLY_URI.getValue() + 0 + "/"
				+ LocalDateTime.now().getYear();
		ResponseEntity<String> rewardPointsResponse = restTemplate.getForEntity(url, String.class);

		// Verify
		assertEquals(RewardPointConstants.INVALID_CUSTOMER_REQUEST_RESPONSE.getValue(), rewardPointsResponse.getBody());
	}

	@Test
	public void testRewardPointsTotal() {
		// Setup test data
		CustomerTransaction customerTransactionOne = new CustomerTransaction(customerRewardRequestOne.customerId,
				customerRewardRequestOne.getAmountSpent());
		CustomerTransaction customerTransactionTwo = new CustomerTransaction(customerRewardRequestTwo.customerId,
				customerRewardRequestTwo.getAmountSpent());
		customerTransactionRepository.save(customerTransactionOne);
		customerTransactionRepository.save(customerTransactionTwo);
		customerRewardRequestOne = new CustomerRewardRequest(RewardPointConstants.CUSTOMER_ONE.getValue(), 250d);
		customerTransactionOne = new CustomerTransaction(customerRewardRequestOne.customerId,
				customerRewardRequestOne.getAmountSpent());
		customerTransactionRepository.save(customerTransactionOne);
		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_TOTAL_URI.getValue();
		ResponseEntity<Integer> rewardPointsResponse = restTemplate.getForEntity(url, Integer.class);

		// Verify
		assertThat(rewardPointsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testRewardPointsTotalBadRequest() {
		// Setup test data
		// Test the API
		String url = RewardPointConstants.HTTP_LOCALHOST.getValue() + port
				+ RewardPointConstants.CUSTOMER_REWARDS_CUSTOMER_TOTAL_NULL_URI.getValue();
		ResponseEntity<String> rewardPointsResponse = restTemplate.getForEntity(url, String.class);

		// Verify
		assertEquals(RewardPointConstants.THREE_MONTHLY_REWARDS_ERROR_MSG.getValue(), rewardPointsResponse.getBody());
	}
}