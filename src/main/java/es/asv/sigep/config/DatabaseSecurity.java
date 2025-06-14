package es.asv.sigep.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DatabaseSecurity {

	@Autowired
	private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/bootstrap/**", "/images/**").permitAll()
				.requestMatchers("/persona/alumnos", "/practica/form/*", "/practica/practicas", "/practica/nueva",
						"/registro/registros/*")
				.hasRole("P").requestMatchers("/registro/calendario/*", "/registro/fecha/*").hasRole("E")
				.requestMatchers("/", "/registrar", "/guardarProfe", "error").permitAll().anyRequest().authenticated())
				.formLogin(form -> form.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// TODO encirptar contraseña

}
