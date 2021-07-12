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
class SingleResourceStubsWiremockApplicationTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

	@BeforeAll
	public static void setUpMockServer() {
	wireMockServer.start();
	WireMock.configureFor("localhost",5050);
	WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/unknown/2"))
			.willReturn(WireMock.aResponse()
			.withStatus(200)
					.withBody("{\n" +
							"    \"data\": {\n" +
							"        \"id\": 2,\n" +
							"        \"name\": \"fuchsia rose\",\n" +
							"        \"year\": 2001,\n" +
							"        \"color\": \"#C74375\",\n" +
							"        \"pantone_value\": \"17-2031\"\n" +
							"    },\n" +
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
				.get("http://localhost:5050/api/unknown/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("2", response.jsonPath().getString("data.id"));
		Assertions.assertEquals("2001", response.jsonPath().getString("data.year"));
		Assertions.assertEquals("fuchsia rose", response.jsonPath().getString("data.name"));
	}

}
