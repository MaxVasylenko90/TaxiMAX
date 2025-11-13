package dev.mvasylenko.authservice.security.jwt.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final Logger LOG = LoggerFactory.getLogger(JwtServiceImpl.class);
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 5;
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 5;

    private final RedisTemplate<String, String> redisTemplate;
    private final SecretKey secretKey;

    public JwtServiceImpl(RedisTemplate<String, String> redisTemplate,
                          @Value("${jwt.secret.key}") String secretKey) {
        this.redisTemplate = redisTemplate;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(AuthUser user, Map<String, Object> claims) {
        return generateToken(user, ACCESS_TOKEN_EXPIRATION, claims);
    }

    @Override
    public String generateRefreshToken(AuthUser user, Map<String, Object> claims) {
        final var refreshToken = generateToken(user, REFRESH_TOKEN_EXPIRATION, claims);
        redisTemplate.opsForValue().set(refreshToken, user.getEmail(), getRefreshTokenDuration(refreshToken));
        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        try {
            redisTemplate.delete(refreshToken);
            LOG.info("Refresh token {} was deleted.", refreshToken);
        } catch (JwtException e) {
            LOG.error("JWT refresh token {} is not in the database and probably corrupted!", refreshToken);
        }
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken) {
        return redisTemplate.hasKey(refreshToken) ? isTokenValid(refreshToken) : Boolean.FALSE;
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            return new Date().before(extractClaim(token, Claims::getExpiration));
        } catch (JwtException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public String extractEmailFromTokenClaims(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String generateToken(AuthUser user, Long expiration, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Duration getRefreshTokenDuration(String token) {
        return extractClaim(token, claims ->
                Duration.between(claims.getIssuedAt().toInstant(), claims.getExpiration().toInstant()));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody());
    }
}
