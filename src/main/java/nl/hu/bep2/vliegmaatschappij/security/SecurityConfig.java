package nl.hu.bep2.vliegmaatschappij.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET,"/*.html").permitAll()
				.antMatchers(HttpMethod.GET,"/*.css").permitAll()
				.antMatchers(HttpMethod.GET,"/*.js").permitAll()
				.antMatchers(HttpMethod.GET,"/").permitAll()
				.antMatchers(HttpMethod.GET,"/test").permitAll()
				.antMatchers(HttpMethod.GET,"/plane/*").permitAll()
				.antMatchers(HttpMethod.POST,"/plane/*").permitAll()
				.anyRequest().authenticated();
	}
}