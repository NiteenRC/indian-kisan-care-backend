package com.nc.med.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.UserRepository;

@Component
public class InitializeData {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private DataSource dataSource;

	//@EventListener(ApplicationReadyEvent.class)
	public void loadDataMaster() {
		if (customerRepo.findAll().isEmpty()) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					new ClassPathResource("/scripts/master_schema.sql"));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
	

	//@EventListener(ApplicationReadyEvent.class)
	public void loadDataProduct() {
		if (productRepo.findAll().isEmpty()) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					//new ClassPathResource("/scripts/IKC_BACKUP_MASTER_2021_06_19.sql"));
					new ClassPathResource("/scripts/IKC_BACKUP_TNX_2021_06_20.sql"));
			resourceDatabasePopulator.execute(dataSource);
		}
	}

	//@EventListener(ApplicationReadyEvent.class)
	public void loadDataUser() {
		if (userRepository.findAll().isEmpty()) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					new ClassPathResource("/scripts/user_schema.sql"));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadDataProducts() {
		if (userRepository.findAll().isEmpty()) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					new ClassPathResource("/scripts/products_schema.sql"));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
}
