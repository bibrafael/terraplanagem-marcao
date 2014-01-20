package model.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.UsuarioDAO;
import model.pojo.Usuario;

public class ServicosUsuario {
	
	private static ServicosUsuario instance = null;
	
	public static ServicosUsuario getInstance() {
		if(instance == null) {
			instance = new ServicosUsuario();
		}
		return instance;
	}
	
	private ServicosUsuario() {}
	
	public void criar(Usuario usuario) throws SQLException {
		if(usuario.getNome() != null) {
			UsuarioDAO.getInsntance().criar(usuario);
		}
	}
	
	public List<Usuario> ler(Usuario usuario) {
		if(usuario == null) {
			usuario = new Usuario();
			usuario.setNome("");
		}
		List<Usuario> usuarios = UsuarioDAO.getInsntance().ler(usuario);
		return usuarios;
	}
	
	public List<Usuario> lerPorId(Long id) {
		Usuario usuario = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if(id != null) {
			usuario = UsuarioDAO.getInsntance().lerPorId(id);
		}
		usuarios.add(usuario);
		return usuarios;
	}
	
	public void atualizar(Usuario usuario) {
		if(usuario.getNome() != null) {
			UsuarioDAO.getInsntance().atualizar(usuario);
		}
	}
	
	public void deletar(Long id) {
		if(id != null) {
			UsuarioDAO.getInsntance().deletar(id);
		}
	}
	
	public boolean verificarLogin(Usuario usuario) {
		boolean flag = false;
		List<Usuario> usuarios = UsuarioDAO.getInsntance().ler(usuario);
		for (Usuario usuario2 : usuarios) {
			if(usuario.getNome().equals(usuario2.getNome())) {
				if(usuario.getSenha().equals(usuario2.getSenha())) {
					flag = true;
				}
			}
		}
		return flag;
	}

}
