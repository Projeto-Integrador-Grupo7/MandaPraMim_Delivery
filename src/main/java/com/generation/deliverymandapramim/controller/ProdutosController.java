package com.generation.deliverymandapramim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.generation.deliverymandapramim.model.Produtos;
import com.generation.deliverymandapramim.repository.CategoriasRepository;
import com.generation.deliverymandapramim.repository.ProdutosRepository;
import com.generation.deliverymandapramim.service.ProdutoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	ProdutosRepository produtosRepository;
	
	@Autowired
	CategoriasRepository categoriasRepository;
	
	ProdutoService produtoService;

	
	@GetMapping("/all")
	public ResponseEntity <List<Produtos>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Produtos> getById(@PathVariable Long id){
		return produtosRepository.findById(id).
				map(resposta -> ResponseEntity.ok(resposta)).
				orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity <List<Produtos>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
		
	}
	
	
	@GetMapping("/saudaveis")
    public ResponseEntity<List<Produtos>> getProdutosSaudaveis() {
        List<Produtos> produtosSaudaveis = produtosRepository.findBySaudavelTrue();
        if (produtosSaudaveis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(produtosSaudaveis);
    }
	

	@PostMapping("/cadastrar")
	public ResponseEntity <Produtos> post(@Valid @RequestBody Produtos produtos){
		if(categoriasRepository.existsById(produtos.getCategorias().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe.", null);
		
	}


	@PutMapping("/atualizar")
	public ResponseEntity <Produtos> put(@Valid @RequestBody Produtos produtos){
		if(produtosRepository.existsById(produtos.getId())) {
			if(categoriasRepository.existsById(produtos.getCategorias().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtos));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe.", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
	}


	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional <Produtos> produtos = produtosRepository.findById(id);
		
		if(produtos.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		produtosRepository.deleteById(id);
	}
	
	
}