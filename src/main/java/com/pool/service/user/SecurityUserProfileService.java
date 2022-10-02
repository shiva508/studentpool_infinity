package com.pool.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.model.security.SecurityUserProfile;
import com.pool.repository.user.UserProfileRepository;
import com.pool.util.InfinityConstants;

@Service
public class SecurityUserProfileService implements UserDetailsService {
	
	private final UserProfileRepository userProfileRepository;
	
	public SecurityUserProfileService(UserProfileRepository userProfileRepository) {
		this.userProfileRepository = userProfileRepository;
	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username){
		
		return userProfileRepository.findByUsername(username)
									.map(SecurityUserProfile::new)
									.orElseThrow(()->new UsernameNotFoundException(InfinityConstants.USER_NOT_FOUND));
	}

}
