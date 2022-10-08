package com.nc.med.config;

import com.nc.med.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class InitializeData {
    private final RoleRepository roleRepository;
    private final DataSource dataSource;
    @Value("${env.profile}")
    private String environment;

    public InitializeData(RoleRepository roleRepository, DataSource dataSource) {
        this.roleRepository = roleRepository;
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataProducts() {
        if (roleRepository.findAll().isEmpty()) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
                    new ClassPathResource("/scripts/" + environment + ".sql"));
            resourceDatabasePopulator.execute(dataSource);
        }
    }
}
