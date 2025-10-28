package dev.mvasylenko.passengerservice.security.impl;

import dev.mvasylenko.passengerservice.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

import static dev.mvasylenko.core.constants.CoreConstants.EMAIL;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secretKey;

    public JwtServiceImpl(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = secretKey;
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
    public String extractClaim(String token, String claim) {
        if (claim.equals(EMAIL)) {
            return  extractClaim(token, Claims::getSubject);
        }
        return extractClaim(token, claims -> claims.get(claim, String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody());
    }
}
