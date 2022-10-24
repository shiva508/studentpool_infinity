package com.pool.service.role;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pool.entity.role.Role;
import com.pool.mapper.role.RoleMapper;
import com.pool.model.CommonResponse;
import com.pool.model.exception.RoleException;
import com.pool.model.exception.UserProfileException;
import com.pool.repository.role.RoleRepository;
import com.pool.util.InfinityConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	private final RoleMapper roleMapper;
	
	public RoleServiceImpl(RoleRepository roleRepository, 
	                       RoleMapper roleMapper) {
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
	}

	@Override
	@Transactional
	public Role save(Role role) {
	  
		roleMapper.mapper(role);
		log.info("save role input:{}",role);
		Role savedRole = roleRepository.save(role);
		return savedRole;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> roles() {
	    List<Role> roles = roleRepository.findAll();
	    if(!roles.isEmpty()) {
	        return roles;
	    }else {
	        throw new RoleException(InfinityConstants.ROLE_EMPTY_MSG);
	    }
	}

	@Override
	@Transactional
	public Role update(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public CommonResponse delete(Long roleId) {
		Optional<Role> optionalRole=roleRepository.findById(roleId);
		if(optionalRole.isPresent()) {
			roleRepository.deleteById(roleId);
			CommonResponse commonResponse = CommonResponse.builder()
					  .httpStatusCode(HttpStatus.MOVED_PERMANENTLY.value())
					  .httpStatus(HttpStatus.MOVED_PERMANENTLY)
					  .message(InfinityConstants.ROLE_DELETE_MSG)
					  .timeStamp(new Date()).build();
			
			return commonResponse;
		}else {
			throw new UserProfileException(InfinityConstants.ROLE_NOT_FOUND_MSG);
		}
		
	}

}
