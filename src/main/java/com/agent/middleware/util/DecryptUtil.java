package com.agent.middleware.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class DecryptUtil {


    public static String decryptRSA(String text, String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString.replaceAll("\\n", ""));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // Decrypt the message
        byte[] decryptedMessageBytes = cipher.doFinal(Base64.getDecoder().decode(text));
        // Convert the decrypted bytes to a string
        return new String(decryptedMessageBytes);
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
}
