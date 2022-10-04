package com.pool.model.security;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.pool.entity.user.UserProfile;

public class SecurityUserProfile implements UserDetails {

	private static final long serialVersionUID = 5011171470227013939L;
	
	private UserProfile userProfile;
	
	
	public SecurityUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return userProfile.getRoles()
	                     .stream()
	                     .map(role->new SimpleGrantedAuthority(role.getRole()))
	                     .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userProfile.getPassword();
	}

	@Override
	public String getUsername() {
		return userProfile.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

    public String getAvatharId() {
        return userProfile.getAvatharId();
    }

    public String getFirstName() {
        return userProfile.getFirstName();
    }
   
}
