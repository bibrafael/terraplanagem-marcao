package model.services;

import java.sql.Date;
import java.util.List;

import model.dao.RelatorioDAO;
import model.pojo.Relatorio;


public class ServicosRelatorio {

	private static ServicosRelatorio instance = null;
	
	public static ServicosRelatorio getInstance() {
		if(instance == null) {
			instance = new ServicosRelatorio();
		}
		return instance;
	}
	
	private ServicosRelatorio() {}
	
	public List<Relatorio> criar (Long idCliente, Date dataInicio, Date dataTermino, boolean tdPedidos, boolean tipo) {
		return (RelatorioDAO.getInstance().criar(idCliente, dataInicio, dataTermino, tdPedidos, tipo));
	}
	
}
