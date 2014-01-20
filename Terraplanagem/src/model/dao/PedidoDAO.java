package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Pedido;
import model.services.ConnectionManager;

public class PedidoDAO {

	private static PedidoDAO instance = null;

	public static PedidoDAO getInstance() {
		if(instance == null) {
			instance = new PedidoDAO();
		}
		return instance;
	}

	private PedidoDAO(){}

	public void criar(Pedido pedido) {
		PreparedStatement stm = null;
		Connection conn = null;
		try {
			String sql = "insert into pedido(ped_cli_id, ped_data, ped_local_entrega, ped_tipo) values(?,?,?,?)";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, pedido.getClienteId());
			stm.setDate(2, pedido.getData());
			stm.setString(3, pedido.getLocalEntrega());
			stm.setBoolean(4, pedido.isTipo());
			stm.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				stm.close();
			}catch (Exception e) {}
		}
	}

	public List<Pedido> ler(Pedido pedido) {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		if(pedido != null) {
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			String sql = "select * from pedido where ped_cli_id = ? and ped_tipo = ? order by ped_data asc";
			try {
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setLong(1, pedido.getClienteId());
				stm.setBoolean(2, pedido.isTipo());
				rs = stm.executeQuery();
				while(rs.next()) {
					Pedido aux = new Pedido();
					aux.setClienteId(rs.getLong("ped_cli_id"));
					aux.setData(rs.getDate("ped_data"));
					aux.setId(rs.getLong("ped_id"));
					aux.setvTotal(rs.getDouble("ped_valor_total"));
					aux.setPagEfetuado(rs.getBoolean("ped_pag_efetuado"));
					aux.setLocalEntrega(rs.getString("ped_local_entrega"));
					pedidos.add(aux);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{
					stm.close();
				}catch (Exception e) {}
			}
		}	
		return pedidos;
	}
	
	public void deletar(Long pedidoId) throws SQLException {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "delete from pedido where ped_id = ?";
		try{
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, pedidoId);
			stm.execute();
		}catch (Exception e) {
			throw new SQLException();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}
	
	public void atualizar(Pedido pedido) {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "update pedido set ped_pag_efetuado = ?, ped_valor_total = ? where ped_id = ?";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setBoolean(1, pedido.isPagEfetuado());
			stm.setDouble(2, pedido.getvTotal());
			stm.setLong(3, pedido.getId());
			stm.execute();
		}catch (Exception e) {
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}

	public Pedido lerPorId(Long pedidoId) {
		Pedido pedido = null;
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from pedido where ped_id = ?";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, pedidoId);
			rs = stm.executeQuery();
			if(rs.next()) {
				pedido = new Pedido();
				pedido.setId(rs.getLong("ped_id"));
				pedido.setClienteId(rs.getLong("ped_cli_id"));
				pedido.setData(rs.getDate("ped_data"));
				pedido.setvTotal(rs.getDouble("ped_valor_total"));
				pedido.setLocalEntrega(rs.getString("ped_local_entrega"));
				pedido.setPagEfetuado(rs.getBoolean("ped_pag_efetuado"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}

		return pedido;
	}
	
	public Long getIdPedido() {
		Long pedidoId = null;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select max(ped_id) as maximo from pedido";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				pedidoId = rs.getLong("maximo");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
		return pedidoId;
	}


	//	
	//	public void atualizar(Pedido pedido) {
	//		Connection conn = null;
	//		PreparedStatement stm = null;
	//		if(pedido.get() != null) {
	//			try {
	//				String sql = "update cliente set cli_nome = ?, cli_endereco = ?, cli_cidade = ?, cli_cpf_cnpj = ?, cli_telefone = ? where cli_id = ?";
	//				conn = ConnectionManager.getInstance().getConnection();
	//				stm = conn.prepareStatement(sql);
	//				stm.setString(1, pedido.getNome());
	//				stm.setString(2, pedido.getEndereco());
	//				stm.setString(3, pedido.getCidade());
	//				stm.setString(4, pedido.getCpf_cnpj());
	//				stm.setString(5, pedido.getTelefone());
	//				stm.setLong(6, pedido.getId());
	//				stm.execute();
	//			}catch (Exception e) {
	//				e.printStackTrace();
	//			}finally {
	//				try {
	//					stm.close();
	//				}catch (Exception e) {}
	//			}
	//		}
}
