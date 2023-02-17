package eu.filip.jwtrefreshtoken.repository;

import eu.filip.jwtrefreshtoken.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from refresh_tokens where user_id = ?1")
    void deleteAllByUser_id(UUID user_id);
}
