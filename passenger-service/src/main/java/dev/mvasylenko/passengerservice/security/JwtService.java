package dev.mvasylenko.passengerservice.security;


public interface JwtService {
    boolean isTokenValid(String token);
    String extractClaim(String token, String claim);
}
