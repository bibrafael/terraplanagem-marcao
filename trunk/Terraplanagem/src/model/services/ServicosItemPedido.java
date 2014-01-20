package model.services;

import java.util.List;

import model.dao.ItemPedidoDAO;
import model.pojo.ItemPedido;

public class ServicosItemPedido {

	private static ServicosItemPedido instance = null;
	
	public static ServicosItemPedido getInstance() {
		if(instance == null) {
			instance = new ServicosItemPedido();
		}
		return instance;
	}
	
	private ServicosItemPedido() {}
	
	public void criar(ItemPedido itemPedido) {
		if(itemPedido != null) {
			ItemPedidoDAO.getInstance().criar(itemPedido);
		}
	}
	
	public List<ItemPedido> lerPorPedido(Long pedidoId) {
		return (ItemPedidoDAO.getInstance().lerPorPedido(pedidoId));
	}
	
	
	public void removerProduto(Long id) {
		ItemPedidoDAO.getInstance().removerProduto(id);
	}
	
}
