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

@Service
public class UserDetailsServiceImpl implements UserDetailsService { 

	@Autowired
	private UsuarioRepository usuarioRepository; // Injeta automaticamente a instância do repositório de usuários

	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		// Busca o usuário no banco de dados pelo nome de usuário (userName)
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);

		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
		
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		

	}

}
