package com.bosonit.block7crudvalidation.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtToken {

    @Value("${jwt.private.key}")
    public String PRIVATE_KEY;

    //public static final String PRIVATE_KEY = "Pepe";

    public String generateToken(String usuario, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setSubject(usuario)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
                .compact();

        return "Bearer " + token;
    }
}