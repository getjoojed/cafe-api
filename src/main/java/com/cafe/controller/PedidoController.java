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
import com.cafe.model.Produto;
import com.cafe.repository.PedidoRepository;
import com.cafe.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;

	public PedidoController(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.produtoRepository = produtoRepository;
	}

	@PostMapping
	public Pedido criarPedido() {
		Pedido pedido = new Pedido();
		return pedidoRepository.save(pedido);
	}

	@PostMapping("/{pedidoId}/adicionarProduto")
	public void adicionarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId,
			@RequestParam int quantidade) {

		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
	}

	@PostMapping("/{pedidoId}/retirarProduto")
	public void retirarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId,
			@RequestParam int quantidade) {

		Pedido pedido = pedidoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
	}

	@GetMapping("/{pedidoId}/calcularPrecoTotal")
	public BigDecimal calcularPrecoTotal(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		return BigDecimal.ZERO;
	}

	@PostMapping("/{pedidoId}/fecharPedido")
	public void fecharPedido(@PathVariable Long pedidoId, @RequestParam BigDecimal valorPagamento) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

	}

	@PostMapping("/calcularPrecoTotalPedido")
	public BigDecimal calcularPrecoTotalPedido(@RequestParam Long pedidoId, @RequestBody Map<Long, Integer> produtos) {

		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
		
		
		return BigDecimal.ZERO;

	}

}
