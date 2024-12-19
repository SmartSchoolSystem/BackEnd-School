package com.seven.schoolapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // @Value("${jwt.secret}") // Secret key for the JWT token, ideally this should be stored securely and changed regularly, it's better to use in these way, but for now the other one will serve
    // public static final String SECRET = "mySecretKey";
    
    public static final String SECRET = "mySecretKeyThisShitsNeedsToBe32bitsOrMore"; // Secret key for the JWT token, ideally this should be stored securely and changed regularly

    // Generate a JWT token for the given username.
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Create a JWT token with claims and a subject (username).
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Claims are the key-value pairs of the JWT token.
                .setSubject(username) // Subject is the username of the user.
                .setIssuedAt(new Date(System.currentTimeMillis())) // IssuedAt is the time at which the token was issued.
                .setExpiration(Date.from(generateExpirationDate())) // Expiration is the time at which the token will expire.
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key and the algorithm.
                .compact(); // Compact the token into a string.
    }
            
    // Decode the secret key and return the signing key for the JWT token.
    private Key getSignKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the username from the JWT token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the JWT token.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a specific claim from the JWT token.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the JWT token.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the JWT token by comparing the username and checking if it's expired.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Generate the expiration date for the token, customizable via application properties.
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }
}
