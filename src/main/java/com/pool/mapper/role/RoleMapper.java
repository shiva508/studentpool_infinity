package com.pool.mapper.role;

import org.springframework.stereotype.Component;
import com.pool.entity.role.Role;
import com.pool.model.ROLES;

@Component
public class RoleMapper {

	public void mapper(Role role) {
		String displayName = role.getName();
		role.setRole(this.getRole(displayName));
	}

	public String getRole(String displayName) {
		return switch (displayName) {
		case "Admin" ->ROLES.ADMIN.getRole();
		case "User" ->ROLES.USER.getRole();
		case "Guest" ->ROLES.GUEST.getRole();
		default ->ROLES.USER.getRole();
		};
	}

}
