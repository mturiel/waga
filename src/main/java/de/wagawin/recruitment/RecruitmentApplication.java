package de.wagawin.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"de.wagawin.recruitment.service.repository"})

public class RecruitmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentApplication.class, args);
	}

}
