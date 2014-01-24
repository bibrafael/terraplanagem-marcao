package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.ezware.dialog.task.CommandLink;
import com.ezware.dialog.task.TaskDialogs;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;

import control.AcoesUsuario;

public class DialogUsuario extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7427995992306608646L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JTable tabUsuario;
	private JLabel lblSenha;
	private JPasswordField pfSenha;
	private JScrollPane scpUsuario;
	private JButton btnCadastrarUsuario;
	private JLabel lblUsuario;
	private JButton btnDeletar;
	private JButton btnAlterar;

	/**
	 * Create the frame.
	 */
	public DialogUsuario() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Terraplanagem São Marcos - Gerenciamento de Usuários" );
		setSize( 500, 500 );
		setContentPane( getContentPane() );

		initialize();

	}

	private void initialize() {
		contentPane.add( getLblUsuario() );
		contentPane.add( getLblSenha() );
		contentPane.add( getTfUsuario() );
		contentPane.add( getBtnCadastrarUsuario() );
		contentPane.add( getPfSenha() );
		contentPane.add( getScpUsuario() );
		contentPane.add( getBtnDeletar() );
		contentPane.add( getBtnAlterar() );

		TableRowFilterSupport.forTable( getTabUsuario() ).searchable( true ).apply();
		getTabUsuario().setModel( AcoesUsuario.getInstance().ler( "" ) );
		getTabUsuario().getColumnModel().removeColumn( tabUsuario.getColumnModel().getColumn( 0 ) );
		getTabUsuario().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				int linha = getTabUsuario().getSelectedRow();
				for ( int i = 0; i < getTabUsuario().getRowCount(); i++ ) {
					if ( getTabUsuario().getModel().getValueAt( i, 2 ).equals( new Boolean( true ) ) && linha != i )
						getTabUsuario().getModel().setValueAt( new Boolean( false ), i, 2 );
				}
			}
		} );
	}

	public JPanel getContentPane() {
		if ( contentPane == null ) {
			contentPane = new JPanel();
			contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
			contentPane.setLayout( null );
		}
		return contentPane;
	}

	private JLabel getLblSenha() {
		if ( lblSenha == null ) {
			lblSenha = new JLabel( "Senha" );
			lblSenha.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
			lblSenha.setBounds( 30, 60, 60, 20 );
		}
		return lblSenha;
	}

	private JScrollPane getScpUsuario() {
		if ( scpUsuario == null ) {
			scpUsuario = new JScrollPane();
			scpUsuario.setBounds( 30, 91, 422, 359 );
			scpUsuario.setViewportView( getTabUsuario() );
		}
		return scpUsuario;
	}

	private JTable getTabUsuario() {
		if ( tabUsuario == null ) {
			tabUsuario = new JTable();
		}
		return tabUsuario;
	}

	private JButton getBtnCadastrarUsuario() {
		if ( btnCadastrarUsuario == null ) {
			btnCadastrarUsuario = new JButton( "Cadastrar" );
			btnCadastrarUsuario.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					String nome = tfUsuario.getText();
					String senha = pfSenha.getText();

					if ( nome.isEmpty() || senha.isEmpty() ) {
						JOptionPane.showMessageDialog( null, "Todos os campos devem ser preenchidos!", "Alerta", JOptionPane.WARNING_MESSAGE );
					} else {
						try {
							senha = criptografarSenha( senha );
							getTabUsuario().setModel( AcoesUsuario.getInstance().criar( nome, senha ) );
							getTabUsuario().getColumnModel().removeColumn( getTabUsuario().getColumnModel().getColumn( 0 ) );
							// setarTamanhoColunas();
							tfUsuario.setText( null );
							pfSenha.setText( null );
						} catch ( SQLException e1 ) {
							JOptionPane.showMessageDialog( null, "Usuário já existente!", "Erro", JOptionPane.ERROR_MESSAGE );
						} catch ( NoSuchAlgorithmException e2 ) {
						}
					}
				}
			} );
			btnCadastrarUsuario.setBounds( 250, 30, 95, 23 );
		}
		return btnCadastrarUsuario;
	}

	private String criptografarSenha( String senha ) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		BigInteger hash = new BigInteger( 1, md.digest( senha.getBytes() ) );
		String s = hash.toString( 16 );
		if ( s.length() % 2 != 0 )
			s = "0" + s;
		return s;
	}

	private JPasswordField getPfSenha() {
		if ( pfSenha == null ) {
			pfSenha = new JPasswordField();
			pfSenha.setBounds( 90, 60, 150, 20 );
			pfSenha.setNextFocusableComponent( getBtnCadastrarUsuario() );
		}
		return pfSenha;
	}

	private JTextField getTfUsuario() {
		if ( tfUsuario == null ) {
			tfUsuario = new JTextField();
			tfUsuario.setBounds( 90, 30, 150, 20 );
			tfUsuario.setNextFocusableComponent( getPfSenha() );
		}
		return tfUsuario;
	}

	private JLabel getLblUsuario() {
		if ( lblUsuario == null ) {
			lblUsuario = new JLabel( "Usuário" );
			lblUsuario.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
			lblUsuario.setBounds( 30, 30, 60, 20 );
		}
		return lblUsuario;
	}

	private JButton getBtnDeletar() {
		if ( btnDeletar == null ) {
			btnDeletar = new JButton( "Deletar" );
			btnDeletar.setBounds( 250, 60, 95, 23 );
			btnDeletar.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed( ActionEvent e ) {
					for ( int i = 0; i < getTabUsuario().getRowCount(); i++ ) {
						if ( (Boolean) getTabUsuario().getModel().getValueAt( i, 2 ) ) {
							String usuario = (String) getTabUsuario().getModel().getValueAt( i, 1 );
							int choice = TaskDialogs.choice( null, "O que você gostaria de fazer com o usuário " + usuario + "?", "", 1, new CommandLink(
									"Deletar o usuário", "Deletar o usuário e o mesmo não ter mais acesso ao sistema." ), new CommandLink(
									"Não deletar o usuário", "Não deletar o usuário e manter o acesso do mesmo ao sistema" ) );

							if ( choice == 0 ) {
								getTabUsuario().setModel(
										AcoesUsuario.getInstance().deletar( (Long) new Long( getTabUsuario().getModel().getValueAt( i, 0 ).toString() ) ) );
								getTabUsuario().getColumnModel().removeColumn( getTabUsuario().getColumnModel().getColumn( 0 ) );
								// setarTamanhoColunas();
							}

						}
					}
				}
			} );
		}
		return btnDeletar;
	}

	private JButton getBtnAlterar() {
		if ( btnAlterar == null ) {
			btnAlterar = new JButton( "Alterar" );
			btnAlterar.setBounds( 357, 30, 95, 23 );
			btnAlterar.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed( ActionEvent e ) {
					Long id = null;
					String usuario = "";
					for ( int i = 0; i < getTabUsuario().getRowCount(); i++ ) {
						if ( getTabUsuario().getModel().getValueAt( i, 2 ).equals( new Boolean( true ) ) ) {
							id = new Long( getTabUsuario().getModel().getValueAt( i, 0 ).toString() );
							usuario = getTabUsuario().getModel().getValueAt( i, 1 ).toString();
							break;
						}
					}
					if ( id != null ) {
						JDialog dialog = new AlterarSenhaDialog( id, usuario );
						dialog.pack();
						dialog.setVisible( true );
					} else {
						TaskDialogs.inform( DialogUsuario.this, "Selecione um usuário!", "Selecione um usuário para alterá-lo." );
					}
				}
			} );
		}
		return btnAlterar;
	}
}
