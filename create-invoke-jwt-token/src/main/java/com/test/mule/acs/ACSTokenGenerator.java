package com.test.mule.acs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class ACSTokenGenerator {
    public static void main(String[] args) {
    }
    
    public static String getToken() {
    	
        // Generate a secret key
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Set JWT claims
        String jwt = Jwts.builder()
                .setSubject("user123") // Subject (e.g., user identifier)
                .setIssuer("myapp.com") // Issuer
                .setIssuedAt(new Date()) // Issue time
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // Expiration time (1 hour)
                .claim("role", "admin") // Custom claims
                .signWith(key) // Sign the JWT with the key
                .compact();

        System.out.println("Generated JWT: " + jwt);

        // To verify the JWT, you can use the same key
        System.out.println("\nVerification:");
        String subject = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        System.out.println("JWT Subject: " + subject);
        
        return jwt;
    }
}
