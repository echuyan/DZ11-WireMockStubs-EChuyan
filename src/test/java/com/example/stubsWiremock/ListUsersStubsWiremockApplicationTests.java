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
class ListUsersStubsWiremockApplicationTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

	@BeforeAll
	public static void setUpMockServer() {
	wireMockServer.start();
	WireMock.configureFor("localhost",5050);
	WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/?page=2"))
			.willReturn(WireMock.aResponse()
			.withStatus(200)
					.withBody("{\n" +
							"    \"page\": 2,\n" +
							"    \"per_page\": 6,\n" +
							"    \"total\": 12,\n" +
							"    \"total_pages\": 2,\n" +
							"    \"data\": [\n" +
							"        {\n" +
							"            \"id\": 7,\n" +
							"            \"email\": \"michael.lawson@reqres.in\",\n" +
							"            \"first_name\": \"Michael\",\n" +
							"            \"last_name\": \"Lawson\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/7-image.jpg\"\n" +
							"        },\n" +
							"        {\n" +
							"            \"id\": 8,\n" +
							"            \"email\": \"lindsay.ferguson@reqres.in\",\n" +
							"            \"first_name\": \"Lindsay\",\n" +
							"            \"last_name\": \"Ferguson\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/8-image.jpg\"\n" +
							"        },\n" +
							"        {\n" +
							"            \"id\": 9,\n" +
							"            \"email\": \"tobias.funke@reqres.in\",\n" +
							"            \"first_name\": \"Tobias\",\n" +
							"            \"last_name\": \"Funke\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/9-image.jpg\"\n" +
							"        },\n" +
							"        {\n" +
							"            \"id\": 10,\n" +
							"            \"email\": \"byron.fields@reqres.in\",\n" +
							"            \"first_name\": \"Byron\",\n" +
							"            \"last_name\": \"Fields\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/10-image.jpg\"\n" +
							"        },\n" +
							"        {\n" +
							"            \"id\": 11,\n" +
							"            \"email\": \"george.edwards@reqres.in\",\n" +
							"            \"first_name\": \"George\",\n" +
							"            \"last_name\": \"Edwards\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/11-image.jpg\"\n" +
							"        },\n" +
							"        {\n" +
							"            \"id\": 12,\n" +
							"            \"email\": \"rachel.howell@reqres.in\",\n" +
							"            \"first_name\": \"Rachel\",\n" +
							"            \"last_name\": \"Howell\",\n" +
							"            \"avatar\": \"https://reqres.in/img/faces/12-image.jpg\"\n" +
							"        }\n" +
							"    ],\n" +
							"    \"support\": {\n" +
							"        \"url\": \"https://reqres.in/#support-heading\",\n" +
							"        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
							"    }\n" +
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
				.get("http://localhost:5050/api/users/?page=2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("2", response.jsonPath().getString("page"));
		Assertions.assertEquals("12", response.jsonPath().getString("total"));
		Assertions.assertEquals("michael.lawson@reqres.in", response.jsonPath().getString("data[0].email"));
	}

}
