import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class SI_dupla_chave {
    

	  public static final String ALGORITHM = "RSA";

	  public static byte[] encrypt(String text, Key key) {
		    byte[] cipherText = null;
		    try {
			    // get an RSA cipher object and print the provider
			    Cipher cipher = Cipher.getInstance(ALGORITHM);
			    // encrypt the plain text using the key
			    cipher.init(Cipher.ENCRYPT_MODE, key);
			    cipherText = cipher.doFinal(text.getBytes());
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    return cipherText;
	  }
  
	  public static String decrypt(byte[] text, Key key) {
			byte[] dectyptedText = null;
			try {
				// get an RSA cipher object and print the provider
			Cipher cipher = Cipher.getInstance(ALGORITHM);				
			// decrypt the text using the key
				cipher.init(Cipher.DECRYPT_MODE, key);
				dectyptedText = cipher.doFinal(text);
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return new String(dectyptedText);
	  }
	  public static void Imprimir(String texto)
	  {
		System.out.println(texto);
	  }
	
	  public static void Imprimir(byte[] texto)
	  {
		System.out.println(new String(texto));
	  }
	    
          
	  public static void main(String[] args) 
	  {	    
                    long t0 = System.currentTimeMillis();
		    try 
		    {			
		    	KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			    keyGen.initialize(512);
			    KeyPair key = keyGen.generateKeyPair();
		    					
				PublicKey publicKey = key.getPublic();
				PrivateKey privateKey = key.getPrivate();
					
				String originalText = "RSA é um algoritmo que"
                                        + " deve o seu nome a três\n" +
                                          "professores do MIT: Ronald Rivest,"
                                        + " Adi Shamir e Leonard Adleman.";
				
				byte[] cipherText = encrypt(originalText, publicKey);					
				String plainText = decrypt(cipherText, privateKey);
					
				System.out.print("Original: ");
				Imprimir(originalText);
				System.out.print("Cifrado: ");
				Imprimir(cipherText);
				System.out.print("Decifrado: ");
				Imprimir(plainText);
                                long t1 = System.currentTimeMillis();
                                System.out.println("Tempo de execução: "+(t1-t0));
		    } 
		    catch (Exception e) {
		        e.printStackTrace();
		    }
                    
	  }
}