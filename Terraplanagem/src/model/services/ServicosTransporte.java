package model.services;

import java.sql.SQLException;
import java.util.List;

import model.dao.TransporteDAO;
import model.pojo.Transporte;

public class ServicosTransporte {
	
	private static ServicosTransporte instance = null;
	
	public static ServicosTransporte getInstance() {
		if(instance == null) {
			instance = new ServicosTransporte();
		}
		return instance;
	}
	
	private ServicosTransporte() {}
	
	public void criar(Transporte transporte) {
		TransporteDAO.getInstance().criar(transporte);
	}
	
	public void deletar(Long id) throws SQLException {
		TransporteDAO.getInstance().deletar(id);
	}
	
	public List<Transporte> lerPorPedido(Long pedidoId) {
		return (TransporteDAO.getInstance().lerPorPedido(pedidoId));
	}
}
