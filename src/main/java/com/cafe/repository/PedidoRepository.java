package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
