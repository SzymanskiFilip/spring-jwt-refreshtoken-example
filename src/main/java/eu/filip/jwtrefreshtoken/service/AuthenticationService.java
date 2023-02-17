package eu.filip.jwtrefreshtoken.service;

import eu.filip.jwtrefreshtoken.domain.AuthenticationResponse;
import eu.filip.jwtrefreshtoken.domain.LoginCredentials;
import eu.filip.jwtrefreshtoken.entity.RefreshToken;
import eu.filip.jwtrefreshtoken.entity.User;
import eu.filip.jwtrefreshtoken.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthenticationResponse authenticate(LoginCredentials loginCredentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword()));
        System.out.println(authentication.isAuthenticated());
        if (authentication.isAuthenticated()) {
            User user = userService.findByUsername(loginCredentials.getUsername());

            String refreshToken = jwtService.generateRefreshToken(user);
            refreshTokenService.saveRefreshToken(user, refreshToken);

            AuthenticationResponse response = new AuthenticationResponse(
                    jwtService.generateAccessToken(user),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAuthorities(),
                    LocalDateTime.now().plusMinutes(30),
                    refreshToken
            );

            System.out.println("token: " + response.getToken());

            return response;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public AuthenticationResponse refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.getRefreshTokenByToken(token);

        String newRefreshToken = jwtService.generateRefreshToken(refreshToken.getUser());
        refreshTokenService.saveRefreshToken(refreshToken.getUser(), newRefreshToken);

        if(jwtService.validateRefreshToken(token)){
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(
                    jwtService.generateAccessToken(refreshToken.getUser()),
                    refreshToken.getUser().getUsername(),
                    refreshToken.getUser().getEmail(),
                    refreshToken.getUser().getAuthorities(),
                    LocalDateTime.now().plusMinutes(30),
                    newRefreshToken
            );

            return authenticationResponse;
        } else {
            refreshTokenService.deleteRefreshTokens(refreshToken.getUser());
        }

        return null;
    }

    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenService.getRefreshTokenByToken(refreshToken);
        User user = token.getUser();
        System.out.println(user.getUsername() + " is trying to log out");
        refreshTokenService.deleteRefreshTokens(user);
    }

}
