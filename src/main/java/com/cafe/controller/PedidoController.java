package com.cafe.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.model.Pedido;
import com.cafe.request.AdicionarProdutoRequest;
import com.cafe.request.FecharPedidoRequest;
import com.cafe.request.RetirarProdutoRequest;
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
    public void adicionarProduto(@PathVariable("pedidoId") Long pedidoId, @RequestBody AdicionarProdutoRequest request) {
        pedidoService.adicionarProduto(pedidoId, request.getProdutoId(), request.getQuantidade());
    }

    @PostMapping("/{pedidoId}/retirarProduto")
    public void retirarProduto(@PathVariable Long pedidoId, @RequestBody RetirarProdutoRequest request) {
        pedidoService.retirarProduto(pedidoId, request.getProdutoId(), request.getQuantidade());
    }

	@GetMapping("/{pedidoId}/calcularPrecoTotal")
	public BigDecimal calcularPrecoTotal(@PathVariable Long pedidoId) {
		return pedidoService.calcularPrecoTotal(pedidoId);
	}
	
	@GetMapping("/{pedidoId}")
	public ResponseEntity<Pedido> getPedido(@PathVariable Long pedidoId) {
	    Pedido pedido = pedidoService.getPedido(pedidoId);
	    if (pedido != null) {
	        return ResponseEntity.ok(pedido);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping("/{pedidoId}/fecharPedido")
	public ResponseEntity<BigDecimal> fecharPedido(@PathVariable Long pedidoId, @RequestBody FecharPedidoRequest request) {
	    BigDecimal troco = pedidoService.fecharPedido(pedidoId, request.getValorPagamento());
	    return ResponseEntity.ok(troco);
	}

	@GetMapping("/{pedidoId}/calcularPrecoTotalPedido")
	public BigDecimal calcularPrecoTotalPedido(@PathVariable Long pedidoId, @RequestParam List<Long> produtoIds, @RequestParam List<Integer> quantidades) {
	    return pedidoService.calcularPrecoTotalPedido(pedidoId, produtoIds, quantidades);
	}

}
