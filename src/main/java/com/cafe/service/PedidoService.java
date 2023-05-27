package com.cafe.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.model.Pedido;
import com.cafe.model.Produto;
import com.cafe.repository.PedidoRepository;
import com.cafe.repository.ProdutoRepository;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;

	public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.produtoRepository = produtoRepository;
	}

	public Pedido criarPedido() {
		Pedido pedido = new Pedido();
		return pedidoRepository.save(pedido);
	}

	public void adicionarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId,
			@RequestParam int quantidade) {

		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

		for (int i = 0; i < quantidade; i++) {
			pedido.getProdutos().add(produto);
		}

		pedidoRepository.save(pedido);

	}

	public void retirarProduto(@PathVariable Long pedidoId, @RequestParam Long produtoId,
			@RequestParam int quantidade) {

		Pedido pedido = pedidoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

		List<Produto> produtosPedido = pedido.getProdutos();

		int quantidadeRetirada = 0;
		for (int i = 0; i < produtosPedido.size(); i++) {
			if (produtosPedido.get(i).getId().equals(produtoId)) {
				produtosPedido.remove(i);
				quantidadeRetirada++;
				i--;
			}

			if (quantidadeRetirada >= quantidade) {
				break;
			}
		}
		pedidoRepository.save(pedido);
	}

	public BigDecimal calcularPrecoTotal(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		BigDecimal precoTotal = BigDecimal.ZERO;
		List<Produto> produtos = pedido.getProdutos();

		for (Produto produto : produtos) {
			precoTotal = precoTotal.add(produto.getPreco());
		}

		return precoTotal;
	}

	public void fecharPedido(@PathVariable Long pedidoId, @RequestParam BigDecimal valorPagamento) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
		
		BigDecimal precoTotal = calcularPrecoTotal(pedidoId);
		
		if(valorPagamento.compareTo(precoTotal) >= 0) {
			pedido.setFechado(true);
			pedidoRepository.save(pedido);
		} else {
			throw new IllegalArgumentException("Valor de pagamento insuficiente.");
		}
		
	}

	public BigDecimal calcularPrecoTotalPedido(@RequestParam Long pedidoId, @RequestBody Map<Long, Integer> produtos) {
		BigDecimal precoTotal = BigDecimal.ZERO;
		
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		for(Map.Entry<Long, Integer> entry : produtos.entrySet()) {
			Long produtoId = entry.getKey();
			Integer quantidade = entry.getValue();
			
			Produto produto = produtoRepository.findById(pedidoId)
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
					
			BigDecimal precoProduto = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
			precoTotal = precoTotal.add(precoProduto);
		}
		
		return precoTotal;
	}

}
