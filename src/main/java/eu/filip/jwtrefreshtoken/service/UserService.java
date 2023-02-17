package eu.filip.jwtrefreshtoken.service;

import eu.filip.jwtrefreshtoken.entity.User;
import eu.filip.jwtrefreshtoken.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username){
        return userRepository.findUserByUsername(username).get();
    }

    public User findByRefreshToken(String refreshToken){
        return userRepository.findUserByRefreshToken(UUID.fromString(refreshToken)).get();
    }

    public User findByUserID(String user_id){
        return userRepository.findById(UUID.fromString(user_id)).get();
    }
}
