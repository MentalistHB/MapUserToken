package com.mapusertoken.rest.mapusertokens;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mapusertoken.model.MapUserToken;
import com.mapusertoken.rest.ApiConstants;
import com.mapusertoken.service.MapUserTokenService;

@RestController
@RequestMapping(ApiConstants.MAP_USER_TOKEN_COLLECTION)
@CrossOrigin(origins = "*")
@Validated
public class MapUserTokensResource {

	private static final Logger log = LoggerFactory.getLogger(MapUserTokensResource.class);

	@Autowired
	private MapUserTokenService mapUserTokenService;

	@RequestMapping(method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MapUserToken create(@RequestBody @Valid MapUserToken mapUserToken) {
		log.info("Create a map");
		// create
		MapUserToken created = mapUserTokenService.create(mapUserToken);
		return created;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<MapUserToken> get() {
		log.info("List all maps");

		return mapUserTokenService.list();

	}
}
