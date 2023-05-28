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
import com.cafe.request.FecharPedidoRequest;

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

	public void adicionarProduto(Pedido pedido, Long produtoId, int quantidade) {
	    Pedido pedidoEncontrado = pedidoRepository.findById(pedido.getId())
	        .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

	    Produto produto = produtoRepository.findById(produtoId)
	        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

	    for (int i = 0; i < quantidade; i++) {
	        pedidoEncontrado.getProdutos().add(produto);
	    }

	    pedidoRepository.save(pedidoEncontrado);
	}
	
	public Pedido getPedido(Long pedidoId) {
	    return pedidoRepository.findById(pedidoId)
	            .orElse(null);
	}

	public void retirarProduto(Long pedidoId, Long produtoId, int quantidade) {

		Pedido pedido = pedidoRepository.findById(pedidoId)
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

	public BigDecimal fecharPedido(Long pedidoId, BigDecimal valorPagamento) {
	    Pedido pedido = pedidoRepository.findById(pedidoId)
	            .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
	    
	    BigDecimal precoTotal = calcularPrecoTotal(pedidoId);
	    
	    if (valorPagamento != null) {
	        if (valorPagamento.compareTo(precoTotal) >= 0) {
	            pedido.setFechado(true);
	            pedidoRepository.save(pedido);
	            
	            // Calcula o troco
	            BigDecimal troco = valorPagamento.subtract(precoTotal);
	            return troco;
	        } else {
	            throw new IllegalArgumentException("Valor de pagamento insuficiente.");
	        }
	    } else {
	        throw new IllegalArgumentException("O valor do pagamento não pode ser nulo.");
	    }
	}

	public BigDecimal calcularPrecoTotalPedido(Long pedidoId, List<Long> produtoIds, List<Integer> quantidades) {
	    Pedido pedido = pedidoRepository.findById(pedidoId)
	            .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

	    BigDecimal precoTotal = BigDecimal.ZERO;

	    for (int i = 0; i < produtoIds.size(); i++) {
	        Long produtoId = produtoIds.get(i);
	        Integer quantidade = quantidades.get(i);

	        Produto produto = produtoRepository.findById(produtoId)
	                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

	        BigDecimal precoProduto = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
	        precoTotal = precoTotal.add(precoProduto);
	    }

	    return precoTotal;
	}

}
