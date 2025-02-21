package io.github.cristian_eds.InfoMed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InfoMedApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoMedApplication.class, args);
	}

}
