package com.generation.deliverymandapramim.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Classe que representa o modelo de Usuário no sistema de Delivery de Mercado.
 * Esta classe é mapeada para a tabela "tb_usuarios" no banco de dados.
 */
@Entity
@Table(name = "tb_usuarios")
public class Usuario {
	
	// Identificador único do usuário, gerado automaticamente pelo banco de dados
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Nome do usuário, não pode ser nulo
	@NotNull(message = "O Atributo Nome é Obrigatório!")
	private String nome;
	
	// Nome de usuário, deve ser um email válido e não pode ser nulo
	@Schema(example = "email@email.com.br")
	@NotNull(message = "O Atributo Usuário é Obrigatório!")
	@Email(message = "O Atributo Usuário deve ser um email válido!")
	private String usuario;
	
	// Senha do usuário, não pode ser em branco e deve ter no mínimo 8 caracteres
	@NotBlank(message = "O Atributo Senha é Obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;
	
	// URL da foto do usuário, pode ter no máximo 5000 caracteres
	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;
	
	// Relacionamento One-to-Many com a classe Produto
	// Um usuário pode ter vários produtos associados a ele
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario") // Ignora a propriedade 'usuario' na serialização dos produtos para evitar loops infinitos
	private List<Produto> produto;

	// Métodos getters e setters para acessar e modificar os atributos da classe
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	
	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	
	public Usuario() { }
	
}
