package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Security Configuration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz
				.requestMatchers("/public/**").hasRole("USER")
				//when we using roll we dont need permitAll
				//.permitAll()
				.anyRequest()
				.authenticated())
		.httpBasic(Customizer.withDefaults());

		return http.build();

	}

	// user Configuration
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.builder()
				.username("Kashyap")
				.password(passwordEncoder().encode("user123"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("Karan")
				.password(passwordEncoder().encode("user123"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder() ;
	}

}
