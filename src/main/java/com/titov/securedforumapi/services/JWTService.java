package com.titov.securedforumapi.services;

import com.titov.securedforumapi.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JWTService {

    private final String jwtSecrete = "myFixedSecretKeyThatIsAtLeast32BytesLongForHmacSHA256Algorithm";

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecrete);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String extractUsername(String token) {
        return getUsername(token);
    }

    public Date getExpiration(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        log.error("Токен не является валидным");
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
}
