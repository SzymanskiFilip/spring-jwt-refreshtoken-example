package eu.filip.jwtrefreshtoken.service;

import eu.filip.jwtrefreshtoken.entity.RefreshToken;
import eu.filip.jwtrefreshtoken.entity.User;
import eu.filip.jwtrefreshtoken.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void deleteRefreshTokenByUser(User user) {

    }

    public RefreshToken saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = new RefreshToken(user, token, LocalDateTime.now().plusHours(24));
        return refreshTokenRepository.save(refreshToken);
    }
}
