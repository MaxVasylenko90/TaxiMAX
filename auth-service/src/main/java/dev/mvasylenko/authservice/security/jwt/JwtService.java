package dev.mvasylenko.authservice.security.jwt;

import dev.mvasylenko.authservice.entity.AuthUser;

import java.util.Map;

public interface JwtService {
    /**
     * Generate new access token for user
     *
     * @param user - user for whom the token will be generated
     * @param claims
     * @return - new access token
     */
    String generateAccessToken(AuthUser user, Map<String, Object> claims);

    /**
     * Generate new refresh token for user
     * @param user - user for whom the token will be generated
     * @param claims
     * @return - new refresh token
     */
    String generateRefreshToken(AuthUser user, Map<String, Object> claims);

    /**
     * Delete refresh token
     * @param refreshToken - token to delete
     */
    void deleteRefreshToken(String refreshToken);

    /**
     * Validate refresh token
     * @param refreshToken - refresh token to validate
     * @return - true if valid, otherwise - false
     */
    boolean isRefreshTokenValid(String refreshToken);

    /**
     * Token validation
     * @param token - token to validate
     * @return - true if valid, otherwise - false
     */
    boolean isTokenValid(String token);

    /**
     * Extract email from token claims
     * @param token - current token
     * @return - user email
     */
    String extractEmailFromTokenClaims(String token);
}
