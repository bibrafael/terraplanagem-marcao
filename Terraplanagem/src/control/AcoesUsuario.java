package control;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.pojo.Usuario;
import model.services.ServicosUsuario;

public class AcoesUsuario {
	
	private static AcoesUsuario instance = null;
	
	public static AcoesUsuario getInstance() {
		if(instance == null) {
			instance = new AcoesUsuario();
		}
		return instance;
	}
	
	private Vector<String> colunas;
	
	private AcoesUsuario() {
		colunas = new Vector<String>();
		colunas.add("Id");
		colunas.add("Usuário");
	}
	
	public DefaultTableModel criar(String nome, String senha) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(senha);
		ServicosUsuario.getInstance().criar(usuario);
		return (createModel(ServicosUsuario.getInstance().ler(null)));
	}
	
	public DefaultTableModel ler(String nome) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		return (createModel(ServicosUsuario.getInstance().ler(usuario)));
	}
	
	public DefaultTableModel lerPorId(Long id) {
		return (createModel(ServicosUsuario.getInstance().lerPorId(id)));
	}
	
	public DefaultTableModel atualizar(Long id, String nome, String senha) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		ServicosUsuario.getInstance().atualizar(usuario);
		return (createModel(ServicosUsuario.getInstance().ler(null)));
	}
	
	public DefaultTableModel deletar(Long id) {
		ServicosUsuario.getInstance().deletar(id);
		return (createModel(ServicosUsuario.getInstance().ler(null)));
	}
	
	public boolean verificarLogin(String nome, String senha) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(senha);
		boolean flag = ServicosUsuario.getInstance().verificarLogin(usuario);
		return flag;
	}
	
	private DefaultTableModel createModel(List<Usuario> usuarios) {
		DefaultTableModel model = null;
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		if(usuarios != null){
			for(int i=0; i < usuarios.size();i++){
				Usuario aux = usuarios.get(i);
				Vector<String> linha = new Vector<String>();
				linha.add(aux.getId().toString());
				linha.add(aux.getNome());
				dados.add(linha);
			}
		}
		model = new DefaultTableModel(dados,colunas);
		return(model);
	};
}
