package br.com.api.youspeaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaRepositories
@SpringBootApplication
public class YouspeakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouspeakingApplication.class, args);
	}

}
