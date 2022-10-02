package com.pool.model;

public enum DISPLAYROLES {
	
	USER("User"),
	ADMIN("Admin"),
	GUEST("Guest");

	private String role;

	private DISPLAYROLES(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

}
