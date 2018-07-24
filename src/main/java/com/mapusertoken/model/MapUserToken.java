package com.mapusertoken.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class MapUserToken implements Serializable {

	private static final long serialVersionUID = -8019398584857375323L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String username;

	@NotEmpty(message = "The token must not be empty")
	@NotNull(message = "The token must not be null")
	@Column(columnDefinition = "varchar")
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MapUserToken username(String username) {
		this.username = username;
		return this;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MapUserToken token(String token) {
		this.token = token;
		return this;
	}

}
