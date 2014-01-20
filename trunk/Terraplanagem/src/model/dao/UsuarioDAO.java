package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Usuario;
import model.services.ConnectionManager;

public class UsuarioDAO {

	private static UsuarioDAO instance = null;

	public static UsuarioDAO getInsntance() {
		if (instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}

	private UsuarioDAO() {}

	public void criar(Usuario usuario) throws SQLException {
		PreparedStatement stm = null;
		Connection conn = null;
		try {
			String sql = "insert into usuario(usu_usuario, usu_senha) values (?,?)";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, usuario.getNome());
			stm.setString(2, usuario.getSenha());
			stm.execute();
		}catch (Exception e) {
			throw new SQLException();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}
	}

	public List<Usuario> ler(Usuario usuario) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if(usuario != null) {
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			String sql = "select * from usuario where usu_usuario ilike ? order by usu_usuario asc";
			try {
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, "%"+usuario.getNome()+"%");
				rs = stm.executeQuery();
				while(rs.next()) {
					Usuario aux = new Usuario();
					aux.setId(rs.getLong("usu_id"));
					aux.setNome(rs.getString("usu_usuario"));
					aux.setSenha(rs.getString("usu_senha"));
					usuarios.add(aux);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	

		return usuarios;
	}
	
	public Usuario lerPorId(Long id) {
		Usuario usuario = null;
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from usuario where usu_id = ?";
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, id);
			rs = stm.executeQuery();
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong("usu_id"));
				usuario.setNome(rs.getString("usu_usuario"));
				usuario.setSenha(rs.getString("usu_senha"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			}catch (Exception e) {}
		}

		return usuario;
	}

	public void atualizar(Usuario usuario) {
		Connection conn = null;
		PreparedStatement stm = null;
		if(usuario.getNome() != null) {
			try {
				String sql = "update usuario set usu_usuario = ?, usu_senha = ? where usu_id = ?";
				conn = ConnectionManager.getInstance().getConnection();
				stm = conn.prepareStatement(sql);
				stm.setString(1, usuario.getNome());
				stm.setString(2, usuario.getSenha());
				stm.setLong(3, usuario.getId());
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
			String sql = "delete from usuario where usu_id = ?";
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