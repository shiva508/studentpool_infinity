package com.pool.mapper.user;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pool.entity.user.UserProfile;

@Component
public class UserProfileMapper {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserProfileMapper(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void mapper(UserProfile userProfile) {
		
		String unEncriptedPassword=userProfile.getPassword();
		//ENCODING PASSWORD
		String encriptedPassword=bCryptPasswordEncoder.encode(unEncriptedPassword);
		
		userProfile.setPassword(encriptedPassword);
		
		//AVATHAR BUILDER
		StringBuilder avatharBuilder=new StringBuilder();
		avatharBuilder.append(userProfile.getFirstName().toUpperCase().charAt(0));
		avatharBuilder.append(userProfile.getLastName().toUpperCase().charAt(0));
		avatharBuilder.append(new Random().nextInt(100000));
		
		userProfile.setAvatharId(avatharBuilder.toString());
		
	}
	

}
