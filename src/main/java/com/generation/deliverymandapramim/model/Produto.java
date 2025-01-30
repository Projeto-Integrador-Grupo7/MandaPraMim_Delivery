package com.generation.deliverymandapramim.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;


/**
 * Classe que representa o modelo de Produto no sistema de Delivery de Mercado.
 * Esta classe é mapeada para a tabela "tb_produtos" no banco de dados.
 */
@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	// Identificador único do produto, gerado automaticamente pelo banco de dados
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Nome do produto, não pode ser vazio e deve ter entre 5 a 100 caracteres
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo Nome deve conter entre 5 a 100 caracteres.")
	private String nome;
    
	// Descrição do produto, não pode ser vazia e deve ter entre 5 a 255 caracteres
	@NotBlank
	@Size(min = 5, max = 255, message = "O campo Descrição deve conter entre 5 a 200 caracteres.")
	private String descricao;
	
	// Quantidade disponível do produto, não pode ser nula e deve ser zero ou positiva
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	// Preço do produto, deve ser um valor positivo ou zero, com precisão de 10 dígitos no total e 2 após a vírgula
	@PositiveOrZero
	@Column(precision = 10, scale = 2, nullable = false)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal preco;
	
	private boolean saudavel; // Indica se o produto é saudável
	
	/**
	 * Relacionamento Many-to-One entre Produto e Categoria.
	 * Cada produto pode estar associado a uma única categoria, 
	 * enquanto uma categoria pode ter vários produtos.
	 */
	@ManyToOne
	@JsonIgnoreProperties("produto") // Ignora a propriedade 'produto' na serialização da categoria para evitar loops infinitos
	private Categoria categoria;  // A categoria à qual o produto pertence
	
	/**
	 * Relacionamento Many-to-One entre Produto e Usuario.
	 * Cada produto pode estar associado a uma único usuário, 
	 * enquanto um usário pode ter vários produtos.
	 */
	@ManyToOne
	@JsonIgnoreProperties("produto") // Ignora a propriedade 'produto' na serialização da categoria para evitar loops infinitos
	private Usuario usuario; // A categoria à qual o produto pertence
	
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
	
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public boolean isSaudavel() {
		return saudavel;
	}

	public void setSaudavel(boolean saudavel) {
		this.saudavel = saudavel;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
