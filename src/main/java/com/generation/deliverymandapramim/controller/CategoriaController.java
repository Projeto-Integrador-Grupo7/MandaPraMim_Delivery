	package com.generation.deliverymandapramim.controller;
	
	import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.server.ResponseStatusException;

import com.generation.deliverymandapramim.model.Categoria;
import com.generation.deliverymandapramim.repository.CategoriaRepository;

import jakarta.validation.Valid;
	
	/**
	 * Controlador REST para gerenciar operações relacionadas a categorias.
	 * Este controlador fornece endpoints para criar, ler, atualizar e excluir categorias.
	 */
	@RestController
	@RequestMapping("/categorias") // Mapeia as requisições para a URL /categorias
	@CrossOrigin(origins = "*", allowedHeaders = "*") // Injeção de dependência do repositório de categorias
	public class CategoriaController {
		
		@Autowired
		private CategoriaRepository categoriaRepository; // Injeção de dependência do repositório de categorias
		
		
		/**
		 * Endpoint para obter todas as categorias.
		 * @return Lista de categorias.
		 */
		@GetMapping
		public ResponseEntity<List<Categoria>> getAll() {
			return ResponseEntity.ok(categoriaRepository.findAll()); // Retorna todas as categorias com status 200 OK
		}
		
		/**
		 * Endpoint para obter uma categoria pelo ID.
		 * @param id O ID da categoria a ser buscada.
		 * @return A categoria encontrada ou status 404 NOT FOUND se não existir.
		 */
		@GetMapping("/{id}")
		public ResponseEntity<Categoria> getById(@PathVariable Long id) {
			return categoriaRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta)) // Retorna a categoria encontrada
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 se não encontrado
		}
		
		/**
		 * Endpoint para buscar categorias pelo nome, ignorando maiúsculas e minúsculas.
		 * @param nome O nome a ser buscado.
		 * @return Lista de categorias que contêm o nome fornecido.
		 */
		@GetMapping("/nome/{nome}")
		public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nome) {
			return ResponseEntity.ok(categoriaRepository
					.findAllByNomeContainingIgnoreCase(nome)); // Retorna categorias que contêm o nome
		}
	
		/**
		 * Endpoint para cadastrar uma nova categoria.
		 * @param categoria A categoria a ser cadastrada.
		 * @return A categoria cadastrada com status 201 CREATED.
		 */
		@PostMapping
		public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(categoriaRepository.save(categoria)); // Salva e retorna a categoria cadastrada
		}
		
		/**
		 * Endpoint para atualizar uma categoria existente.
		 * @param categoria A categoria com as informações atualizadas.
		 * @return A categoria atualizada com status 200 OK ou 404 NOT FOUND se não existir.
		 */
		@PutMapping
		public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
			return categoriaRepository.findById(categoria.getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(categoriaRepository.save(categoria))) // Atualiza e retorna a categoria
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 se não encontrado
		}
		
		/**
		 * Endpoint para excluir uma categoria pelo ID.
		 * @param id O ID da categoria a ser excluída.
		 */
		@ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 NO CONTENT se a exclusão for bem-sucedida
		@DeleteMapping("/{id}")
		public void delete (@PathVariable Long id) {
			Optional<Categoria> categoria = categoriaRepository.findById(id); 
			
			if(categoria.isEmpty()) 
				throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Lança exceção se a categoria não existir
			
			categoriaRepository.deleteById(id); // Exclui a categoria
		}
		
	}
