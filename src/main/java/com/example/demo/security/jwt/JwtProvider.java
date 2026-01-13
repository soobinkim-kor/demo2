package com.example.demo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 토큰을 파싱해서 Claims를 반환.
     * - 만료 토큰이면 ExpiredJwtException 발생
     * - 위/변조 등 문제가 있으면 JwtException 발생
     */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 유효성 검증 (만료/위조/형식 오류 포함)
     */
    public boolean validate(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // (선택) 만료시간 체크가 필요하면
    public boolean isExpired(String token) {
        try {
            Date exp = parseClaims(token).getExpiration();
            return exp != null && exp.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }


    public String createAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

