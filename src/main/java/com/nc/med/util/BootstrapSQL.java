package com.nc.med.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.nc.med.model.Product;

@Component
public class BootstrapSQL {

	@PostConstruct
	private void insertProductData() throws IOException {
		Stream<String> stream =  Files.lines(Paths.get("src/main/resources/bootstrap_data/products.txt"));
		stream.map(x-> x.split("  ")).forEach(x->{
			//new Product(x[0], x[1], x[2], x[3], x[4], x[5]);
		});;
	}
}
