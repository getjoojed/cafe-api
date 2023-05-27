package com.cafe.request;

import java.math.BigDecimal;

public class FecharPedidoRequest {
    private Long pedidoId;
    private BigDecimal valorPagamento;
    
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public BigDecimal getValorPagamento() {
		return valorPagamento;
	}
	public void setValorPagamento(BigDecimal valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

    
    
}
