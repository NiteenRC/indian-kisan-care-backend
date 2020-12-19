package com.nc.med.util;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	private static final Logger log = LoggerFactory.getLogger(Utility.class);

	private static SecureRandom random = new SecureRandom();

	public static JsonNode jsonToObject(String productData) {
		JsonNode map = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readTree(productData);
		} catch (Exception e) {
			log.error("ERROR while parsing: ", e);
		}
		return map;
	}

	public static String nextSessionId() {
		return new BigInteger(100, random).toString(32).toUpperCase();
	}
}
