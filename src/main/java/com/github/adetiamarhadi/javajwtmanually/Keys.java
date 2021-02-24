package com.github.adetiamarhadi.javajwtmanually;

import io.jsonwebtoken.io.Encoders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Keys {

    public static PrivateKey getPrivateKey(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] bytes = Files.readAllBytes(new File("src/main/resources/"+fileName).toPath());

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(bytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] bytes = Files.readAllBytes(new File("src/main/resources/"+fileName).toPath());

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(bytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(spec);
    }

    public static byte[] sign(String message, String privateKeyFileName) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(getPrivateKey(privateKeyFileName));
        privateSignature.update(message.getBytes(StandardCharsets.UTF_8));

        return privateSignature.sign();
    }

    public static boolean verify(String message, String sign, String publicKeyFileName) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(getPublicKey(publicKeyFileName));
        publicSignature.update(message.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getUrlDecoder().decode(sign);

        return publicSignature.verify(signatureBytes);
    }
}
