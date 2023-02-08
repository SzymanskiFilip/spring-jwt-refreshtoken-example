package eu.filip.jwtrefreshtoken.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private LocalDateTime expiryDate;
}
