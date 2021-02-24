package com.github.adetiamarhadi.javajwtmanually;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtJjwtGenerator {

    // header
    // {"alg":"RS256", "typ":"JWT"}
    // payload
    // {"iss":"CLIENT2021", "sub":"CLIENT2021", "exp":"9999999999", "aud":"https://github.com/adetiamarhadi"}
    // signature
    // rsa-type

    public String jwt() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        System.out.println("Creating JWT .....");
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");

        Instant now = Instant.now();

        String jwt = Jwts.builder()
                .setHeader(header)
                .setIssuer("CLIENT2021")
                .setSubject("CLIENT2021")
                .setAudience("https://github.com/adetiamarhadi")
                .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                .signWith(Keys.getPrivateKey("privatekey.der"))
                .compact();

        System.out.println("JWT: " + jwt);

        return jwt;
    }

    public void verifyJwt(String jwt) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.getPublicKey("publickey.der"))
                .build()
                .parseClaimsJws(jwt);
        System.out.println("verify success: " + claimsJws);
    }
}
