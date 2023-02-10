package eu.filip.jwtrefreshtoken.domain;

import lombok.Data;

@Data
public class RefreshRequest {
    private String token;
}
