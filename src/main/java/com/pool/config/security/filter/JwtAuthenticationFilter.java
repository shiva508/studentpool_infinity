package com.pool.config.security.filter;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.pool.config.security.jwt.JwtTokenService;
import com.pool.util.InfinityConstants;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private JwtTokenService jwtTokenService;
    
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(InfinityConstants.OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        }else {
            String authorizationHeader=request.getHeader(InfinityConstants.JWT_TOKEN_HEADER);
            if(null ==authorizationHeader || !authorizationHeader.startsWith(InfinityConstants.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            
            String jwtToken=authorizationHeader.substring(InfinityConstants.TOKEN_PREFIX.length());
            String username=jwtTokenService.extractSubjectFromToken(jwtToken);
            if(jwtTokenService.isTokenValid(username, jwtToken)) {
                if(SecurityContextHolder.getContext().getAuthentication() ==null) {
                    List<GrantedAuthority>  authorities=jwtTokenService.extractAuthoritiesFromToken(jwtToken);
                    Authentication authentication=jwtTokenService.buildAuthenticationObject(username, authorities, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }                
            }else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    public String jwtTokenExtractor(HttpServletRequest request) {
        String authorizationHeader=request.getHeader("authorization");
        if( StringUtils.hasText(authorizationHeader) &&  authorizationHeader.startsWith(InfinityConstants.TOKEN_PREFIX)) {
            return authorizationHeader.substring(InfinityConstants.TOKEN_PREFIX.length());
        }
        return null;
    }

}
