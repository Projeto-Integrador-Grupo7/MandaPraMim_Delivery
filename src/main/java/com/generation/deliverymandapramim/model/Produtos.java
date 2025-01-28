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

@Entity
@Table(name = "tb_produtos")
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo Nome deve conter entre 5 a 100 caracteres.")
	private String nome; // tomate, arroz integral, salgadinho
	
	@NotBlank
	@Size(min = 5, max = 200, message = "O campo Descrição deve conter entre 5 a 200 caracteres.")
	private String descricao; // "", Camil, Doritos
	
	@NotNull
	@PositiveOrZero
	private Integer quantidade; // por unidade 10, 12, 5
	
	@PositiveOrZero
	@Column(precision = 10, scale = 2, nullable = false)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal preco; // preço por unidade
	
	@NotNull
	private Boolean saudavel;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Categorias categoria;
<<<<<<< HEAD
=======
	
	
	@ManyToOne
	@JsonIgnoreProperties("produtos")
	private Usuario usuario;
	
	
	
	

	public Boolean getSaudavel() {
		return saudavel;
	}


	public void setSaudavel(Boolean saudavel) {
		this.saudavel = saudavel;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
>>>>>>> f184e391118be9dd639fd55db83d71daa3c5e977


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


	public Categorias getCategorias() {
		return categoria;
	}


	public void setCategorias(Categorias categorias) {
		this.categoria = categorias;
	}

	
	

	
}
