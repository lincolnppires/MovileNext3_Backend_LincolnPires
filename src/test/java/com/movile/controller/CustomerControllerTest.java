package com.movile.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;

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

}
