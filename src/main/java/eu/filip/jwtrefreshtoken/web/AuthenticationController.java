package eu.filip.jwtrefreshtoken.web;

import eu.filip.jwtrefreshtoken.domain.AuthenticationResponse;
import eu.filip.jwtrefreshtoken.domain.LoginCredentials;
import eu.filip.jwtrefreshtoken.domain.RefreshRequest;
import eu.filip.jwtrefreshtoken.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginCredentials loginCredentials, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(loginCredentials);

        Cookie httpOnlyCookieRefreshToken = new Cookie("refresh-token", authenticationResponse.getRefreshToken());
        httpOnlyCookieRefreshToken.setHttpOnly(true);
        response.addCookie(httpOnlyCookieRefreshToken);

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refreshToken")
    ResponseEntity<?> refreshAuthentication(@RequestBody RefreshRequest refreshRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest.getToken()));
    }

    @GetMapping("/authenticated")
    public String k() {
        return "hi";
    }

}
