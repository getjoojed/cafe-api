package com.cafe.controller;

import com.cafe.model.Produto;
import com.cafe.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> obterProdutoPorId(@PathVariable Long produtoId) {
        Produto produto = produtoService.obterProdutoPorId(produtoId);
        if (produto != null) {
            return ResponseEntity.ok().body(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}