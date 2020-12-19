package com.nc.med;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nc.med.model.Category;
import com.nc.med.model.Company;
import com.nc.med.model.Customer;
import com.nc.med.model.ERole;
import com.nc.med.model.Location;
import com.nc.med.model.Product;
import com.nc.med.model.Role;
import com.nc.med.model.Supplier;
import com.nc.med.model.User;
import com.nc.med.repo.CategoryRepo;
import com.nc.med.repo.CompanyRepo;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.LocationRepo;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.RoleRepository;
import com.nc.med.repo.SalesOrderRepo;
import com.nc.med.repo.SupplierRepo;
import com.nc.med.repo.UserRepository;
import com.nc.med.service.AuditorAwareImpl;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppLauncher extends SpringBootServletInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(AppLauncher.class);
	private boolean isDefaultDataRequired = true;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	public static void main(String[] args) {
		SpringApplication.run(AppLauncher.class);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	//@Bean
	public void trials() {
		try {
			LOGGER.info("getSalesOrderByCreateDate {}"
					+ salesOrderRepo.getSalesOrderByCreateDate(LocalDateTime.now()).size());
			LOGGER.info("findByDateBetween {}",
					salesOrderRepo.findByCreatedDateBetween(LocalDateTime.now(), LocalDateTime.now()).size());
			LOGGER.info("findAllByLocationLocationID {}", customerRepo.findAllByLocationLocationID(3).size());
			LOGGER.info("findByLocationLocationID {}", customerRepo.findByLocationLocationID(3).size());
		} catch (Exception e) {
			LOGGER.error("Failed {}", e);
		}
	}

	@Bean
	public CommandLineRunner setup(UserRepository userRepo, CompanyRepo companyRepo, LocationRepo locationRepo,
			SupplierRepo supplierRepo, CustomerRepo customerRepo, CategoryRepo categoryRepo, ProductRepo productRepo,
			RoleRepository roleRepository) {
		return (args) -> {
			if (isDefaultDataRequired) {
				Role role1 = new Role(ERole.ROLE_ADMIN);
				Role role2 = new Role(ERole.ROLE_USER);

				if (!roleRepository.findByName(ERole.ROLE_ADMIN).isPresent()) {
					roleRepository.saveAll(Arrays.asList(role1, role2));
				}
				Set<Role> roles1 = new HashSet<>();
				roles1.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());

				Set<Role> roles2 = new HashSet<>();
				roles2.add(roleRepository.findByName(ERole.ROLE_USER).get());

				User user1 = new User("admin", "admin@gmail.com",
						"$2a$10$XSleb92FS0jidzZsosNZNeJvgPZXYDKQAMFW2Fdcyn/.zrQb.D.2S");
				User user2 = new User("user", "user@gmail.com",
						"$2a$10$TaXk2cLcaKM3hGDhS1tPZu3qJGDXFKSy4Zb/WY7eEE8Dfh1TB0yM2");

				if (!userRepo.findByUsername("admin").isPresent()) {
					user1.setRoles(roles1);
					user2.setRoles(roles2);
					userRepo.saveAll(Arrays.asList(user1, user2));
				}

				Company company1 = new Company(1L, "Jai Kisan", "+98723452345");
				Company company2 = new Company(2L, "Mangala", "+98723452345");
				Company company3 = new Company(3L, "Daneshwari", "+98723452345");
				Company company4 = new Company(4L, "Shakti", "+98723452345");
				Company company5 = new Company(5L, "Mangam", "+98723452345");
				if (companyRepo.findByCompanyName("Reliance") == null) {
					companyRepo.saveAll(Arrays.asList(company1, company2, company3, company4, company5));
				}

				Location location1 = new Location(1, "Shinal");
				Location location2 = new Location(2, "Tangadi");
				Location location3 = new Location(3, "Katral");
				Location location4 = new Location(4, "Hulagabali");
				Location location5 = new Location(5, "Other");
				Location location6 = new Location(5, "Athani");
				Location location7 = new Location(5, "Ainapur");
				if (locationRepo.findByCityName("Shinal") == null) {
					locationRepo.saveAll(
							Arrays.asList(location1, location2, location3, location4, location5, location6, location7));
				}

				Supplier supplier1 = new Supplier(1, "Badakambe", companyRepo.findByCompanyName("Jai Kisan"),
						locationRepo.findByCityName("Athani"), "+9038902342");
				Supplier supplier2 = new Supplier(2, "JN Kumbar", companyRepo.findByCompanyName("Mangala"),
						locationRepo.findByCityName("Athani"), "+9038902344");
				Supplier supplier3 = new Supplier(3, "Ghatge", companyRepo.findByCompanyName("Mangam"),
						locationRepo.findByCityName("Ainapur"), "+9038902342");
				if (supplierRepo.findBySupplierName("Badakambe") == null) {
					supplierRepo.saveAll(Arrays.asList(supplier1, supplier2, supplier3));
				}

				Customer customer1 = new Customer(1, "Kallappa", locationRepo.findByCityName("Shinal"), "+9878923745");
				Customer customer2 = new Customer(2, "Mallappa", locationRepo.findByCityName("Katral"), "+9878923745");
				Customer customer3 = new Customer(3, "Birappa", locationRepo.findByCityName("Tangadi"), "+9878923745");
				Customer customer4 = new Customer(4, "Bairappa", locationRepo.findByCityName("Shinal"), "+9878923745");
				Customer customer5 = new Customer(5, "Murgyappa", locationRepo.findByCityName("Other"), "+9878923745");
				if (customerRepo.findByCustomerName("Kallappa") == null) {
					customerRepo.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));
				}

				Category category1 = new Category("fertilizer desc", "Fertilizer", new Date());
				Category category2 = new Category("seed desc", "Seed", new Date());
				Category category3 = new Category("pestiside desc", "Pesticide", new Date());
				if (categoryRepo.findByCategoryName("Fertilizer") == null) {
					categoryRepo.saveAll(Arrays.asList(category1, category2, category3));
				}

				Product product1 = new Product(new Date(), 267, "urea desc", "Urea", 100,
						categoryRepo.findByCategoryName("Fertilizer"));
				Product product2 = new Product(new Date(), 1360, "tanger desc", "Tanger", 150,
						categoryRepo.findByCategoryName("Pesticide"));
				Product product3 = new Product(new Date(), 1700, "Mico desc", "Mico", 250,
						categoryRepo.findByCategoryName("Seed"));
				Product product4 = new Product(new Date(), 260, "24D desc", "24D", 500,
						categoryRepo.findByCategoryName("Pesticide"));
				Product product5 = new Product(new Date(), 170, "ultrazen desc", "Ultrazen", 250,
						categoryRepo.findByCategoryName("Pesticide"));
				Product product6 = new Product(new Date(), 1200, "dap desc", "DAP", 50,
						categoryRepo.findByCategoryName("Fertilizer"));
				if (productRepo.findByProductName("24D") == null) {
					productRepo.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6));
				}
			}
		};
	}

	@Bean
	public CommandLineRunner setupProduct(ProductRepo productRepo, CategoryRepo categoryRepo) {
		return (args) -> {
			mapCategoryFileData(categoryRepo, "C:\\Users\\administator\\Downloads\\h2database\\category.txt");
			mapProductFileData(productRepo, categoryRepo,
					"C:\\Users\\administator\\Downloads\\h2database\\product.txt");
		};
	}

	private static void mapCategoryFileData(CategoryRepo categoryRepo, String fileName) {
		if (Files.exists(Paths.get(fileName))) {
			try {
				List<Category> products = Files.lines(Paths.get(fileName)).skip(1).map(line -> {
					String[] result = line.split(",");
					try {
						if (categoryRepo.findByCategoryName(result[2].replaceAll("\"", "")) == null) {
							return new Category(result[1].replaceAll("\"", ""), result[2].replaceAll("\"", ""),
									new SimpleDateFormat("yyyy-MM-dd")
											.parse(result[3].replaceAll("00:00.0", "").replaceAll("\"", "")));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).collect(Collectors.toList());
				categoryRepo.saveAll(products);
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	private static void mapProductFileData(ProductRepo productRepo, CategoryRepo categoryRepo, String fileName) {
		if (Files.exists(Paths.get(fileName))) {
			try {
				List<Product> products = Files.lines(Paths.get(fileName)).skip(1).map(line -> {
					String[] result = line.split(",");
					try {
						if (productRepo.findByProductName(result[4].replaceAll("\"", "")) == null) {
							return new Product(
									new SimpleDateFormat("yyyy-MM-dd")
											.parse(result[1].replaceAll("00:00.0", "").replaceAll("\"", "")),
									Double.valueOf(result[2].replaceAll("\"", "")), result[3].replaceAll("\"", ""),
									result[4].replaceAll("\"", ""), Integer.valueOf(result[5].replaceAll("\"", "")),
									categoryRepo.findById(Integer.valueOf(result[6].replaceAll("\"", ""))).get());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
				productRepo.saveAll(products);
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppLauncher.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "GET", "DELETE", "OPTIONS",
						"PATCH", "POST");
			}
		};
	}
}