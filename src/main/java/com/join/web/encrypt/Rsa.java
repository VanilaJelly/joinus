package com.join.web.encrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException; 
import java.security.KeyFactory; 
import java.security.KeyPair; 
import java.security.Key;
import java.security.KeyPairGenerator; 
import java.security.NoSuchAlgorithmException; 
import java.security.PrivateKey; 
import java.security.PublicKey; 
import java.security.spec.InvalidKeySpecException; 
import java.security.spec.RSAPrivateKeySpec; 
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException; 
import javax.crypto.Cipher; 
import javax.crypto.IllegalBlockSizeException; 
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;


public class Rsa { 
	public static final String RSA = "RSA"; 

	public static KeyPair generateKeyPair() { 
		KeyPair keyPair = null; 
		try { 
			keyPair = KeyPairGenerator.getInstance(RSA).generateKeyPair(); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		} return keyPair; } 

	public static String encrypt(String text, Key publicKey) { 
		byte[] bytes = text.getBytes(); 
		String encryptedText = null; 
		try { 
			Cipher cipher = Cipher.getInstance(RSA); 
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); 
			encryptedText = new String(Base64.encodeBase64(cipher.doFinal(bytes))); } 
		catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
			} catch (NoSuchPaddingException e) {
				e.printStackTrace(); 
			} catch (InvalidKeyException e) {
				e.printStackTrace(); 
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace(); 
			} catch (BadPaddingException e) {
			e.printStackTrace();
			} return encryptedText; } 

	public static String decrypt(String encryptedBase64Text, Key privateKey) { 
		byte[] bytes = Base64.decodeBase64(encryptedBase64Text.getBytes());
		String decryptedText = null;
		try { 
			Cipher cipher = Cipher.getInstance(RSA); 
			cipher.init(Cipher.DECRYPT_MODE, privateKey); 
			decryptedText = new String(cipher.doFinal(bytes)); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		} catch (NoSuchPaddingException e) { 
			e.printStackTrace(); 
		} catch (InvalidKeyException e) {
			e.printStackTrace(); 
		} catch (IllegalBlockSizeException e) { 
			e.printStackTrace(); 
		} catch (BadPaddingException e) {
			decryptedText = "badpadding";
		} return decryptedText; 
		} 
	
	public static RSAPublicKeySpec getRSAPublicKeySpec(PublicKey publicKey) { 
		RSAPublicKeySpec spec = null; 
		try { 
			spec = KeyFactory.getInstance(RSA).getKeySpec(publicKey, RSAPublicKeySpec.class);
		} catch (InvalidKeySpecException e) { 
			e.printStackTrace(); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); } return spec; 
		}
	
	public static RSAPrivateKeySpec getRSAPrivateKeySpec(PrivateKey privateKey) {
		RSAPrivateKeySpec spec = null; 
		try { 
			spec = KeyFactory.getInstance(RSA).getKeySpec(privateKey, RSAPrivateKeySpec.class); 
		} catch (InvalidKeySpecException e) { 
			e.printStackTrace(); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		} return spec; }
	
	public static PublicKey getPublicKey(String modulus, String exponent) { 
		BigInteger modulus_ = new BigInteger(modulus); 
		BigInteger exponent_ = new BigInteger(exponent); 
		PublicKey publicKey = null; 
		try { 
			publicKey = KeyFactory 
					.getInstance(RSA) 
					.generatePublic(new RSAPublicKeySpec(modulus_, exponent_)); 
		} catch (InvalidKeySpecException e) { 
			e.printStackTrace(); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		} return publicKey; 
		} 
	
	public static PrivateKey getPrivateKey(String modulus, String privateExponent) { 
		BigInteger modulus_ = new BigInteger(modulus); 
		BigInteger privateExponent_ = new BigInteger(privateExponent); 
		PrivateKey privateKey = null; 
		try { 
			privateKey = KeyFactory 
					.getInstance(RSA) 
					.generatePrivate(new RSAPrivateKeySpec(modulus_, privateExponent_)); 
		} catch (InvalidKeySpecException e) { 
			e.printStackTrace(); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); 
		} return privateKey; 
		} 
	} 

