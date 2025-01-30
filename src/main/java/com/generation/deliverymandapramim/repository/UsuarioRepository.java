package com.generation.deliverymandapramim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.deliverymandapramim.model.Usuario;

/**
 * Interface que representa o repositório de Usuários.
 * Esta interface herda de JpaRepository, o que fornece métodos prontos para operações CRUD.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * Método para buscar um usuário pelo nome de usuário (login).
	 * 
	 * @param usuario O nome de usuário a ser buscado.
	 * @return Um Optional contendo o usuário encontrado, ou vazio se não existir.
	 */
	public Optional<Usuario> findByUsuario(String usuario);
}
