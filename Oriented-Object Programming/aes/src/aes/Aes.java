/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;



public class Aes {
	private static String ALGORITMO = "AES";
	
	public static byte[] cifra(String texto, String chave) 
			throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException
	{
		Key key = 
				new SecretKeySpec(chave.getBytes(StandardCharsets.UTF_8), ALGORITMO);			
		Cipher cifrador = Cipher.getInstance(ALGORITMO);
		cifrador.init(Cipher.ENCRYPT_MODE, key);
		byte[] textoCifrado = cifrador.doFinal(texto.getBytes());
		return textoCifrado;
	}	
	
	public static String decifra(byte[] texto, String chave) 
			throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException
	{
	  	 Key key = 
	  			 new SecretKeySpec(chave.getBytes(StandardCharsets.UTF_8), ALGORITMO);
		 Cipher decifrador = Cipher.getInstance(ALGORITMO);
		 decifrador.init(Cipher.DECRYPT_MODE, key);
    	 byte[] textoDecifrado = decifrador.doFinal(texto);
    	 return new String(textoDecifrado);
	}	
	
	public static void Imprimir(String texto)
	{
		System.out.println(texto);
	}
	
	public static void Imprimir(byte[] texto)
	{
		System.out.println(new String(texto));
	}
	
	public static void main(String[] args) throws Exception {
            long t0 = System.currentTimeMillis();
            try
		{						
			String chave = "bolabolabolabola"; //tamanho: 16
			String texto = "RSA é um algoritmo que deve o seu nome a três\n" +
"professores do MIT: Ronald Rivest, Adi Shamir e Leonard Adleman";			
			byte[] textoCifrado = Aes.cifra(texto, chave);
			String textoDecifrado = Aes.decifra(textoCifrado, chave);
			Imprimir(textoCifrado);
			Imprimir(textoDecifrado);
                        long t1 = System.currentTimeMillis();
                        System.out.println("Tempo: "+(t1-t0));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
