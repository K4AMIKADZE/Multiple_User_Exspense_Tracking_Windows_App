package GenerallyUsedCode;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionBlock {

    static SecretKey secretKey;


        // generates key and give it as secret key
     public static SecretKey getKey() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);  // can be 128 256 i guess for safety
        return keyGen.generateKey();
    }

        // encrypts information
      public static String encrypt(String TextToEncrypt, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");  // this can be changes from AES TO RSA DIFFRENT ENCRYPTING I GUESS
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(TextToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES"); // this can be changes from AES TO RSA DIFFRENT DECRYPTING I GUESS
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

     public static SecretKey decodeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

}
