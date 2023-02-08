package eu.filip.jwtrefreshtoken.service;

import eu.filip.jwtrefreshtoken.entity.RefreshToken;
import eu.filip.jwtrefreshtoken.entity.User;
import eu.filip.jwtrefreshtoken.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken(
                user,
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(2)
        );
        RefreshToken token = refreshTokenRepository.save(refreshToken);
        return token;
    }

    public void removeRefreshToken(UUID user_id){
        refreshTokenRepository.deleteRefreshTokenByUserId(user_id);
    }

}
