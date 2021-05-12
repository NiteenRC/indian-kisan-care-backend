package com.nc.med.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.nc.med.repo.CustomerRepo;

import javax.sql.DataSource;

@Component
public class InitializeData {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private DataSource dataSource;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		if (customerRepo.findAll().isEmpty()) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					new ClassPathResource("/scripts/schema.sql"));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
}
