package com.pool.controller.role;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pool.entity.role.Role;
import com.pool.model.CommonResponse;
import com.pool.service.role.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/create")
	public ResponseEntity<?> createNewRole(@RequestBody Role role) {
		Role createdRole = roleService.save(role);
		return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getRoles() {
		List<Role> roles = roleService.roles();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRole(@RequestBody Role role) {
		Role updatedRole = roleService.update(role);
		return new ResponseEntity<>(updatedRole, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{roleId}")
	public ResponseEntity<?> deleteRole(@PathVariable("roleId") Long roleId) {
		CommonResponse commonResponse=roleService.delete(roleId);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

}
