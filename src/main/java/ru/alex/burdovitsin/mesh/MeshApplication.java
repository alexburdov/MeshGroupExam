package ru.alex.burdovitsin.mesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MeshApplication {

    public static void main(String[] args) {
        System.setProperty("liquibase.secureParsing", "false");
        SpringApplication.run(MeshApplication.class, args);
    }
}
