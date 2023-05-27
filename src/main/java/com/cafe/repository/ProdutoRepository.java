package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
