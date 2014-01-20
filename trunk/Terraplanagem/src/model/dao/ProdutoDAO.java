package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Produto;
import model.services.ConnectionManager;

public class ProdutoDAO {

	private static ProdutoDAO instance =  null;

	public static ProdutoDAO getInstance() {
		if(instance == null) {
			instance = new ProdutoDAO();
		}
		return (instance);
	}

	private ProdutoDAO(){}

	public void criar(Produto produto) {
		PreparedStatement stm = null;
		Connection conn = null;
		try {
			String sql = "insert into produto (pro_descricao, pro_pcusto, pro_densidade, pro_pvista, pro_pprazo) values (?,?,?,?,?)";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, produto.getDescricao());
			stm.setDouble(2, produto.getpCusto());
			stm.setDouble(3, produto.getDensidade());
			stm.setDouble(4, produto.getpVista());
			stm.setDouble(5, produto.getpPrazo());
			stm.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}

	public List<Produto> ler(Produto produto) {
		List<Produto> produtos = new ArrayList<Produto>();
		if(produto != null) {
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			String sql = "select * from produto where pro_descricao ilike ? order by pro_descricao asc";
			try {
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, "%"+produto.getDescricao()+"%");
				rs = stm.executeQuery();
				while(rs.next()) {
					Produto aux = new Produto();
					aux.setId(rs.getLong("pro_id"));
					aux.setDescricao(rs.getString("pro_descricao"));
					aux.setpCusto(rs.getDouble("pro_pcusto"));
					aux.setDensidade(rs.getDouble("pro_densidade"));
					aux.setpVista(rs.getDouble("pro_pvista"));
					aux.setpPrazo(rs.getDouble("pro_pprazo"));
					produtos.add(aux);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	

		return produtos;
	}

	public Produto lerPorId(Long id) {
		Produto produto = null;
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from produto where pro_id = ?";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			rs = stm.executeQuery();
			if(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getLong("pro_id"));
				produto.setDescricao(rs.getString("pro_descricao"));
				produto.setpCusto(rs.getDouble("pro_pcusto"));
				produto.setDensidade(rs.getDouble("pro_densidade"));
				produto.setpVista(rs.getDouble("pro_pvista"));
				produto.setpPrazo(rs.getDouble("pro_pprazo"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}

		return produto;
	}

	public void atualizar(Produto produto) {
		Connection conn = null;
		PreparedStatement stm = null;
		if(produto.getDescricao() != null) {
			try {
				String sql = "update produto set pro_descricao = ?, pro_pcusto = ?, pro_densidade = ?, pro_pvista = ?, pro_pprazo = ? where pro_id = ?";
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, produto.getDescricao());
				stm.setDouble(2, produto.getpCusto());
				stm.setDouble(3, produto.getDensidade());
				stm.setDouble(4, produto.getpVista());
				stm.setDouble(5, produto.getpPrazo());
				stm.setLong(6, produto.getId());
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
			String sql = "delete from produto where pro_id = ?";
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
