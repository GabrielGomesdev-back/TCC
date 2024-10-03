package br.com.api.youspeaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan({
	"br.com.api.youspeaking.data.entity", 
	"br.com.api.youspeaking.data.repository", 
	"br.com.api.youspeaking.feature.login", 
	"br.com.api.youspeaking.thirdparties"
})
@EnableJpaRepositories({
	"br.com.api.youspeaking.data.entity", 
	"br.com.api.youspeaking.data.repository"
})
@SpringBootApplication
public class YouspeakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouspeakingApplication.class, args);
	}

}
