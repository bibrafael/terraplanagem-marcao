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
		if ( instance == null ) {
			instance = new AcoesUsuario();
		}
		return instance;
	}

	private Vector<String> colunas;

	private AcoesUsuario() {
		colunas = new Vector<String>();
		colunas.add( "Id" );
		colunas.add( "Usuário" );
		colunas.add( "Selecionar" );
	}

	public DefaultTableModel criar( String nome, String senha ) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setNome( nome );
		usuario.setSenha( senha );
		ServicosUsuario.getInstance().criar( usuario );
		return (createModel( ServicosUsuario.getInstance().ler( null ) ));
	}

	public DefaultTableModel ler( String nome ) {
		Usuario usuario = new Usuario();
		usuario.setNome( nome );
		return (createModel( ServicosUsuario.getInstance().ler( usuario ) ));
	}

	public DefaultTableModel lerPorId( Long id ) {
		return (createModel( ServicosUsuario.getInstance().lerPorId( id ) ));
	}

	public DefaultTableModel atualizar( Long id, String nome, String senha ) {
		Usuario usuario = new Usuario();
		usuario.setId( id );
		usuario.setNome( nome );
		usuario.setSenha( senha );
		ServicosUsuario.getInstance().atualizar( usuario );
		return (createModel( ServicosUsuario.getInstance().ler( null ) ));
	}

	public DefaultTableModel deletar( Long id ) {
		ServicosUsuario.getInstance().deletar( id );
		return (createModel( ServicosUsuario.getInstance().ler( null ) ));
	}

	public boolean verificarLogin( String nome, String senha ) {
		Usuario usuario = new Usuario();
		usuario.setNome( nome );
		usuario.setSenha( senha );
		boolean flag = ServicosUsuario.getInstance().verificarLogin( usuario );
		return flag;
	}

	private DefaultTableModel createModel( List<Usuario> usuarios ) {
		DefaultTableModel model = null;
		Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
		if ( usuarios != null ) {
			for ( int i = 0; i < usuarios.size(); i++ ) {
				Usuario aux = usuarios.get( i );
				if ( !aux.getNome().equalsIgnoreCase( "admin" ) ) {
					Vector<Object> linha = new Vector<Object>();
					linha.add( aux.getId().toString() );
					linha.add( aux.getNome() );
					linha.add( new Boolean( false ) );
					dados.add( linha );
				}
			}
		}
		model = new DefaultTableModel( dados, colunas ) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5564027001573700916L;
			
			public boolean isCellEditable(int row, int column) {
				if(column == 2) 
					return true;
				return false;
			};
			
			Class[] types = { Integer.class, String.class, Boolean.class };

			@Override
			public Class<?> getColumnClass( int columnIndex ) {
				return types[columnIndex];
			}

		};
		return (model);
	};
}
