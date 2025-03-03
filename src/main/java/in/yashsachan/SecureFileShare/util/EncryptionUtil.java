package in.yashsachan.SecureFileShare.util;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Key;

public class EncryptionUtil {
    // 256-bit key (32 bytes)
    private static final String SECRET_KEY = "0123456789ABCDEF0123456789ABCDEF";

    public static void encryptAndSaveFile(byte[] fileData, String filePath) throws Exception {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(fileData);

        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(encryptedData);
        }
    }

    public static byte[] decryptFile(String filePath) throws Exception {
        byte[] fileContent = Files.readAllBytes(new File(filePath).toPath());
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(fileContent);
    }
}
