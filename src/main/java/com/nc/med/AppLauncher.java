package com.nc.med;

import com.nc.med.service.AuditorAwareImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RestController
public class AppLauncher extends SpringBootServletInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(AppLauncher.class);
    }


    public static boolean backup(String dbUsername, String dbPassword, String dbName, String outputFile)
            throws IOException, InterruptedException {
        String command = String.format("mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);

        Process process = Runtime.getRuntime().exec(command);
        int processComplete = process.waitFor();
        return processComplete == 0;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void schedule() throws IOException, InterruptedException {
        System.out.println("Backup Started at " + new Date());
        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
        String dbNameList = "smart_001";
        String saveFileName = format.format(backupDate) + "_smart_accounting_book_backup.sql";
        String filePath = System.getProperty("user.home") + "/Documents/";
        String savePath = filePath + File.separator + saveFileName;
        backup("root", "Root@123", "smart_001", savePath);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppLauncher.class);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
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
