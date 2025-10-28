package dev.mvasylenko.driverservice.security;


public interface JwtService {
    boolean isTokenValid(String token);
    String extractClaim(String token, String claim);
}
