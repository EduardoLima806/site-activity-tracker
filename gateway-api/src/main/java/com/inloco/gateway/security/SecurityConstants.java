package com.inloco.gateway.security;

public class SecurityConstants {

    public static final String SECRET = "PitangChallengeSecretKey";
    public static final long EXPIRATION_TIME = 36000000; // 10 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/signup";
}
