package org.chaoticbits.collabcloud.visualizer.avatars;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * A class for pulling avatar images from Gravatar
 * @author andy
 * 
 */
public class Gravatar {

	private static Gravatar instance = null;

	public static Gravatar instance() throws NoSuchAlgorithmException {
		if (instance == null)
			instance = new Gravatar();
		return instance;
	}

	private final Properties props = new Properties();
	private MessageDigest digest;

	private Gravatar() throws NoSuchAlgorithmException {
		try {
			props.load(getClass().getResourceAsStream("gravatar.properties"));
		} catch (IOException e) {
			System.err.println("No gravatar.properties found in this package");
			e.printStackTrace();
		}
		digest = MessageDigest.getInstance(prop("digestalgorithm", "MD5"));
	}

	/**
	 * Returns the digest of the given input in a string with lowercase hex. See gravatar.properties for
	 * changing properties. Default charset is CP1252 and default digest is MD5.
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String digest(String input) {
		byte[] bytes = new byte[0];
		try {
			bytes = digest.digest(input.getBytes(prop("charset", "CP1252")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes)
			sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
		return sb.toString();
	}

	public URL makeURL(String email) throws MalformedURLException {
		String url = prop("url", "");
		url += digest(email.trim().toLowerCase());
		url += "?s=" + prop("size", "80") + "&r=" + prop("rating", "g") + "&d=" + prop("defaultImage", "mm");
		return new URL(url);
	}

	
	
	private String prop(String name, String defaultValue) {
		return props.getProperty("org.chaoticbits.collabcloud.visualizer.avatars.gravatar." + name, defaultValue);
	}

	public BufferedImage download(URL url) throws IOException {
		return ImageIO.read(url.openStream());
	}
}
