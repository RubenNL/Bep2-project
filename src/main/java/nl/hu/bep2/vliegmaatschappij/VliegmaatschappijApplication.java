package nl.hu.bep2.vliegmaatschappij;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled=true, securedEnabled = true)
public class VliegmaatschappijApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VliegmaatschappijApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VliegmaatschappijApplication.class);
	}
}