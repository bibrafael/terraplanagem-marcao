package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Cliente;
import model.services.ConnectionManager;

public class ClienteDAO {

	private static ClienteDAO instance =  null;

	public static ClienteDAO getInstance() {
		if(instance == null) {
			instance = new ClienteDAO();
		}
		return (instance);
	}

	private ClienteDAO(){}

	public void criar (Cliente cliente) {
		PreparedStatement stm = null;
		Connection conn = null;
		try{
			String sql = "insert into cliente(cli_nome, cli_endereco, cli_cpf_cnpj, cli_telefone, cli_cidade) values (?,?,?,?,?)";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, cliente.getNome());
			stm.setString(2, cliente.getEndereco());
			stm.setString(3, cliente.getCpf_cnpj());
			stm.setString(4, cliente.getTelefone());
			stm.setString(5, cliente.getCidade());
			stm.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				stm.close();
			}catch (Exception e) {}
		}
	}

	public List<Cliente> ler(Cliente cliente) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		if(cliente != null) {
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			String sql = "select * from cliente where cli_nome ilike ? order by cli_nome asc";
			try {
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, "%"+cliente.getNome()+"%");
				rs = stm.executeQuery();
				while(rs.next()) {
					Cliente aux = new Cliente();
					aux.setId(rs.getLong("cli_id"));
					aux.setNome(rs.getString("cli_nome"));
					aux.setEndereco(rs.getString("cli_endereco"));
					aux.setCidade(rs.getString("cli_cidade"));
					aux.setCpf_cnpj(rs.getString("cli_cpf_cnpj"));
					aux.setTelefone(rs.getString("cli_telefone"));
					clientes.add(aux);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	

		return clientes;
	}

	public Cliente lerPorId(Long id) {
		Cliente cliente = null;
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from cliente where cli_id = ?";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			rs = stm.executeQuery();
			if(rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getLong("cli_id"));
				cliente.setNome(rs.getString("cli_nome"));
				cliente.setEndereco(rs.getString("cli_endereco"));
				cliente.setCidade(rs.getString("cli_cidade"));
				cliente.setCpf_cnpj(rs.getString("cli_cpf_cnpj"));
				cliente.setTelefone(rs.getString("cli_telefone"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}

		return cliente;
	}

	public void atualizar(Cliente cliente) {
		Connection conn = null;
		PreparedStatement stm = null;
		if(cliente.getNome() != null) {
			try {
				String sql = "update cliente set cli_nome = ?, cli_endereco = ?, cli_cidade = ?, cli_cpf_cnpj = ?, cli_telefone = ? where cli_id = ?";
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, cliente.getNome());
				stm.setString(2, cliente.getEndereco());
				stm.setString(3, cliente.getCidade());
				stm.setString(4, cliente.getCpf_cnpj());
				stm.setString(5, cliente.getTelefone());
				stm.setLong(6, cliente.getId());
				stm.execute();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					stm.close();
				}catch (Exception e) {}
			}
		}
	}

	public void deletar(Long id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			String sql = "delete from cliente where cli_id = ?";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			stm.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}
}
