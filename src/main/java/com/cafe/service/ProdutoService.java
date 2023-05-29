package com.cafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cafe.model.Produto;
import com.cafe.repository.ProdutoRepository;

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
    
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }
    
}