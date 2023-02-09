package org.unibl.etf.sni.anonymouschat.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public final class CryptUtils {

    private static final String SymmetricAlgorithmName = "AES";
    private static final int SymmetricKeyLength = 256;
    private static final String AlgorithmFullName = "AES/CBC/PKCS5Padding";
    private static final int IVSize = 16;
    private static final int KeySize = 16;
    private static final String MessageDigestAlgorithm = "SHA-256";

    private static CryptUtils instance = null;

    public static CryptUtils getInstance(){
        if(instance == null)
            instance = new CryptUtils();
        return  instance;
    }

    public String generateSessionKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(SymmetricAlgorithmName);
        generator.init(SymmetricKeyLength);
        SecretKey secret = generator.generateKey();
        return Base64.getEncoder().encodeToString(secret.getEncoded());
    }

    public String encryptSymmetric(String content, String sessionKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextBytes = content.getBytes();
        byte[] iv = new byte[IVSize];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithm);
        messageDigest.update(sessionKey.getBytes(StandardCharsets.UTF_8));

        byte[] keyBytes = new byte[KeySize];
        System.arraycopy(messageDigest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SymmetricAlgorithmName);

        Cipher cipher = Cipher.getInstance(AlgorithmFullName);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedData = cipher.doFinal(plainTextBytes);
        byte[] encryptedIvAndText = new byte[IVSize + encryptedData.length];
        System.arraycopy(iv, 0, encryptedIvAndText, 0, IVSize);
        System.arraycopy(encryptedData, 0, encryptedIvAndText, IVSize, encryptedData.length);

        return new String(Base64.getEncoder().encode(encryptedIvAndText));
    }

    public String decryptSymmetric(byte[] encryptedContent, String sessionKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] dataBytes = Base64.getDecoder().decode(encryptedContent);

        byte[] iv = new byte[IVSize];
        System.arraycopy(dataBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        int encryptedBytesSize = dataBytes.length - IVSize;
        byte[] encryptedBytes = new byte[encryptedBytesSize];
        System.arraycopy(dataBytes, IVSize, encryptedBytes, 0, encryptedBytesSize);

        byte[] keyBytes = new byte[KeySize];
        MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithm);
        messageDigest.update(sessionKey.trim().getBytes(StandardCharsets.UTF_8));
        System.arraycopy(messageDigest.digest(), 0, keyBytes, 0, keyBytes.length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SymmetricAlgorithmName);

        Cipher cipher = Cipher.getInstance(AlgorithmFullName);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted);
    }
}
