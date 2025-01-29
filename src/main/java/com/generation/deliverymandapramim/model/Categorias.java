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

@Entity
@Table(name = "tb_categorias")
public class Categorias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo Nome é obrigatório para a digitação")
	@Size(min = 5, max = 100, message = "O campo Nome deve conter no mínimo 05 e no máximo 100 caracteres")
	private String nome; // Alimentos saudaveis
	
	@NotBlank(message = "O campo Descrição é obrigatório para a digitação")
	@Size(min = 5, max = 255, message = "O campo Descrição deve conter no mínimo 05 e no máximo 255 caracteres")
	private String descricao; // 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categorias")
	private List<Produtos> produtos;
	
	
	public List<Produtos> getProduto() {
		return produtos;
	}

	public void setProduto(List<Produtos> produto) {
		this.produtos = produto;
	}

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
	
	
}
