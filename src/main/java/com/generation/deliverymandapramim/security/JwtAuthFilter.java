package com.generation.deliverymandapramim.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Filtro de autenticação JWT que intercepta as requisições HTTP
 * para validar o token JWT e autenticar o usuário.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Serviço responsável pela manipulação de tokens JWT

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Serviço que carrega os detalhes do usuário

    /**
     * Método que processa o filtro de autenticação.
     * 
     * @param request A requisição HTTP.
     * @param response A resposta HTTP.
     * @param filterChain A cadeia de filtros a serem aplicados.
     * @throws ServletException Se ocorrer um erro durante o processamento do filtro.
     * @throws IOException Se ocorrer um erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // Obtém o cabeçalho de autorização
        String token = null; // Inicializa a variável do token
        String username = null; // Inicializa a variável do nome de usuário
        
        
        try{
        	// Verifica se o cabeçalho de autorização está presente e começa com "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extrai o token do cabeçalho
                username = jwtService.extractUsername(token); // Extrai o nome de usuário do token
            }
            
            // Verifica se o nome de usuário foi extraído e se não há autenticação no contexto de segurança
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Carrega os detalhes do usuário
                 
                // Valida o token e, se for válido, cria um objeto de autenticação
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Adiciona detalhes da requisição
                    SecurityContextHolder.getContext().setAuthentication(authToken); // Define a autenticação no contexto de segurança
                }
            
            }
            // Continua a cadeia de filtros
            filterChain.doFilter(request, response);

        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException 
                | SignatureException | ResponseStatusException e){
        	// Se ocorrer uma exceção relacionada ao token, define o status da resposta como FORBIDDEN
        	response.setStatus(HttpStatus.FORBIDDEN.value());
            return; // Retorna para não continuar o processamento
        }
    }

}
