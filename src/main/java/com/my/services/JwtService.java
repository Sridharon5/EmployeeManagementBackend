package com.my.services;

import java.util.Date;
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
  public boolean isValid(String token,UserDetails user) {
	  System.out.println("Error in me JwtService");
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
            .setIssuedAt(new Date(System.currentTimeMillis())) 
            .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) 
            .signWith(getSignKey(), io.jsonwebtoken.SignatureAlgorithm.HS256) 
            .compact();
}
 
}
