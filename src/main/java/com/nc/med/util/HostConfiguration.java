package com.nc.med.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class HostConfiguration {

	private static String mpCryptoPassword = "Niteen";

	@PostConstruct
	public void hosting() {
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (!"Niteens-Air".equals(localHost.getHostName()) && !"192.168.43.211".equals(localHost.getHostAddress())) {
			//System.exit(0);
		}

		// System.out.println(decrypt("LnTMjpPtkgVuL8cTnQzMT51JawLw+ARQ"));
	}

	private static String encrypt(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(mpCryptoPassword);
		String encryptedText = textEncryptor.encrypt(text);
		return encryptedText;
	}

	private static String decrypt(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(mpCryptoPassword);
		String decryptedText = textEncryptor.decrypt(text);
		return decryptedText;
	}

}
