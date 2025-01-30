package com.generation.deliverymandapramim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.deliverymandapramim.model.Usuario;
import com.generation.deliverymandapramim.model.UsuarioLogin;
import com.generation.deliverymandapramim.repository.UsuarioRepository;
import com.generation.deliverymandapramim.service.UsuarioService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas a usuários.
 * Este controlador fornece endpoints para criar, ler, atualizar e autenticar usuários.
 */
@RestController
@RequestMapping("/usuarios") // Mapeia as requisições para a URL /usuarios
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite requisições de diferentes origens
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService; // Serviço que contém as regras de negócio para usuários

	@Autowired
	private UsuarioRepository usuarioRepository; // Repositório para acessar os dados do usuário
	
	/**
	 * Endpoint para obter todos os usuários.
	 * 
	 * @return Lista de usuários com status 200 OK.
	 */
	@GetMapping("/all")
	public ResponseEntity <List<Usuario>> getAll(){
		
		return ResponseEntity.ok(usuarioRepository.findAll()); // Retorna todos os usuários
		
	}
	
	/**
	 * Endpoint para obter um usuário pelo ID.
	 * 
	 * @param id O ID do usuário a ser buscado.
	 * @return O usuário encontrado ou status 404 NOT FOUND se não existir.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id) 
			.map(resposta -> ResponseEntity.ok(resposta)) // Retorna o usuário encontrado
			.orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
	}
	
	/**
	 * Endpoint para autenticar um usuário.
	 * 
	 * @param usuarioLogin O objeto UsuarioLogin contendo as credenciais do usuário.
	 * @return O objeto UsuarioLogin preenchido com os dados do usuário autenticado ou status 401 UNAUTHORIZED se a autenticação falhar.
	 */
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		
		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta)) // Retorna o usuário autenticado
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // Retorna 401 se a autenticação falhar
	}
    
	/**
	 * Endpoint para cadastrar um novo usuário.
	 * 
	 * @param usuario O objeto Usuario a ser cadastrado.
	 * @return O usuário cadastrado com status 201 CREATED ou status 400 BAD REQUEST se o cadastro falhar.
	 */
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {

		return usuarioService.cadastrarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta)) // Retorna o usuário cadastrado
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); // Retorna 400 se o cadastro falhar

	}
	
	/**
	 * Endpoint para atualizar um usuário existente.
	 * 
	 * @param usuario O objeto Usuario com as informações atualizadas.
	 * @return O usuário atualizado com status 200 OK ou status 404 NOT FOUND se o usuário não existir.
	 */
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
		
		return usuarioService.atualizarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta)) // Retorna o usuário atualizado
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 se o usuário não existir
		
	}

}