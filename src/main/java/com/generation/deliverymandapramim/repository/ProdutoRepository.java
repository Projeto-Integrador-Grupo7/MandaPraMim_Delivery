package com.generation.deliverymandapramim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.deliverymandapramim.model.Produto;

/**
 * Interface que representa o repositório de Produtos.
 * Esta interface herda de JpaRepository, o que fornece métodos prontos para operações CRUD.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	// Aqui você pode adicionar métodos personalizados, se necessário.
    // Por exemplo, métodos para buscar produtos por nome, categoria, etc.
	
	
	/**
	 * Método que busca todos os produtos cujo nome contém a string fornecida, 
	 * ignorando diferenças entre maiúsculas e minúsculas.
	 *
	 * @param nome A string que será utilizada para filtrar os produtos pelo nome.
	 * @return Uma lista de produtos que contêm a string fornecida no nome.
	 */
	public List <Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String titulo);
	
	
	List<Produto> findBySaudavelTrue(); // Busca produtos que são saudáveis
}
