package com.pool.service.user;

import java.util.List;

import com.pool.entity.user.UserProfile;
import com.pool.model.CommonResponse;

public interface UserProfileService {
	public UserProfile save(UserProfile userProfile);

	public UserProfile update(UserProfile userProfile);

	public List<UserProfile> findAll();
	
	public UserProfile findByUserId(Long userId);
	
	public CommonResponse deleteByUserId(Long userId);
	
}
