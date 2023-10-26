package com.mycollectionmanager.collectionmanager.services.impl;

import com.mycollectionmanager.collectionmanager.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    @Override
    public String getToken(UserDetails user) {
        HashMap<String, Object> claims = new HashMap<>();
        return getToken(claims, user);
    }

    public String getToken(Map<String,Object> claims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getKey() {
        byte[] B64Key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(B64Key);
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isExpiredToken(token);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isExpiredToken(String token) {
        return getExpiration(token).before(new Date());
    }
}
