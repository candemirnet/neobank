package com.neobank.backend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    
    public String generateAccessToken(UserDetails userDetails) {
    	
    	
    	return generateToken(new HashMap<String, Object>() , userDetails.getUsername(), accessTokenExpiration);
    	
    	
    }
    
    public String generateRefreshToken(UserDetails userDetails) {
    	
    	return generateToken(new HashMap<>(), userDetails.getUsername(), refreshTokenExpiration);
    	
    }
    
    
    
    private String generateToken(Map<String, Object> claims, String subject, long expiration) {
    	
    	return Jwts.builder()
    			.claims(claims)
    			.issuedAt(new Date())
    			.expiration(new Date(System.currentTimeMillis() + expiration))
    			.subject(subject)
    			.signWith(getSignInKey())
    			.compact();
    	
    	
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
    	
    	final String username = extractUsername(token); 
    	
    	return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    	
    }
    
    
    public String extractUsername(String token) {
    	
    	return extractClaims(token, Claims::getSubject);
    	
    }
    
    private boolean isTokenExpired(String token) {
    	
    	return extractClaims(token, Claims::getExpiration).before(new Date());
    	
    }
    
    
    
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    	
    	Claims claims = extractAllClaims(token);
    	return claimsResolver.apply(claims);
    	
    }
    
    private Claims extractAllClaims(String token) {
    	
    	return Jwts.parser()
    			.verifyWith(getSignInKey())
    			.build()
    			.parseSignedClaims(token)
    			.getPayload();
    	
    	
    }
    
    private SecretKey getSignInKey() {
    	
    	return Keys.hmacShaKeyFor(secret.getBytes());
    	
    }
    
    
    
    
    
}
