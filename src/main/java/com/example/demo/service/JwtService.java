package com.example.demo.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {

	private final String SECRET_KEY = "mysecret"; // Use a more secure key in production
	private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", userDetails.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).toList());
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	private void onAuthenticationFailure(HttpServletResponse response, AuthenticationException ex) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Authentication failed: " + ex.getMessage());
	}
}
