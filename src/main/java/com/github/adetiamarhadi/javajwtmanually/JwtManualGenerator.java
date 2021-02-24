package com.github.adetiamarhadi.javajwtmanually;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class JwtManualGenerator {

    // header
    // {"alg":"RS256", "typ":"JWT"}
    // payload
    // {"iss":"CLIENT2021", "sub":"CLIENT2021", "exp":"9999999999", "aud":"https://github.com/adetiamarhadi"}
    // signature
    // rsa-type

    public String jwt() throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {

        System.out.println("Creating JWT (manual) .....");
        Instant now = Instant.now();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode headerNode = objectMapper.createObjectNode();
        headerNode.put("alg", "RS256");
        headerNode.put("typ", "JWT");

        ObjectNode payloadNode = objectMapper.createObjectNode();
        payloadNode.put("iss", "CLIENT2021");
        payloadNode.put("sub", "CLIENT2021");
        payloadNode.put("exp", Date.from(now.plus(10, ChronoUnit.MINUTES)).getTime());
        payloadNode.put("aud", "https://github.com/adetiamarhadi");

        String header = Base64.getUrlEncoder().encodeToString(headerNode.toString().getBytes(StandardCharsets.UTF_8));
        String payload = Base64.getUrlEncoder().encodeToString(payloadNode.toString().getBytes(StandardCharsets.UTF_8));
        String signature = Base64.getUrlEncoder().encodeToString(
                Keys.sign(String.join(".", header, payload), "privatekey.der")
        );

        String jwt = String.join(".", header, payload, signature);

        System.out.println("JWT (manual): " + jwt);

        return jwt;
    }

    public void verifyJwt(String jwt) throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {

        String[] jwts = jwt.split("\\.");
        String header = jwts[0];
        String payload = jwts[1];
        String signature = jwts[2];

        boolean isVerified = Keys.verify(String.join(".", header, payload), signature, "publickey.der");

        System.out.println("verify JWT (manual): " + isVerified);
    }
}
