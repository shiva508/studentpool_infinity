package com.pool.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pool.entity.user.UserProfile;
import com.pool.model.CommonResponse;
import com.pool.service.user.UserProfileService;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

	private UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> save(@RequestBody UserProfile userProfile) {
		UserProfile savedUserProfile = userProfileService.save(userProfile);
		return new ResponseEntity<>(savedUserProfile, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserProfile userProfile) {
        UserProfile updateUserProfile = userProfileService.update(userProfile);
        return new ResponseEntity<>(updateUserProfile, HttpStatus.OK);
    }
	
	@GetMapping("/all")
    public ResponseEntity<?> users() {
        List<UserProfile> users = userProfileService.findAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
	
	@GetMapping("/byuserid/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable("userId") Long userId) {
        UserProfile userProfile = userProfileService.findByUserId(userId);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }
	
	@GetMapping("/delete/{userId}")
    public ResponseEntity<?> deleteByUserId(@PathVariable("userId") Long userId) {
	    CommonResponse commonResponse = userProfileService.deleteByUserId(userId);
        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }
	
	
}
