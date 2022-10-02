package com.pool.service.role;

import java.util.List;

import com.pool.entity.role.Role;
import com.pool.model.CommonResponse;

public interface RoleService {

	public Role save(Role role);

	public List<Role> roles();

	public Role update(Role role);

	public CommonResponse delete(Long roleId);
}
