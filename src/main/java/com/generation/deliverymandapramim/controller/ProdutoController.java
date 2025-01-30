package com.generation.deliverymandapramim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.generation.deliverymandapramim.model.Produto;
import com.generation.deliverymandapramim.repository.CategoriaRepository;
import com.generation.deliverymandapramim.repository.ProdutoRepository;
import com.generation.deliverymandapramim.service.ProdutoService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas a produtos.
 * Este controlador fornece endpoints para criar, ler, atualizar e excluir produtos.
 */
@RestController
@RequestMapping("/produtos") // Mapeia as requisições para a URL /produtos
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite requisições de diferentes origens
public class ProdutoController {
	
	@Autowired // Injeção de dependência do repositório de produtos
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	/**
	 * Endpoint para obter todos os produtos.
	 * @return Lista de produtos.
	 */
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll()); // Retorna todos os produtos com status 200 OK
	}
	
	/**
	 * Endpoint para obter um produto pelo ID.
	 * @param id O ID do produto a ser buscado.
	 * @return O produto encontrado ou status 404 NOT FOUND se não existir.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta)) // Retorna o produto encontrado
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 se não encontrado
	}
	
	/**
	 * Endpoint para buscar produtos pelo nome, ignorando maiúsculas e minúsculas.
	 * @param nome O nome a ser buscado.
	 * @return Lista de produtos que contêm o nome fornecido.
	 */
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome)); // Retorna produtos que contêm o nome
	}
	
	@GetMapping("/recomendar-saudaveis")
	public ResponseEntity<List<Produto>> recomendarProdutosSaudaveis() {
	    List<Produto> produtosSaudaveis = produtoService.recomendarProdutosSaudaveis(); // Chama o serviço para obter produtos saudáveis
	    return ResponseEntity.ok(produtosSaudaveis); // Retorna a lista de produtos saudáveis
	}
	
	
	/**
	 * Endpoint para cadastrar um novo produto.
	 * Este método verifica se a categoria associada ao produto existe antes de realizar o cadastro.
	 * 
	 * @param produto O produto a ser cadastrado, que deve incluir uma categoria válida.
	 * @return Um ResponseEntity contendo o produto cadastrado com status 201 CREATED se a categoria existir.
	 * @throws ResponseStatusException Se a categoria associada ao produto não existir, retorna status 400 BAD REQUEST.
	 */
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		// Verifica se a categoria associada ao produto existe no repositório
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
			 // Salva o produto no repositório e retorna o produto cadastrado com status 201 CREATED
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(produtoRepository.save(produto));
		// Lança uma exceção se a categoria não existir, retornando status 400 BAD REQUEST
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria Não Existe!", null);
	}
	
	/**
	 * Endpoint para atualizar um produto existente.
	 * Este método verifica se o produto existe e se a categoria associada ao produto é válida antes de realizar a atualização.
	 * 
	 * @param produto O produto com as informações atualizadas, incluindo um ID e uma categoria válida.
	 * @return Um ResponseEntity contendo o produto atualizado com status 200 OK se a atualização for bem-sucedida,
	 *         ou status 404 NOT FOUND se o produto não existir.
	 * @throws ResponseStatusException Se a categoria associada ao produto não existir, retorna status 400 BAD REQUEST.
	 */
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		// Verifica se o produto existe no repositório
		if(produtoRepository.existsById(produto.getId())) {
			// Verifica se a categoria associada ao produto existe no repositório
			if(categoriaRepository.existsById(produto.getCategoria().getId()))
				// Atualiza o produto no repositório e retorna o produto atualizado com status 200 OK
				return ResponseEntity.status(HttpStatus.OK)
							.body(produtoRepository.save(produto)); // Atualiza e retorna o produto
			// Lança uma exceção se a categoria não existir, retornando status 400 BAD REQUEST
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria Não Existe!", null);
	
		}
		// Retorna status 404 NOT FOUND se o produto não existir
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	/**
	 * Endpoint para excluir um produto pelo ID.
	 * @param id O ID do produto a ser excluído.
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 NO CONTENT se a exclusão for bem-sucedida
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Lança exceção se o produto não existir
		
		produtoRepository.deleteById(id); // Exclui o produto
	}
}