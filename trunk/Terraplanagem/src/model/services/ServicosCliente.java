package model.services;

import java.util.List;

import model.dao.ClienteDAO;
import model.pojo.Cliente;

public class ServicosCliente {

	private static ServicosCliente instance = null;
	
	public static ServicosCliente getInstance() {
		if(instance == null) {
			instance = new ServicosCliente();
		}
		return instance;
	}
	
	private ServicosCliente() {}
	
	public void criar(Cliente cliente) {
		if(cliente != null) {
			ClienteDAO.getInstance().criar(cliente);
		}
	}
	
	public List<Cliente> ler(Cliente cliente) {
		if(cliente == null) {
			cliente = new Cliente();
			cliente.setNome("");
		}
		List<Cliente> clientes = ClienteDAO.getInstance().ler(cliente);
		return clientes;
	}
	
	public Cliente lerPorId(Long id) {
		Cliente cliente = null;
		if(id != null) {
			cliente = ClienteDAO.getInstance().lerPorId(id);
		}
		return cliente;
	}
	
	public void atualizar(Cliente cliente) {
		if(cliente != null) {
			ClienteDAO.getInstance().atualizar(cliente);
		}
	}
	
	public void deletar(Long id) {
		if(id != null) {
			ClienteDAO.getInstance().deletar(id);
		}
	}
	
}
