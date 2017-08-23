package lib;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

//import java.util.Base64;

/**
 * PassWord - This Class Contains Methods to encrypt and Decrypt Password
 * @author svkdtt
 *
 */
public class PassWord {
	
	  private static final String UNICODE_FORMAT = "UTF8";
	    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	    private KeySpec ks;
	    private SecretKeyFactory skf;
	    private Cipher cipher;
	    byte[] arrayBytes;
	    private String myEncryptionKey;
	    private String myEncryptionScheme;
	    SecretKey key;
	   /**
	    * Method to encrypt the password
	    * @throws Exception
	    */
	    public PassWord() throws Exception {
	    	myEncryptionKey = "GreatWestPasGreatWestPas";
	        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
	        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
	        ks = new DESedeKeySpec(arrayBytes);
	        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
	        cipher = Cipher.getInstance(myEncryptionScheme);
	        key = skf.generateSecret(ks);
	    }
/**
 * Method to decrypt provided encrypted string
 * @param encryptedString
 * @return String - decryptedText
 */
	    public String decrypt(String encryptedString) {
	        String decryptedText=null;
	        try {
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            byte[] encryptedText = Base64.decodeBase64(encryptedString);
	            byte[] plainText = cipher.doFinal(encryptedText);
	            decryptedText= new String(plainText);
	        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return decryptedText;
	    }

}
