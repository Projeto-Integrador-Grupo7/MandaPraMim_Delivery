package com.generation.deliverymandapramim.model;

/**
 * Classe que representa o modelo de Usuário para fins de login.
 * Esta classe é utilizada para armazenar as informações do usuário
 * durante o processo de autenticação, incluindo um token de sessão.
 */
public class UsuarioLogin {
	
	// Identificador único do usuário
	private Long id;
	
	// Nome do usuário
	private String nome;
	
	// Nome de usuário, geralmente um email ou nome de login
	private String usuario;
	
	// Senha do usuário, utilizada para autenticação
	private String senha;
	
	// URL da foto do usuário, se disponível
	private String foto;
	
	// Token de autenticação gerado após o login bem-sucedido
	private String token;
	
	// Métodos getters e setters para acessar e modificar os atributos da classe
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

