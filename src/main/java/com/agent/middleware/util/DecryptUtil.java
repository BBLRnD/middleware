package com.agent.middleware.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class DecryptUtil {
    public static String doDecrypt(String encryptedHex, String privateKeyPEM) throws Exception {
        byte[] encryptedBytes = decodeHex(encryptedHex);
        byte[] privateKeyBytes = parsePEMPrivateKey(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
        return decryptedString;
    }

    private static byte[] decodeHex(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even number of characters");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static byte[] parsePEMPrivateKey(String privateKeyPEM) {
        privateKeyPEM = privateKeyPEM
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
        return Base64.getDecoder().decode(privateKeyPEM);
    }
}
