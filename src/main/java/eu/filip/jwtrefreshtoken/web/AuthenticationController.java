package eu.filip.jwtrefreshtoken.web;

import eu.filip.jwtrefreshtoken.domain.LoginCredentials;
import eu.filip.jwtrefreshtoken.service.AuthenticationService;
import eu.filip.jwtrefreshtoken.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/login")
    ResponseEntity<String> authenticate(@RequestBody LoginCredentials loginCredentials){
        return ResponseEntity.ok(authenticationService.authenticate(loginCredentials));
    }

    @GetMapping("/authenticated")
    public String k() {
        return "hi";
    }
}
