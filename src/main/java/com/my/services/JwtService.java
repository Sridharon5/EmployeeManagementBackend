package com.my.services;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.my.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  private final String SECRET_KEY="05f70778409b6fa7f3dc013e4380b2b766ffc1b50f0b7cad19266c066e29dd43";

  /** Access token lifetime (ms) — 2 hours; use {@code /auth/refresh} while active to extend. */
  private static final long ACCESS_TOKEN_TTL_MS = 2L * 60 * 60 * 1000;
  
  private SecretKey getSignKey() {
		byte[] keyBytes=Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
  }
  public <T> T extractClaim(String token,Function<Claims,T>resolver) {
	  Claims claims=extractAllClaims(token);
	  return resolver.apply(claims);
  }
  public String extractUsername(String token) {
	  return extractClaim(token,Claims::getSubject);
  }

  public Long extractUserId(String token) {
	  return extractClaim(token, claims -> {
		  Object v = claims.get("userId");
		  if (v == null) return null;
		  if (v instanceof Long) return (Long) v;
		  if (v instanceof Integer) return ((Integer) v).longValue();
		  if (v instanceof Number) return ((Number) v).longValue();
		  return Long.parseLong(v.toString());
	  });
  }

  public String extractRole(String token) {
	  return extractClaim(token, claims -> {
		  Object r = claims.get("role");
		  return r != null ? r.toString() : null;
	  });
  }
  public boolean isValid(String token,UserDetails user) {
	  String username=extractUsername(token);
	  return (username.equals(user.getUsername()))&& !isTokenExpired(token);
  }
  
  private boolean isTokenExpired(String token) {
	
	return extractExpiration(token).before(new Date());
}
private Date extractExpiration(String token) {
	return extractClaim(token,Claims::getExpiration);
}
private Claims extractAllClaims(String token) {
    return Jwts
            .parserBuilder()  
            .setSigningKey(getSignKey()) 
            .build() 
            .parseClaimsJws(token)
            .getBody(); 
   }

public String generateToken(User user) {
    return Jwts
            .builder()
            .setSubject(user.getUsername())
            .addClaims(Map.of(
            		"userId", user.getId(),
            		"role", user.getRole().name()))
            .setIssuedAt(new Date(System.currentTimeMillis())) 
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TTL_MS)) 
            .signWith(getSignKey(), io.jsonwebtoken.SignatureAlgorithm.HS256) 
            .compact();
}
 
}
