package com.gorun.demo.dto;

public class AuthenticationResponce {
	
	private final String jwt;

	public AuthenticationResponce(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	

}
