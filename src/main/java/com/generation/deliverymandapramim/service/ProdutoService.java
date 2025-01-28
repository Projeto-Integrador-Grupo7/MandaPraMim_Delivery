package com.generation.deliverymandapramim.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation.deliverymandapramim.model.Produtos;
import com.generation.deliverymandapramim.repository.ProdutosRepository;

@Service
public class ProdutoService {

	private ProdutosRepository produtoRepository;

	public Optional<Boolean> recomendarProdutos(Long id) {
		// Buscar o produto pelo ID
		Optional<Produtos> produto = produtoRepository.findById(id);

		// Verificar se o produto existe
		if (produto.isPresent()) {
			Boolean saudavel = produto.get().getSaudavel();

			// Lógica de recomendação com base no atributo 'saudável'
			if (Boolean.TRUE.equals(saudavel)) {
				return Optional.of(true); // Produto recomendado
			} else {
				return Optional.of(false); // Produto não recomendado
			}
		}

		// Retornar vazio se o produto não for encontrado
		return Optional.empty();
	}
}