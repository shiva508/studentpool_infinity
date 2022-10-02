package com.pool.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pool.entity.user.UserProfile;
import com.pool.service.user.UserProfileService;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

	private UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserProfile userProfile) {

		UserProfile savedUserProfile = userProfileService.save(userProfile);

		return new ResponseEntity<>(savedUserProfile, HttpStatus.OK);
	}
}
