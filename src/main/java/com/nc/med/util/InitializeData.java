package com.nc.med.util;

import com.nc.med.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@AllArgsConstructor
public class InitializeData {
    private final UserRepository userRepository;
    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataProducts() {
        if (userRepository.findAll().isEmpty()) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
                    new ClassPathResource("/scripts/roles_schema.sql"));
            resourceDatabasePopulator.execute(dataSource);
        }
    }
}
