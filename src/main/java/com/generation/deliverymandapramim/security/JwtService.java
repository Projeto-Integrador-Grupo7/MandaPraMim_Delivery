package com.generation.deliverymandapramim.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


/**
 * Classe responsável pela geração e validação de tokens JWT (JSON Web Tokens).
 * Esta classe fornece métodos para criar, extrair e validar informações contidas em tokens JWT.
 */
@Component
public class JwtService {
	
	// Chave secreta utilizada para assinar os tokens JWT
	public static final String SECRET = "gvYJqKFAOEwbUfW2Tu80O7WjS9pLSDiYRArQVyTnk59MEID1/NIBy+ym2lgxEr9a";

	/**
	 * Obtém a chave de assinatura a partir da chave secreta.
	 * 
	 * @return A chave de assinatura para a criação de tokens JWT.
	 */
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET); // Decodifica a chave secreta em base64
		return Keys.hmacShaKeyFor(keyBytes);  // Cria a chave HMAC para assinatura
	}
	

	/**
	 * Extrai todas as reivindicações (claims) de um token JWT.
	 * 
	 * @param token O token JWT do qual as reivindicações serão extraídas.
	 * @return As reivindicações contidas no token.
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build() // Define a chave de assinatura
				.parseClaimsJws(token).getBody(); // Analisa o token e retorna as reivindicações
	}
	
	/**
	 * Extrai uma reivindicação específica do token JWT.
	 * 
	 * @param token O token JWT do qual a reivindicação será extraída.
	 * @param claimsResolver Função que define como a reivindicação será extraída.
	 * @return O valor da reivindicação extraída.
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token); // Extrai todas as reivindicações
		return claimsResolver.apply(claims); // Aplica a função para obter a reivindicação desejada
	}

	/**
	 * Extrai o nome de usuário (subject) do token JWT.
	 * 
	 * @param token O token JWT do qual o nome de usuário será extraído.
	 * @return O nome de usuário contido no token.
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject); // Extrai o nome de usuário
	}
	
	/**
	 * Extrai a data de expiração do token JWT.
	 * 
	 * @param token O token JWT do qual a data de expiração será extraída.
	 * @return A data de expiração do token.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration); // Extrai a data de expiração
	}

	/**
	 * Verifica se o token JWT está expirado.
	 * 
	 * @param token O token JWT a ser verificado.
	 * @return true se o token estiver expirado, false caso contrário.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual
	}
	
	/**
	 * Valida um token JWT, verificando se o nome de usuário e a expiração estão corretos.
	 * 
	 * @param token O token JWT a ser validado.
	 * @param userDetails Detalhes do usuário que estão sendo autenticados.
	 * @return true se o token for válido, false caso contrário.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token); // Extrai o nome de usuário do token
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Verifica se o token é válido
	}
	
	/**
	 * Cria um token JWT com as reivindicações e o nome de usuário fornecidos.
	 * 
	 * @param claims As reivindicações a serem incluídas no token.
	 * @param userName O nome de usuário a ser associado ao token.
	 * @return O token JWT gerado.
	 */
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims) // Define as reivindicações do token
					.setSubject(userName) // Define o nome de usuário como o assunto do token
					.setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão do token
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Define a data de expiração (1 hora a partir da emissão)
					.signWith(getSignKey(), SignatureAlgorithm.HS256) // Assina o token com a chave secreta usando o algoritmo HS256
					.compact(); // Compacta e retorna o token JWT gerado
	}
	
	
	/**
	 * Gera um token JWT para o nome de usuário fornecido.
	 * 
	 * @param userName O nome de usuário para o qual o token será gerado.
	 * @return O token JWT gerado.
	 */
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>(); // Cria um mapa para as reivindicações
		return createToken(claims, userName); // Chama o método createToken para gerar o token
	}

}
