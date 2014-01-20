package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.pojo.ItemPedido;
import model.services.ConnectionManager;

public class ItemPedidoDAO {

	private static ItemPedidoDAO instance = null;
	
	public static ItemPedidoDAO getInstance() {
		if(instance == null) {
			instance = new ItemPedidoDAO();
		}
		return instance;
	}
	
	public void criar(ItemPedido itemPedido) {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "insert into item_pedido (ite_ped_produto_id_fk, ite_ped_pedido_id_fk, ite_ped_punitario, ite_ped_ptotal, ite_ped_quantidade, ite_ped_pesocaminhao, ite_ped_densidade) values (?,?,?,?,?,?,?)";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, itemPedido.getProdutoId());
			stm.setLong(2, itemPedido.getPedidoId());
			stm.setDouble(3, itemPedido.getpUnitario());
			stm.setDouble(4, itemPedido.getpTotal());
			stm.setDouble(5, itemPedido.getQuantidade());
			stm.setDouble(6, itemPedido.getPesoCaminhao());
			stm.setDouble(7, itemPedido.getDensidade());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (Exception e2) {}
		}
	}
	
	public List<ItemPedido> lerPorPedido(Long pedidoId) {
		List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String sql = "select * from item_pedido where ite_ped_pedido_id_fk = ?";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, pedidoId);
			rs = stm.executeQuery();
			while (rs.next()) {
				ItemPedido aux = new ItemPedido();
				aux.setId(rs.getLong("ite_ped_id"));
				aux.setPedidoId(rs.getLong("ite_ped_pedido_id_fk"));
				aux.setProdutoId(rs.getLong("ite_ped_produto_id_fk"));
				aux.setPesoCaminhao(rs.getDouble("ite_ped_pesocaminhao"));
				aux.setpTotal(rs.getDouble("ite_ped_ptotal"));
				aux.setQuantidade(rs.getDouble("ite_ped_quantidade"));
				aux.setpUnitario(rs.getDouble("ite_ped_punitario"));
				aux.setDensidade(rs.getDouble("ite_ped_densidade"));
				itemPedidos.add(aux);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (Exception e2) {}
		}
		return itemPedidos;
	}
	
	public void removerProduto(Long id) {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "delete from item_pedido where ite_ped_id = ?";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			stm.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (Exception e2) {}
		}
	}
	
}
