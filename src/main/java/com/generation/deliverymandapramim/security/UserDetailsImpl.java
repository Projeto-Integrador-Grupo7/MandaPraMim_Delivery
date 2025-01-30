package com.generation.deliverymandapramim.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.deliverymandapramim.model.Usuario;

/**
 * Implementação da interface UserDetails do Spring Security.
 * Esta classe é usada para representar um usuário autenticado no sistema,
 * incluindo suas credenciais e autoridades.
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L; // Versão da classe para serialização

	private String userName; // Nome de usuário (login)
	private String password; // Senha do usuário
	private List<GrantedAuthority> authorities; // Lista de autoridades (permissões) do usuário

	/**
	 * Construtor que inicializa o UserDetailsImpl com um objeto Usuario.
	 * 
	 * @param user O objeto Usuario que contém as informações do usuário.
	 */
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario(); // Define o nome de usuário
		this.password = user.getSenha(); // Define a senha
	}
	
	// Construtor padrão
	public UserDetailsImpl() {	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		// Retorna as autoridades (permissões) do usuário
		return authorities;
	}

	@Override
	public String getPassword() {

		// Retorna a senha do usuário
		return password;
	}

	@Override
	public String getUsername() {
		
		// Retorna o nome de usuário
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Indica se a conta do usuário não está expirada
		return true; // Para simplificação, sempre retorna true
	}

	@Override
	public boolean isAccountNonLocked() {
		// Indica se a conta do usuário não está bloqueada
		return true; // Para simplificação, sempre retorna true
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Indica se as credenciais do usuário não estão expiradas
		return true; // Para simplificação, sempre retorna true
	}

	@Override
	public boolean isEnabled() {
		// Indica se a conta do usuário está habilitada
		return true; // Para simplificação, sempre retorna true
	}


}
