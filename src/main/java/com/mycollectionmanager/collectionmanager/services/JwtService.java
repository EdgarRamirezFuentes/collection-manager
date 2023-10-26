package com.mycollectionmanager.collectionmanager.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    public String getToken(UserDetails user);
    public String getToken(Map<String,Object> claims, UserDetails user);
    public Key getKey();
    public boolean isValidToken(String token, UserDetails userDetails);
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver);
    public Claims getAllClaims(String token);
    public String getUsernameFromToken(String token);
    public Date getExpiration(String token);
    public boolean isExpiredToken(String token);
}
