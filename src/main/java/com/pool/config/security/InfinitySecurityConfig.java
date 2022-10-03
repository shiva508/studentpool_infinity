package com.pool.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.pool.config.properties.InfinityProperties;
import com.pool.config.security.entrypoint.CustomAuthenticationEntryPoint;
import com.pool.service.user.SecurityUserProfileService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, 
							prePostEnabled = true, 
							securedEnabled = true)
public class InfinitySecurityConfig {
	
	private final InfinityProperties infinityProperties;
	
	private final CustomAuthenticationEntryPoint authenticationEntryPoint; 
	
	private final SecurityUserProfileService userProfileService; 

	public InfinitySecurityConfig(InfinityProperties infinityProperties,
			CustomAuthenticationEntryPoint authenticationEntryPoint, SecurityUserProfileService userProfileService) {
		this.infinityProperties = infinityProperties;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.userProfileService = userProfileService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf->csrf.disable())
			       .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			       .authorizeRequests(auth->auth.antMatchers(infinityProperties.getPublicUrls())
			    		   						.permitAll()
			    		   						.anyRequest()
			    		   						.authenticated()
			    		   			 )
			       .userDetailsService(userProfileService)
			       .formLogin()
			       .and()
			       //.exceptionHandling(exe->exe.accessDeniedHandler(null)
			      //		   					  .authenticationEntryPoint(authenticationEntryPoint))
			       .httpBasic(Customizer.withDefaults())
			       .headers(header->header.frameOptions().sameOrigin())
			       .build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(infinityProperties.getCorsOrigins());
		configuration.setAllowedMethods(infinityProperties.getCorsMethods());
		configuration.setAllowedHeaders(infinityProperties.getCorsHeaders());
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
}
