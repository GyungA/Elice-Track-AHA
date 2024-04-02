package com.secondproject.shoppingproject.global.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	private static final String SECRET_KEY = "test1234test";

	public String generate(String email) {
		Date expiredTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.ES256);

		return Jwts.builder()
				.setSubject(email)
				.setExpiration(expiredTime)
				.setIssuedAt(new Date())
				.signWith(secretKey)
				.compact();
	}


}
