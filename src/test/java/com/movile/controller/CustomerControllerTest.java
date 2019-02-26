package com.movile.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.movile.resource.OrderResource;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		RestAssured.port = port;			
	}

	@Test
	public void whenGetAllCustomerWithSize3Pagination_thenOK() {		
		String result = given().header("Accept", "application/hal+json").get("/movile/customers")
				.andReturn().asString();
		DocumentContext jsonContext = JsonPath.parse(result);		
		List<Integer> listCustomer = jsonContext.read("$.content[*].customerResource.customer_id");
		assertEquals(3, listCustomer.size());					
	}
	
	@Test
	public void whenGetCustomerOrdersById_thenOK() {		
		String result = given().header("Accept", "application/hal+json").get("/movile/customer/1001")
				.andReturn().asString();
		DocumentContext jsonContext = JsonPath.parse(result);		
		List<Integer> listOrderCustomer = jsonContext.read("$.customerOrderResource.customer_orders[*].id");
		assertEquals(6, listOrderCustomer.size());					
	}
	
	@Test
	public void whenGetUserById_thenNotFound() {		
		Response response = given().header("Accept", "application/hal+json").get("/movile/customer/999999")
				.andReturn();				
		assertEquals(404, response.getStatusCode());				
	}

	@Test
	public void whenPostNewOrderToCustomer_temOk() {
		OrderResource newOrder = new OrderResource(1003l, "Order custormer 1003 - 3", new BigDecimal(3000.00));
		
		given().header("Accept", "application/hal+json").contentType("application/json")
			.body(newOrder).expect().statusCode(201)
			.when().post("/movile/customer/1003/order");
	}

}
