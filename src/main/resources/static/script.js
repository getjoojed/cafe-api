// Função para adicionar um produto ao pedido
function adicionarProduto() {
			var produtoId = document.getElementById('produtoId').value;
			var quantidade = document.getElementById('quantidade').value;
			var pedidoId = document.getElementById('pedidoId').value;

			// Verifica se os campos foram preenchidos
			if (!produtoId || !quantidade) {
				showError('Por favor, preencha todos os campos.');
				return;
			}

			// Monta o objeto de requisição para adicionar o produto ao pedido
			var request = {
				pedidoId: pedidoId,
				produtoId: parseInt(produtoId),
				quantidade: parseInt(quantidade)
			};

			// Envia a requisição para adicionar o produto ao pedido
			fetch('/api/pedidos/' + pedidoId + '/adicionarProduto', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(request)
			})
				.then(function (response) {
					if (!response.ok) {
						throw new Error('Erro ao adicionar o produto ao pedido.');
					}
					return response.json();
				})
				.then(function (data) {
					showSuccess('Produto adicionado ao pedido com sucesso.');
				})
				.catch(function (error) {
					showError(error.message);
				});
		}

function redirectToAdicionarProdutos() {
	window.location.href = 'criar_produtos.html';
}

// Função para retirar um produto do pedido
function retirarProduto() {
	var pedidoId = document.getElementById('pedidoId').value;
	var produtoId = document.getElementById('produtoId').value;
	var quantidade = document.getElementById('quantidade').value;

	// Verifica se os campos foram preenchidos
	if (!pedidoId || !produtoId || !quantidade) {
		showError('Por favor, preencha todos os campos.');
		return;
	}

	// Monta o objeto de requisição
	var request = {
		pedidoId: parseInt(pedidoId),
		produtoId: parseInt(produtoId),
		quantidade: parseInt(quantidade)
	};

	// Envia a requisição para a API
	fetch('/api/pedidos/' + pedidoId + '/retirarProduto', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(request)
	})
		.then(function(response) {
			if (!response.ok) {
				throw new Error('Erro ao retirar o produto do pedido.');
			}
			return response.json();
		})
		.then(function(data) {
			showSuccess('Produto retirado do pedido com sucesso.');
		})
		.catch(function(error) {
			showError(error.message);
		});
}

// Função para calcular o preço total do pedido
function calcularPrecoTotal() {
	var pedidoId = document.getElementById('pedidoId').value;

	// Verifica se o campo foi preenchido
	if (!pedidoId) {
		showError('Por favor, preencha o campo ID do Pedido.');
		return;
	}

	// Envia a requisição para a API
	fetch('/api/pedidos/' + pedidoId + '/calcularPrecoTotal', {
		method: 'GET'
	})
		.then(function(response) {
			if (!response.ok) {
				throw new Error('Erro ao calcular o preço total do pedido.');
			}
			return response.json();
		})
		.then(function(data) {
			showSuccess('Preço total do pedido: R$ ' + data.precoTotal.toFixed(2));
		})
		.catch(function(error) {
			showError(error.message);
		});
}

// Função para fechar o pedido
function fecharPedido() {
	var pedidoId = document.getElementById('pedidoId').value;
	var valorPagamento = document.getElementById('valorPagamento').value;

	// Verifica se os campos foram preenchidos
	if (!pedidoId || !valorPagamento) {
		showError('Por favor, preencha todos os campos.');
		return;
	}

	// Monta o objeto de requisição
	var request = {
		pedidoId: parseInt(pedidoId),
		valorPagamento: parseFloat(valorPagamento)
	};

	// Envia a requisição para a API
	fetch('/api/pedidos/' + pedidoId + '/fecharPedido', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(request)
	})
		.then(function(response) {
			if (!response.ok) {
				throw new Error('Erro ao fechar o pedido.');
			}
			return response.json();
		})
		.then(function(data) {
			showSuccess('Pedido fechado com sucesso. Troco: R$ ' + data.troco.toFixed(2));
		})
		.catch(function(error) {
			showError(error.message);
		});
}

// Função para exibir uma mensagem de sucesso
function showSuccess(message) {
	var resultDiv = document.getElementById('result');
	resultDiv.style.color = 'green';
	resultDiv.textContent = message;
}

// Função para exibir uma mensagem de erro
function showError(message) {
	var resultDiv = document.getElementById('result');
	resultDiv.style.color = 'red';
	resultDiv.textContent = message;
}