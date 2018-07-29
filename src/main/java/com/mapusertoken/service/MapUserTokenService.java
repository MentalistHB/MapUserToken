package com.mapusertoken.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapusertoken.exception.ResourceBadRequestException;
import com.mapusertoken.exception.ResourceNotFoundException;
import com.mapusertoken.model.MapUserToken;
import com.mapusertoken.repository.MapUserTokenRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class MapUserTokenService {
	@Autowired
	private MapUserTokenRepository mapUserTokenRepository;

	
	public List<MapUserToken> list(){
	
		return mapUserTokenRepository.findAll();
	}
	/**
	 * Create a role
	 * 
	 * @param mapUserToken
	 * 
	 * @return
	 * @throws ResourceBadRequestException if a map with the username already exist
	 */
	public MapUserToken create(MapUserToken mapUserToken) {

		checkConsistency(mapUserToken);

		mapUserToken.setId(null);

		return mapUserTokenRepository.save(mapUserToken);
	}

	/**
	 * Get a map
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the map does not exist
	 */
	public MapUserToken get(Long id) {
		return findMap(id);
	}

	/**
	 * Get a map
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the map does not exist
	 */
	public MapUserToken getByUsername(String username) {

		MapUserToken map = mapUserTokenRepository.findOneByUsername(username);

		if (map == null) {
			throw new ResourceNotFoundException(
					String.format("The map with the username %s as key cannot be found", username));
		}

		return map;
	}

	/**
	 * Delete a map
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the map is not found
	 */
	public void delete(Long id) {
		// find map
		findMap(id);
		mapUserTokenRepository.delete(id);
	}

	/**
	 * Delete a map
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @throws ResourceNotFoundException if the map is not found
	 */
	public void deleteByUsername(String username) {

		MapUserToken map = mapUserTokenRepository.findOneByUsername(username);

		if (map == null) {
			throw new ResourceNotFoundException(
					String.format("The map with the username %s as key cannot be found", username));
		}

		mapUserTokenRepository.delete(map.getId());
	}

	private MapUserToken findMap(Long id) {
		// find map
		MapUserToken found = mapUserTokenRepository.findOne(id);
		if (found == null) {
			throw new ResourceNotFoundException("The map " + id.toString() + " does not exist");
		}

		return found;
	}

	private void checkConsistency(MapUserToken mapUserToken) {

		if (mapUserTokenRepository.countByUsername(mapUserToken.getUsername()) > 0) {
			throw new ResourceBadRequestException(
					String.format("A map with a username '%s' exist already", mapUserToken.getUsername()));
		}
	}

}
