package com.maniraj.orderservice;

//import com.maniraj.orderservice.stub.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.mysql.MySQLContainer;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

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
	void shouldSubmitOrder() {
		String requestBody = """
				{
				  "skuCode": "iphone_15",
				  "price": 999.99,
				  "quantity": 2
				}
				""";
//		InventoryClientStub.stubInventoryCall("iphone_15", 2);


		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(201)
				.body("message" , Matchers.equalTo("Order placed successfully"));
	}

}
