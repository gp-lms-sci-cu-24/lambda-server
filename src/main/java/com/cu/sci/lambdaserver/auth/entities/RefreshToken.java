package com.cu.sci.lambdaserver.auth.entities;


import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * This class holds the properties for security configuration.
 * It includes the properties for JWT access and JWT refresh.
 * It is annotated with @ConfigurationProperties to indicate that it's a source of configuration properties.
 * The prefix "security" is used to map the properties.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    private String uid;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private String ipAddress;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
