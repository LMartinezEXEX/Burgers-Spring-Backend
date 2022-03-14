package com.burgers.app.Security;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;
    private String username;
    private String email;

    @Setter(value = AccessLevel.NONE)
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
