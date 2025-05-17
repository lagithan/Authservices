package org.example.authservice.repository;

import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

    boolean existsByToken(String token);


    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.expiryDate <= ?1 OR t.revoked = true")
    void deleteAllExpiredAndRevoked(Instant now);
}
