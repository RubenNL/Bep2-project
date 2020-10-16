package nl.hu.bep2.vliegmaatschappij.security;

import nl.hu.bep2.vliegmaatschappij.security.presentation.filter.JwtAuthenticationFilter;
import nl.hu.bep2.vliegmaatschappij.security.presentation.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public final static String LOGIN_PATH = "/login";
	public final static String REGISTER_PATH = "/register";

	@Value("${security.jwt.secret}")
	private String jwtSecret;

	@Value("${security.jwt.expiration-in-ms}")
	private Integer jwtExpirationInMs;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, REGISTER_PATH).permitAll()
				.antMatchers(HttpMethod.DELETE, REGISTER_PATH+"/*").permitAll() //delete user by username TODO weghalen
				.antMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
				.antMatchers(HttpMethod.GET,"/*.html").permitAll()
				.antMatchers(HttpMethod.GET,"/*.css").permitAll()
				.antMatchers(HttpMethod.GET,"/*.js").permitAll()
				.antMatchers(HttpMethod.GET,"/").permitAll()
				.anyRequest().authenticated()
				.and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(
                                LOGIN_PATH,
                                this.jwtSecret,
                                this.jwtExpirationInMs,
                                this.authenticationManager()
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(new JwtAuthorizationFilter(this.jwtSecret, this.authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
