package eu.filip.jwtrefreshtoken.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import eu.filip.jwtrefreshtoken.entity.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class JWTService {
    public static final String SECRET = "482B4D6251655468576D5A7134743777217A25432A462D4A404E635266556A58";
    public static final String issuer = "JWT_APPLICATION";

    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    private JWTVerifier accessTokenVerifier;
    private JWTVerifier refreshTokenVerifier;
    private Algorithm algorithm;

    public JWTService() throws UnsupportedEncodingException {
        accessTokenExpiration = 1000 * 60 * 60 * 24;
        refreshTokenExpiration = 1000 * 60 * 60 * 24;

        algorithm = Algorithm.HMAC512(SECRET);
        accessTokenVerifier = JWT.require(algorithm).withIssuer(issuer).build();
        refreshTokenVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String generateAccessToken(User user){
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + accessTokenExpiration))
                .sign(algorithm);
    }

    public String generateRefreshToken(){
        return "";
    }

    public Optional<DecodedJWT> decodeAccessToken(String token){
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String getIdFromAccessToken(String token) {
        return decodeAccessToken(token).get().getSubject();
    }

    public boolean validateAccessToken(String token){
        return decodeAccessToken(token).isPresent();
    }
}
