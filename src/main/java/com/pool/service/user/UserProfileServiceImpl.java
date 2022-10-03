package com.pool.service.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pool.entity.user.UserProfile;
import com.pool.mapper.user.UserProfileMapper;
import com.pool.model.CommonResponse;
import com.pool.model.exception.UserProfileException;
import com.pool.repository.user.UserProfileRepository;
import com.pool.util.InfinityConstants;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userProfileRepository;

	private final UserProfileMapper userProfileMapper;

	public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
		this.userProfileRepository = userProfileRepository;
		this.userProfileMapper = userProfileMapper;
	}

	@Override
	@Transactional
	public UserProfile save(UserProfile userProfile) {
		
		//CHECK IF USER ALREADY EXISTS
		Optional<UserProfile> existingUser = userProfileRepository.findByUsername(userProfile.getUsername());
		
		if(!existingUser.isPresent()) {
			userProfileMapper.mapper(userProfile);
			UserProfile savedUserProfile = userProfileRepository.saveAndFlush(userProfile);
			//CHECK IF AVATHAR ALREADY EXISTS
			return savedUserProfile;
		}else {
			throw new UserProfileException(InfinityConstants.USER_PROFILE_EXIST_MSG);
		}
	}

	@Override
	@Transactional
	public UserProfile update(UserProfile userProfile) {
		userProfileMapper.mapper(userProfile);
		UserProfile updatedUserProfile = userProfileRepository.saveAndFlush(userProfile);
		return updatedUserProfile;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserProfile> findAll() {
		List<UserProfile> users = userProfileRepository.findAll();
		if (null == users || users.isEmpty()) {
			throw new UserProfileException(InfinityConstants.EMPTY_USER_PROFILE);
		}

		return users;
	}

	@Override
	@Transactional
	public UserProfile findByUserId(Long userId) {

		Optional<UserProfile> optionalUser = userProfileRepository.findById(userId);

		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserProfileException(InfinityConstants.USER_PROFILE_NOT_FOUND+userId);
		}
	}

	@Override
	@Transactional
	public CommonResponse deleteByUserId(Long userId) {
		Optional<UserProfile> optionalUser = userProfileRepository.findById(userId);

		if (optionalUser.isPresent()) {
			userProfileRepository.delete(optionalUser.get());
			CommonResponse commonResponse = CommonResponse.builder()
														  .httpStatusCode(HttpStatus.MOVED_PERMANENTLY.value())
														  .httpStatus(HttpStatus.MOVED_PERMANENTLY)
														  .message(InfinityConstants.USER_PROFILE_DELETE_MSG)
														  .timeStamp(new Date()).build();
			return commonResponse;
		} else {
			throw new UserProfileException(InfinityConstants.USER_PROFILE_NOT_FOUND+userId);
		}
	}

}
