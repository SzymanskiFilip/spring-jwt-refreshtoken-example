package eu.filip.jwtrefreshtoken.repository;

import eu.filip.jwtrefreshtoken.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);

    @Query(nativeQuery = true, value = "select users.id, users.username, users.\"password\", users.email, users.roles from users " +
            "inner join refresh_tokens on users.id = refresh_tokens.user_id where refresh_tokens.token = ?1")
    Optional<User> findUserByRefreshToken(UUID token);
}
