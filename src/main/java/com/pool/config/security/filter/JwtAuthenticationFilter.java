package com.pool.config.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.pool.config.security.jwt.JwtTokenService;
import com.pool.util.InfinityConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private JwtTokenService jwtTokenService;
    
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)throws ServletException, IOException {
        try {
            String jwtToken=jwtTokenExtractor(request);
            
            if(StringUtils.hasText(jwtToken) && jwtTokenService.tokenValidator(jwtToken) ) {
                String username=jwtTokenService.userDetailsExtractor(jwtToken, "username");
                String roles=jwtTokenService.userDetailsExtractor(jwtToken, "roles");
                List<GrantedAuthority> authorities=jwtTokenService.userRolesExtractor(roles);
                if(StringUtils.hasText(username) && null==SecurityContextHolder.getContext().getAuthentication()) {
                    Authentication authentication = jwtTokenService.buildAuthenticationObject(username,authorities,request);
                    SecurityContextHolder.getContext().setAuthentication(authentication); 
                }
               
            }
            
        } catch (Exception e) {
           log.error("error", e);
        }
        filterChain.doFilter(request, response);
    }

    public String jwtTokenExtractor(HttpServletRequest request) {
        String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if( StringUtils.hasText(authorizationHeader) &&  authorizationHeader.startsWith(HttpHeaders.AUTHORIZATION)) {
            return authorizationHeader.substring(InfinityConstants.TOKEN_PREFIX.length());
        }
        return null;
    }

}
