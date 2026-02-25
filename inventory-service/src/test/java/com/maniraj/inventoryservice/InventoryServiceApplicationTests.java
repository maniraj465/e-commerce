package com.maniraj.inventoryservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.mysql.MySQLContainer;

import io.restassured.RestAssured;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldReadInventory() {
		var response = RestAssured.given()
				.queryParam("skuCode", "iphone_15")
				.queryParam("quantity", 2)
				.when()
				.get("/api/inventory")
				.then()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		Assertions.assertTrue(response);

		var negativeResponse = RestAssured.given()
				.queryParam("skuCode", "iphone_15")
				.queryParam("quantity", 200)
				.when()
				.get("/api/inventory")
				.then()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		Assertions.assertFalse(negativeResponse);
	}

}
