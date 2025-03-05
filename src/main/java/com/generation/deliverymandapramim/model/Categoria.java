package com.generation.deliverymandapramim.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * Classe que representa o modelo de Categoria no sistema de Delivery de Mercado.
 * Esta classe é mapeada para a tabela "tb_categorias" no banco de dados.
 */
@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	// Identificador único da categoria, gerado automaticamente pelo banco de dados
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Nome da categoria, não pode ser vazio e deve ter entre 5 a 100 caracteres
	@NotBlank(message = "O campo Nome é obrigatório para a digitação")
	@Size(min = 5, max = 100, message = "O campo Nome deve conter no mínimo 05 e no máximo 100 caracteres")
	private String nome;
	
	// Descrição da categoria, não pode ser vazia e deve ter entre 5 a 255 caracteres
	@NotBlank(message = "O campo Descrição é obrigatório para a digitação")
	@Size(min = 5, max = 255, message = "O campo Descrição deve conter no mínimo 05 e no máximo 255 caracteres")
	private String descricao;

	// URL da foto do usuário, pode ter no máximo 5000 caracteres
	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;
	
	// Relacionamento One-to-Many com a classe Produto
	// Uma categoria pode ter vários produtos associados a ela
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria") // Ignora a propriedade 'categoria' na serialização dos produtos para evitar loops infinitos
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
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
