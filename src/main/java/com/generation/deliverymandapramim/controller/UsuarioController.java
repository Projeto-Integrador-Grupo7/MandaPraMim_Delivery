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

import com.generation.deliverymandapramim.model.Usuario;
import com.generation.deliverymandapramim.repository.UsuarioRepository;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {

		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> post(@RequestBody @Valid Usuario usuario) {
		Usuario usuarioSalva = usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalva);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@RequestBody @Valid Usuario usuario) {
		if (usuarioRepository.existsById(usuario.getId())) { 
			Usuario usuarioAtualizada = usuarioRepository.save(usuario);

			return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizada);
		} else {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isEmpty()) // Verifica se a postagem existe ----> poderia usar o .map nesse caso tambem
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		usuarioRepository.deleteById(id);
} 
} 
