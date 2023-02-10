package eu.filip.jwtrefreshtoken.service;

import eu.filip.jwtrefreshtoken.domain.AuthenticationResponse;
import eu.filip.jwtrefreshtoken.domain.LoginCredentials;
import eu.filip.jwtrefreshtoken.entity.User;
import eu.filip.jwtrefreshtoken.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(LoginCredentials loginCredentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword()));
        System.out.println(authentication.isAuthenticated());
        if (authentication.isAuthenticated()) {
            User user = userService.findByUsername(loginCredentials.getUsername());

            //generate refresh token

            AuthenticationResponse response = new AuthenticationResponse(
                    jwtService.generateToken(loginCredentials.getUsername()),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAuthorities(),
                    LocalDateTime.now().plusMinutes(30),
                    //refreshToken.getToken().toString()
                    "REFRESH TOKEN"
            );

            return response;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public AuthenticationResponse refreshToken(){
        return null;
    }

}
