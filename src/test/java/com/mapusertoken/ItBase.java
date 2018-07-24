package com.mapusertoken;

import static io.restassured.RestAssured.given;

import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;

import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mapusertoken.model.MapUserToken;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItBase {
	protected Random random = new Random();

	@LocalServerPort
	protected int port;

	public void setup() throws Exception {
		RestAssured.port = port;

	}

	public void cleanup() throws Exception {

	}

	@SuppressWarnings("unchecked")
	public <T> T createResource(T entity, String path, Object... arg1) {
		String location = given().contentType(ContentType.JSON).body(entity).log().body().post(path, arg1).then().log()
				.body().statusCode(201).extract().header(HttpHeaders.LOCATION);
		return (T) given().get(location).then().log().body().statusCode(200).extract().as(entity.getClass());
	}

	protected MapUserToken buildMap() {

		MapUserToken item = new MapUserToken().username("username-" + UUID.randomUUID())
				.token("token-" + UUID.randomUUID());
		return item;
	}

}
