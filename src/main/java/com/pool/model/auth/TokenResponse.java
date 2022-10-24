package com.pool.model.auth;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String username;
    private String fullName;
    private String avatharId;
    private String token;
    private List<String> roles;
    private Map<Long,String> rolesmap;
}
