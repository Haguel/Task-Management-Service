package com.task_management_service.backend.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtServiceHelper {
    @Value("${token.signing.token}")
    private String jwtSigningToken;
    
    public boolean isTokenExpired(String jwtToken) {
        boolean test = extractExpiration(jwtToken).before(new Date());

        return test;
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(jwtToken);

        return claimsResolvers.apply(claims);
    }

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningToken);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
