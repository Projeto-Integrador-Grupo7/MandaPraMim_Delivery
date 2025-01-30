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

/**
 * Classe de configuração de segurança básica para a aplicação.
 * Esta classe configura a autenticação e autorização usando Spring Security.
 */
@Configuration
@EnableWebSecurity // Habilita a configuração de segurança da aplicação
public class BasicSecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter; // Filtro de autenticação JWT
    
    /**
     * Define um bean para o serviço de detalhes do usuário.
     * 
     * @return Uma instância de UserDetailsService.
     */
    @Bean
    UserDetailsService userDetailsService() {

        return new UserDetailsServiceImpl(); // Retorna a implementação do serviço de detalhes do usuário
    }
    

    /**
     * Define um bean para o codificador de senhas.
     * 
     * @return Uma instância de PasswordEncoder usando BCrypt.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna um codificador de senhas BCrypt
    }

    /**
     * Define um bean para o provedor de autenticação.
     * 
     * @return Uma instância de AuthenticationProvider configurada com o serviço de detalhes do usuário e o codificador de senhas.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService()); // Define o serviço de detalhes do usuário
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Define o codificador de senhas
        return authenticationProvider; // Retorna o provedor de autenticação
    }
    
    /**
     * Define um bean para o gerenciador de autenticação.
     * 
     * @param authenticationConfiguration Configuração de autenticação.
     * @return Uma instância de AuthenticationManager.
     * @throws Exception Se ocorrer um erro ao obter o gerenciador de autenticação.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Retorna o gerenciador de autenticação
    }
    
    /**
     * Define um bean para a cadeia de filtros de segurança.
     * 
     * @param http Configuração de segurança HTTP.
     * @return A cadeia de filtros de segurança configurada.
     * @throws Exception Se ocorrer um erro ao configurar a segurança HTTP.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// Configura a gestão de sessão para ser stateless (sem estado)
    	http
	        .sessionManagement(management -> management
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
	        		.csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF
	        		.cors(withDefaults()); // Habilita CORS com configurações padrão
    	
    	// Configura as regras de autorização para as requisições HTTP
    	http
	        .authorizeHttpRequests((auth) -> auth
	                .requestMatchers("/usuarios/logar").permitAll() // Permite acesso sem autenticação
	                .requestMatchers("/usuarios/cadastrar").permitAll() // Permite acesso sem autenticação
	                .requestMatchers("/error/**").permitAll() // Permite acesso sem autenticação
	                .requestMatchers(HttpMethod.OPTIONS).permitAll() // Permite requisições OPTIONS sem autenticação
	                .anyRequest().authenticated()) // Requer autenticação para qualquer outra requisição
	        .authenticationProvider(authenticationProvider()) // Define o provedor de autenticação
	        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de autenticação JWT antes do filtro de autenticação padrão
	        .httpBasic(withDefaults()); // Habilita autenticação básica com configurações padrão

		return http.build(); // Retorna a cadeia de filtros de segurança configurada

    }

}