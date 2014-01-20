package model.services;

import java.sql.SQLException;
import java.util.List;

import model.dao.PedidoDAO;
import model.pojo.Pedido;

public class ServicosPedido {

	private static ServicosPedido instance = null;

	public static ServicosPedido getInstance() {
		if(instance == null) {
			instance = new ServicosPedido();
		}
		return instance;
	}

	private ServicosPedido() {}

	public void criar(Pedido pedido) {
		if(pedido != null) {
			PedidoDAO.getInstance().criar(pedido);
		}
	}

	public List<Pedido> ler(Pedido pedido) {
		List<Pedido> pedidos = PedidoDAO.getInstance().ler(pedido);
		return pedidos;
	}
	
	public void deletar(Long pedidoId) throws SQLException {
		PedidoDAO.getInstance().deletar(pedidoId);
	}
	
	public void atualizar(Pedido pedido) {
		PedidoDAO.getInstance().atualizar(pedido);
	}
	
	public Long getIdPedido() {
		return PedidoDAO.getInstance().getIdPedido();
	}
	
	public Pedido lerPorId(Long pedidoId) {
		return PedidoDAO.getInstance().lerPorId(pedidoId);
	}

}
