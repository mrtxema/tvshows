package com.acme.tvshows.movies.api.v1.model;

public class LoginResponse {
    final String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
