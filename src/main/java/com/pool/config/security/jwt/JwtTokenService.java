package com.pool.config.security.jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pool.config.properties.InfinityProperties;
import com.pool.model.auth.TokenResponse;
import com.pool.model.security.SecurityUserProfile;
import com.pool.util.InfinityConstants;

@Component
public class JwtTokenService {
    
    private InfinityProperties infinityProperties;

    public JwtTokenService(InfinityProperties infinityProperties) {
        this.infinityProperties = infinityProperties;
    }
    
    public String generateJwtToken(SecurityUserProfile userPrincipal) {
        String claims[]=extractClaimsFromUser(userPrincipal);
        String jwtToken=JWT.create()
                           .withIssuer(InfinityConstants.GET_ARRAYS_LLC)
                           .withAudience(InfinityConstants.GET_ARRAYS_ADMINISTRATION)
                           .withIssuedAt(new Date(System.currentTimeMillis()))
                           .withSubject(userPrincipal.getUsername())
                           .withArrayClaim(InfinityConstants.AUTHORITIES, claims)
                           .withExpiresAt(new Date(System.currentTimeMillis()+infinityProperties.getExpirationTime()))
                           .sign(generateAlgorithm(infinityProperties.getJwtSecret()));
                           
        return jwtToken;
    }

    public String[] extractClaimsFromUser(SecurityUserProfile userPrincipal) {
        List<String> cliamsList=userPrincipal.getAuthorities()
                                             .stream()
                                             .map(auth->auth.getAuthority())
                                             .collect(Collectors.toList());
        return cliamsList.stream()
                         .toArray(String[]::new);
    }
    
    public Algorithm generateAlgorithm(String secret) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        return algorithm;
    }
    
    public List<GrantedAuthority> extractAuthoritiesFromToken(String jwtToken){
        String[] claims=extractClaimsFromJwtToken(jwtToken);
        List<String> claimList=Arrays.asList(claims);
        return claimList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }

    public String[] extractClaimsFromJwtToken(String jwtToken) {
        JWTVerifier jwtVerifier=getJwtVerifier();
        return jwtVerifier.verify(jwtToken)
                          .getClaim(InfinityConstants.AUTHORITIES)
                          .asArray(String.class);
    }

    public JWTVerifier getJwtVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = generateAlgorithm(infinityProperties.getJwtSecret());
            jwtVerifier=JWT.require(algorithm)
                           .withIssuer(InfinityConstants.GET_ARRAYS_LLC)
                           .build();
        } catch (JWTVerificationException jwtVerificationException) {
            throw new JWTVerificationException(InfinityConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
        return jwtVerifier;
    }
    
    public Authentication buildAuthenticationObject(String username,List<GrantedAuthority> grantedAuthorities,HttpServletRequest httpServletRequest) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        return authenticationToken;
        
    }
    
    public boolean isTokenValid(String username,String token) {
        JWTVerifier jwtVerifier=getJwtVerifier();
        return StringUtils.isNotEmpty(username) && isJwtTokenExpaired(jwtVerifier,token);
    }

    public boolean isJwtTokenExpaired(JWTVerifier jwtVerifier,String token) {
        Date expairationDate=jwtVerifier.verify(token).getExpiresAt();
        return expairationDate.after(new Date());
    }
    
    public Long expirationTimeInMilliSeconds(String token) {
        JWTVerifier jwtVerifier=getJwtVerifier();
        Date expairationDate=jwtVerifier.verify(token).getExpiresAt();
        return expairationDate.toInstant().toEpochMilli();
    }
    
    public String extractSubjectFromToken(String jwtToken) {
        JWTVerifier jwtVerifier=getJwtVerifier();
        return jwtVerifier.verify(jwtToken).getSubject();
    }

    public HttpHeaders generateJwtHeaders(SecurityUserProfile userPrincipal) {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add(InfinityConstants.JWT_TOKEN_HEADER, generateJwtToken(userPrincipal));
        return httpHeaders;
    }
    
    public String generateJwtTokenValue(SecurityUserProfile userPrincipal) {
        return generateJwtToken(userPrincipal);
    }

    
    public TokenResponse jwtTokenGenerator(Authentication authentication) {
        
        SecurityUserProfile userPrincipal = (SecurityUserProfile) authentication.getPrincipal();
        String claims[]=extractClaimsFromUser(userPrincipal);
        String jwtToken=JWT.create()
                           .withIssuer(InfinityConstants.GET_ARRAYS_LLC)
                           .withAudience(InfinityConstants.GET_ARRAYS_ADMINISTRATION)
                           .withIssuedAt(new Date(System.currentTimeMillis()))
                           .withSubject(userPrincipal.getUsername())
                           .withArrayClaim(InfinityConstants.AUTHORITIES, claims)
                           .withExpiresAt(new Date(System.currentTimeMillis()+infinityProperties.getExpirationTime()))
                           .sign(generateAlgorithm(infinityProperties.getJwtSecret()));
                           
        return TokenResponse.builder()
                            .fullName(userPrincipal.getFirstName())
                            .username(userPrincipal.getUsername())
                            .avatharId(userPrincipal.getAvatharId())
                            .token(InfinityConstants.TOKEN_PREFIX+jwtToken)
                            .roles(userPrincipal.getRoles())
                            .rolesmap(userPrincipal.getRolesMap())
                            .build();
    }
    
}
