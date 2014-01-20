package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Transporte;
import model.services.ConnectionManager;

public class TransporteDAO {
	
	private static TransporteDAO instance = null;
	
	public static TransporteDAO getInstance() {
		if(instance == null) {
			instance = new TransporteDAO();
		}
		return instance;
	}
	
	private TransporteDAO() {}
	
	public void criar(Transporte transporte) {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "insert into transporte (tra_descricao, tra_placa, tra_hora_maquina, tra_vhora, tra_vtotal, tra_pedido_id_fk) values (?,?,?,?,?,?)";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, transporte.getDescricao());
			stm.setString(2, transporte.getPlaca());
			stm.setDouble(3, transporte.getqHora());
			stm.setDouble(4, transporte.getvHora());
			stm.setDouble(5, transporte.getvTotal());
			stm.setLong(6, transporte.getPedidoId());
			stm.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}
	
	public List<Transporte> lerPorPedido(Long pedidoId) {
		List<Transporte> transportes = new ArrayList<Transporte>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String sql = "select * from transporte where tra_pedido_id_fk = ?";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, pedidoId);
			rs = stm.executeQuery();
			while(rs.next()) {
				Transporte aux = new Transporte();
				aux.setId(rs.getLong("tra_id"));
				aux.setDescricao(rs.getString("tra_descricao"));
				aux.setPedidoId(rs.getLong("tra_pedido_id_fk"));
				aux.setPlaca(rs.getString("tra_placa"));
				aux.setqHora(rs.getDouble("tra_hora_maquina"));
				aux.setvHora(rs.getDouble("tra_vhora"));
				aux.setvTotal(rs.getDouble("tra_vtotal"));
				transportes.add(aux);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
		return transportes;
	}
	
	public void deletar(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stm = null;
		String sql = "delete from transporte where tra_id = ?";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			stm.execute();
		}catch (Exception e) {
			throw new SQLException();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}
}
