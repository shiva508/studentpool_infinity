package com.pool.util;

public class InfinityConstants {

    private InfinityConstants() {
    }

    public static final String UNAUTHORIZED_MSG = "Please login or check your access level.";

    public static final String EMPTY_USER_PROFILE = "No user profiles are available";

    public static final String USER_PROFILE_NOT_FOUND = "User Profile not found with";

    public static final String USER_PROFILE_DELETE_MSG = "User Profile deleted";

    public static final String USER_NOT_FOUND = "User Profile not found";

    public static final String USER_PROFILE_EXIST_MSG = "User profile already exists,please try login";

    public static final String ROLE_DELETE_MSG = "Role deleted";

    public static final String ROLE_EMPTY_MSG = "Roles empty ";

    public static final String ROLE_NOT_FOUND_MSG = "Role not found";

    public static final String TOKEN_PREFIX = "Bearer ";
    
    //public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final long EXPIRATION_TIME = 600000;
    public static final String JWT_TOKEN_HEADER = "jwt-joken";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLC = "Studentpool";
    public static final String GET_ARRAYS_ADMINISTRATION = "Studentpool";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

}
