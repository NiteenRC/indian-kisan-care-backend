package com.nc.med.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sales-order", ignoreUnknownFields = false)
public class ValidationProperties {
	private String stock;

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStock() {
		return stock;
	}

}
