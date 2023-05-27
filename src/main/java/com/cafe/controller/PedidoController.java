package com.cafe.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.model.Pedido;
import com.cafe.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

    @PostMapping
    public Pedido criarPedido() {
        return pedidoService.criarPedido();
    }

	@PostMapping("/{pedidoId}/adicionarProduto")
	public void adicionarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId, @RequestParam int quantidade) {
		pedidoService.adicionarProduto(pedidoId, produtoId, quantidade);
	}

	@PostMapping("/{pedidoId}/retirarProduto")
	public void retirarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId, @RequestParam int quantidade) {
		pedidoService.retirarProduto(pedidoId, produtoId, quantidade);
	}

	@GetMapping("/{pedidoId}/calcularPrecoTotal")
	public BigDecimal calcularPrecoTotal(@PathVariable Long pedidoId) {
		return pedidoService.calcularPrecoTotal(pedidoId);
	}

	@PostMapping("/{pedidoId}/fecharPedido")
	public void fecharPedido(@PathVariable Long pedidoId, @RequestParam BigDecimal valorPagamento) {
		pedidoService.fecharPedido(pedidoId, valorPagamento);
	}

	@PostMapping("/calcularPrecoTotalPedido")
	public BigDecimal calcularPrecoTotalPedido(@RequestParam Long pedidoId, @RequestBody Map<Long, Integer> produtos) {
		return pedidoService.calcularPrecoTotalPedido(pedidoId, produtos);
	}

}
