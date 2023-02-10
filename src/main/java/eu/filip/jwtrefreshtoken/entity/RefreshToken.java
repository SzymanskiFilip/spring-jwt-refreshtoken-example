package eu.filip.jwtrefreshtoken.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String token;

    private LocalDateTime expiry_date;

    public RefreshToken() {}

    public RefreshToken(User user, String token, LocalDateTime expiry_date) {
        this.user = user;
        this.token = token;
        this.expiry_date = expiry_date;
    }
}
