package com.pool.model;

public enum ROLES {
	
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN"),
	GUEST("ROLE_GUEST");

	private String role;

	private ROLES(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

}
