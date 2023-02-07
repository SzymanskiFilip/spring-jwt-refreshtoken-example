package eu.filip.jwtrefreshtoken.domain;

import lombok.Data;

@Data
public class LoginCredentials {
    private String username;
    private String password;
}
