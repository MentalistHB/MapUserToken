package com.mapusertoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapusertoken.model.MapUserToken;

public interface MapUserTokenRepository extends JpaRepository<MapUserToken, Long> {

	public int countByUsername(String username);

	public MapUserToken findOneByUsername(String username);
}
