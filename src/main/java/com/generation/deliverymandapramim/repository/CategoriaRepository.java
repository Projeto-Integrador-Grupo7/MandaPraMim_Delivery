package com.generation.deliverymandapramim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.deliverymandapramim.model.Categoria;

/**
 * Interface que representa o repositório de Categorias.
 * Esta interface herda de JpaRepository, o que fornece métodos prontos para operações CRUD.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	/**
	 * Método para buscar todas as categorias cujo nome contém a string fornecida,
	 * ignorando diferenças entre maiúsculas e minúsculas.
	 *
	 * @param nome A string que será utilizada para filtrar as categorias pelo nome.
	 * @return Uma lista de categorias que contêm a string fornecida no nome.
	 */
	public List<Categoria> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}

