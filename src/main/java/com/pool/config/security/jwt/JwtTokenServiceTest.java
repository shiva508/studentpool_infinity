/*
package com.pool.config.security.jwt;

package com.pool.config.security.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pool.config.properties.InfinityProperties;
import com.pool.model.ROLES;
import com.pool.model.security.SecurityUserProfile;
import com.pool.util.InfinityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component

@Slf4j
public class JwtTokenService {

    private InfinityProperties infinityProperties;

    public JwtTokenService(InfinityProperties infinityProperties) {
        this.infinityProperties = infinityProperties;
    }

    public String tokenGenerator(Authentication authentication) {
        SecurityUserProfile userProfile = (SecurityUserProfile) authentication.getPrincipal();

        Date endDate = new Date(System.currentTimeMillis() + infinityProperties.getExpirationTime());
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userProfile.getUsername());
        claims.put("firstname", userProfile.getFirstName());
        claims.put("avatharid", userProfile.getAvatharId());
        String roles = userProfile.getAuthorities()
                .stream()
                .map(gran -> gran.getAuthority())
                .collect(Collectors.joining(","));
        claims.put("roles", roles);

        return Jwts.builder()
                .setSubject(userProfile.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(endDate)
                .signWith(SignatureAlgorithm.HS512, infinityProperties.getJwtSecret())
                .compact();
    }

    public boolean tokenValidator(String jwtToken) {
        try {
            Jwts.parser()
                    .setSigningKey(infinityProperties.getJwtSecret())
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException exe) {
            log.error("SignatureException", exe);
        } catch (MalformedJwtException exe) {
            log.error("MalformedJwtException", exe);
        } catch (ExpiredJwtException exe) {
            log.error("ExpiredJwtException", exe);
            throw new UsernameNotFoundException("asnans");

        } catch (UnsupportedJwtException exe) {
            log.error("UnsupportedJwtException", exe);
        } catch (IllegalArgumentException exe) {
            log.error("IllegalArgumentException", exe);
        }
        return false;
    }

    public String userDetailsExtractor(String jwtToken, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(infinityProperties.getJwtSecret())
                .parseClaimsJws(jwtToken)
                .getBody();
        Object obje = (String) claims.get(key) != null ? claims.get(key) : "";
        return (String) obje;
    }

    public List<GrantedAuthority> userRolesExtractor(String rolse) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.hasText(rolse)) {
            authorities = Arrays.asList(rolse.split(",")).stream().map(
                    SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else {
            authorities.add(new SimpleGrantedAuthority(ROLES.USER.getRole()));
        }
        return authorities;
    }

    public Authentication buildAuthenticationObject(String username,
            List<GrantedAuthority> grantedAuthorities,
            HttpServletRequest httpServletRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                null, grantedAuthorities);
        authenticationToken.setDetails(new WebAuthenticationDetails(httpServletRequest));
        return authenticationToken;
    }

}
*/
