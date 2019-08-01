package com.api.microservices;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EntityScan(basePackages = "com.api.microservices")
@SpringBootApplication
    public class AutorizationServerApplication {

        public static void main(String[] args) {
            SpringApplication.run(AutorizationServerApplication.class, args);
        }


    @Bean
    public Module hibernate5Module() {
        return new Hibernate5Module();
    }
    }

