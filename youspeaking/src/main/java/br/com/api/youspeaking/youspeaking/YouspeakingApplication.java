package br.com.api.youspeaking.youspeaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan({"br.com.api.youspeaking.youspeaking.configurations.security.data.entity"})
@EnableJpaRepositories({"br.com.api.youspeaking.youspeaking.configurations.security.data.repository"})
@SpringBootApplication
public class YouspeakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouspeakingApplication.class, args);
	}

}
