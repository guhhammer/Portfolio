package ciphers;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;

/** Public-key encryption with RSA: generates a key pair, encrypts with the public key and decrypts with the private one. */
public class RsaExample {

    public static final String ALGORITHM = "RSA";

    public static byte[] encrypt(String text, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text.getBytes());
    }

    public static String decrypt(byte[] cipherText, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(cipherText));
    }

    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        String original = "RSA is an algorithm named after three MIT professors:\nRonald Rivest, Adi Shamir and Leonard Adleman.";
        byte[] cipherText = encrypt(original, pair.getPublic());
        String plainText = decrypt(cipherText, pair.getPrivate());

        System.out.println("Original: " + original);
        System.out.println("Encrypted (" + cipherText.length + " bytes): " + java.util.Base64.getEncoder().encodeToString(cipherText));
        System.out.println("Decrypted: " + plainText);
        System.out.println("Elapsed: " + (System.currentTimeMillis() - t0) + " ms");
    }
}
