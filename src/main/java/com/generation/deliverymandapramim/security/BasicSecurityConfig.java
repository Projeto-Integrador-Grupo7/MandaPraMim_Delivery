package com.generation.deliverymandapramim.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration 
@EnableWebSecurity
public class BasicSecurityConfig {
	@Autowired //Spring injeta automaticamente componentes da JwtAuthFilter
	private JwtAuthFilter authFilter;

	 //Configura um serviço de detalhes do usuário personalizado, que é responsável por carregar os dados 
	 //do usuário (e-mail, senha e permissões) do banco de dados.
	
	@Bean
	UserDetailsService userDetailsService() {   

		return new UserDetailsServiceImpl();
	}

	@Bean  
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean  
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean //Gerencia o processo de autenticaçao com base nas configuraçoes fornecidas
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean 
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Configura a cadeia de filtros de segurança da aplicação.

		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Define que a aplicação não manterá sessões (Stateless).
				.csrf(csrf -> csrf.disable()).cors(withDefaults());

		http.authorizeHttpRequests((auth) -> auth  
				.requestMatchers("/usuarios/logar").permitAll() 
				.requestMatchers("/usuarios/cadastrar").permitAll()
				.requestMatchers("/error/**").permitAll()
				.requestMatchers(HttpMethod.OPTIONS).permitAll()  
				.anyRequest().authenticated()) // Todas as outras requisições exigem autenticação.
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro personalizado JwtAuthFilter antes do filtro padrão de autenticação.
				.httpBasic(withDefaults());

		return http.build();

	}
}
