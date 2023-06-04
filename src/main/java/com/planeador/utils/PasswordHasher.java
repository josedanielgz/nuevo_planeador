package com.planeador.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		// Java's standard input retrieves characters as bytes, we need a a Buffer or
		// Scanner like object to retrieve what we need as an String.
		// Source:
		// https://www.tutorialspoint.com/how-can-we-read-from-standard-input-in-java
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Write your password below:");
		String originalPassword = buffer.readLine();
		// This will only work if originalPassword = "password". See Line 46.
		String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
		System.out.println(generatedSecuredPasswordHash);
		// The String we're passing as a validatePassword()'s arg should come from an
		// external source like a DB.
		boolean matched = validatePassword("password", generatedSecuredPasswordHash);
		System.out.println(matched);
		// Another sample text, checks that this is able to realize when the strings are
		// different.
		matched = validatePassword("fossword", generatedSecuredPasswordHash);
		System.out.println(matched);
	}

	public static String generateStorngPasswordHash(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// The more iterations, the stronger the outputted hash will be, but the slower
		// will be the
		// Computing. According with the docs 20000 iterations take like 1000ms on a
		// 2.7Ghz Dual core.
		int iterations = 10000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		// Here's when the printed message is generated. Obviosuly, the iterations and
		// ":" characters are
		// decoration only, you're supossed to get rid of them when you put this string
		// on the database
		// return iterations + ":" + toHex(salt) + ":" + toHex(hash);
		return toHex(salt) + toHex(hash);
	}

	// Generates a random 32-byte lenght value based on the SHA-1 Algorithm.
	// Will be used as the base for a salt.
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		// The SecureRandom class implements standarized RNG algorithms
		// suited for security purposes. With the getInstance(); method is posible
		// create one with the specific SHA-1 based algorithm.
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		// This array is empty, it'll be filled with random bytes from the SecureRandom
		// object,
		byte[] salt = new byte[32];
		sr.nextBytes(salt);
		return salt;
	}

	// Given a long array of bytes, converts them into a String
	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
		// BigIntegers are able to handle numbers bigger than 2^32 and
		// can be created by using the sign-magnitude notation with an
		// array of given bytes.
		// https://www.tutorialspoint.com/sign-magnitude-notation
		BigInteger bi = new BigInteger(1, array);
		// Creates a String in base hexadecimal from the BigInteger.
		String hex = bi.toString(16);
		// Looks for a lenght difference between the raw bytes and
		// It's hex representation, if it does then pad zeroes.
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	// Compares two hashed passwords (one is generated on the fly from a string we
	// just got, other is supossed to be retrieved from eslewhere, like from a DB)
	// and returns if they're the same or not.

	public static boolean validatePassword(String originalPassword, String storedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// The same amount of iterations used to encode the storedPassword should be
		// used with the new one.
		int iterations = 10000;
		// This is from the demo excercise. The ":" separator makes it too obvious
		// where's the salt so better
		// remove it
//        String[] parts = storedPassword.split(":");  
//        byte[] salt = fromHex(parts[0]);
//        byte[] hash = fromHex(parts[1]);
		// Get a substring between the nth and mth character of a String.
		byte[] salt = fromHex(storedPassword.substring(0, 64));
		// Get a substring starting from the n+1th characther of a String (i.e, the 65th
		// for this case).
		byte[] hash = fromHex(storedPassword.substring(64));

		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] testHash = skf.generateSecret(spec).getEncoded();

		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}

	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

}
