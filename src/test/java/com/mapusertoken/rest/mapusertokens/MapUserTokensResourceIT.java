package com.mapusertoken.rest.mapusertokens;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mapusertoken.ItBase;
import com.mapusertoken.model.MapUserToken;
import com.mapusertoken.repository.MapUserTokenRepository;
import com.mapusertoken.rest.ApiConstants;

import io.restassured.http.ContentType;

public class MapUserTokensResourceIT extends ItBase {

	@Autowired
	private MapUserTokenRepository repository;

	private MapUserToken map1;

	private MapUserToken map2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create map1
		map1 = buildMap();
		map1 = repository.save(map1);
		// create role 2
		map2 = repository.save(buildMap());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		MapUserToken create = buildMap();
		int id = given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.MAP_USER_TOKEN_COLLECTION).then().log().body().statusCode(200).extract().body()
				.path("id");

		// check that the role has been saved
		MapUserToken actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getUsername(), is(equalTo(create.getUsername())));
		assertThat(actual.getToken(), is(equalTo(create.getToken())));
	}

	@Test
	public void createUsernameNull() {
		MapUserToken create = buildMap().username(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MAP_USER_TOKEN_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createTokenNull() {
		MapUserToken create = buildMap().token(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MAP_USER_TOKEN_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createUsernameVide() {
		MapUserToken create = buildMap();
		create.setUsername("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MAP_USER_TOKEN_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createMemeUsername() {
		MapUserToken create = buildMap();
		create.setUsername(map1.getUsername());
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MAP_USER_TOKEN_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createTokenVide() {
		MapUserToken create = buildMap().token("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MAP_USER_TOKEN_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void getByUsername() {
		given().get(ApiConstants.MAP_USER_TOKEN_ITEM, map2.getUsername()).then().log().body().statusCode(200)
				.body("id", is(equalTo(map2.getId().intValue()))).body("username", is(equalTo(map2.getUsername())))
				.body("token", is(equalTo(map2.getToken())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.MAP_USER_TOKEN_ITEM, UUID.randomUUID().toString()).then().statusCode(404);
	}

	@Test
	public void deleteByUsername() {
		given().delete(ApiConstants.MAP_USER_TOKEN_ITEM, map1.getUsername()).then().statusCode(200);

		// check that the role has been deleted
		MapUserToken actual = repository.findOne(map1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteByUsernameNotFound() {
		given().delete(ApiConstants.MAP_USER_TOKEN_ITEM, UUID.randomUUID().toString()).then().statusCode(404);
	}

}
