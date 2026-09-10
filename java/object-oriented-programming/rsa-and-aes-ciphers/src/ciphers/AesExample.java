package ciphers;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/** Symmetric encryption with AES: the same 16-byte key encrypts and decrypts. */
public class AesExample {

    private static final String ALGORITHM = "AES";

    public static byte[] encrypt(String text, String key) throws Exception {
        Key secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(text.getBytes());
    }

    public static String decrypt(byte[] cipherText, String key) throws Exception {
        Key secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText));
    }

    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();
        String key = "bolabolabolabola";   // 16 bytes = AES-128
        String text = "RSA is an algorithm named after three MIT professors:\nRonald Rivest, Adi Shamir and Leonard Adleman";

        byte[] cipherText = encrypt(text, key);
        String decrypted = decrypt(cipherText, key);

        System.out.println("Encrypted: " + java.util.Base64.getEncoder().encodeToString(cipherText));
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Elapsed: " + (System.currentTimeMillis() - t0) + " ms");
    }
}
