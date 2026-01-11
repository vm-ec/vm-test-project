package com.example.insurancecompany.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "token", unique = true, nullable = false)
        private String token;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "expiresAt", nullable = false)
        private LocalDateTime expiresAt;

        @Column(name = "confirmed_at")
        private LocalDateTime confirmedAt;

        @ManyToOne
        @JoinColumn(nullable = false, name = "app_user_id", referencedColumnName = "id")
        private User user;

        public ConfirmationToken(String token,
                                 LocalDateTime createdAt,
                                 LocalDateTime expiresAt,
                                 User user) {
                this.token = token;
                this.createdAt = createdAt;
                this.expiresAt = expiresAt;
                this.user = user;
        }

}
