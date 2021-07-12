package com.example.stubsWiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;


@SpringBootTest
class CreateUserStubsWiremockApplicationTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

	@BeforeAll
	public static void setUpMockServer() {
	wireMockServer.start();
	WireMock.configureFor("localhost",5050);
	WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/users"))
			.willReturn(WireMock.aResponse()
			.withStatus(201)
					.withBody("{\n" +
							"    \"name\": \"morpheus\",\n" +
							"    \"job\": \"leader\",\n" +
							"    \"id\": \"498\",\n" +
							"    \"createdAt\": \"2021-07-12T15:34:47.033Z\"\n" +
							"}")));

	}

	@AfterAll
	public static void tearDownMockServer() {
		wireMockServer.stop();
	}


	@Test
	void contextLoads() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.body("{\n    \"name\": \"morpheus\",\n    \"job\": \"leader\"\n}")
				.post("http://localhost:5050/api/users")
				.then()
				.extract().response();

		Assertions.assertEquals(201, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
	}

}
