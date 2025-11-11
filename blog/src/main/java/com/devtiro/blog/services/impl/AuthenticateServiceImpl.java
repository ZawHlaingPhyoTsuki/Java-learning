package com.devtiro.blog.services.impl;

import com.devtiro.blog.services.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @Value("${jwt.expiration:86400000}")
  private Long jwtExpiryMs = 86400000L; // 1 day

  @Value("${jwt.secret}")
  private String secretKey;

  @Override
  public UserDetails authenticate(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return userDetailsService.loadUserByUsername(email);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Objects> claims = new HashMap<>();
    return Jwts.builder()
               .setClaims(claims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
               .signWith(getSigningKey(), SignatureAlgorithm.HS256)
               .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = secretKey.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

