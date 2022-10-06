package com.pool.config.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class InfinityProperties {
	
	@Value("#{'${infinity.cors.methods}'.split(',')}")
	private List<String> corsMethods;
	
	@Value("#{'${infinity.cors.origins}'.split(',')}")
	private List<String> corsOrigins;
	
	@Value("#{'${infinity.cors.headers}'.split(',')}")
	private List<String> corsHeaders;
	
	@Value("#{'${infinity.security.publicurls}'.split(',')}")
	private String[] publicUrls;
	
	@Value("${infinity.security.jwt.secret}")
	private String jwtSecret;
	
	@Value("${infinity.security.jwt.exp_time}")
	private Long expirationTime;
}
