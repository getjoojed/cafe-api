package com.cafe.service;

import com.cafe.model.Produto;
import com.cafe.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto obterProdutoPorId(Long produtoId) {
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        return produtoOptional.orElse(null);
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }
}