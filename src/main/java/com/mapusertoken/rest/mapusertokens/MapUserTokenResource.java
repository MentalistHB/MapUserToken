package com.mapusertoken.rest.mapusertokens;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mapusertoken.model.MapUserToken;
import com.mapusertoken.rest.ApiConstants;
import com.mapusertoken.service.MapUserTokenService;

@RestController
@RequestMapping(ApiConstants.MAP_USER_TOKEN_ITEM)
public class MapUserTokenResource {

	private static final Logger log = LoggerFactory.getLogger(MapUserTokenResource.class);

	@Autowired
	private MapUserTokenService mapUserTokenService;

	@RequestMapping(method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public MapUserToken get(@PathVariable("username") String username) {
		log.info("Get a map [username={}]", username);

		return mapUserTokenService.getByUsername(username);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@PathVariable("username") String username) {
		log.info("Delete a map [username={}]", username);

		mapUserTokenService.deleteByUsername(username);

	}

}
