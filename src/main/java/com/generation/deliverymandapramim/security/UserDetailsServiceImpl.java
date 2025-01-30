package com.generation.deliverymandapramim.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.deliverymandapramim.model.Usuario;
import com.generation.deliverymandapramim.repository.UsuarioRepository;

/**
 * Implementação da interface UserDetailsService do Spring Security.
 * Esta classe é responsável por carregar os detalhes do usuário durante o processo de autenticação.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository; // Repositório para acessar os dados do usuário
	
	
	/**
	 * Carrega um usuário pelo nome de usuário (login).
	 * 
	 * @param userName O nome de usuário a ser buscado.
	 * @return Um objeto UserDetails contendo as informações do usuário.
	 * @throws UsernameNotFoundException Se o usuário não for encontrado.
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		// Busca o usuário no repositório pelo nome de usuário
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);
		// Se o usuário for encontrado, retorna um UserDetailsImpl com as informações do usuário
		
		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
		else
			// Lança uma exceção se o usuário não for encontrado, retornando status FORBIDDEN
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
	}

}
