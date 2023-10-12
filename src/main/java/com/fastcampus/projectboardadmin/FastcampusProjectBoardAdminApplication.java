package com.fastcampus.projectboardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FastcampusProjectBoardAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastcampusProjectBoardAdminApplication.class, args);
	}

}
