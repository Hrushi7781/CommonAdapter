package com.dataAdapter.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DataAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAdapterApplication.class, args);
	}

}
