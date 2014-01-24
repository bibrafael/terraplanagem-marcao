package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.ezware.dialog.task.CommandLink;
import com.ezware.dialog.task.TaskDialogs;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;

import control.AcoesUsuario;

public class UsuarioDialog extends JDialog {

	private static final long serialVersionUID = 4628715204679911739L;

	private JPanel panelToolBar;
	private Long id;

	private JPanel panelItens;
	private JLabel lbNome;
	private JLabel lbSenha;
	private JTextField tfNome;
	private JPasswordField tfSenha;

	private JPanel panelButtons;
	private JButton btCriar;
	private JButton btAlterar;
	private JButton btDeletar;

	private JScrollPane scrollPanel;
	private JTable table;
	private DefaultTableModel tableModel;

	public UsuarioDialog() {
		this.setTitle( "Terraplanagem São Marcos - Cadastro de Usuários" );

		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets( 5, 5, 5, 5 );

		panelToolBar = new JPanel();
		panelToolBar.setLayout( new BorderLayout() );

		panelItens = new JPanel();
		panelItens.setLayout( new GridBagLayout() );

		lbNome = new JLabel( "Usuário:" );
		cons.gridy = 0;
		cons.gridx = 0;
		cons.anchor = GridBagConstraints.LINE_START;
		panelItens.add( lbNome, cons );

		tfNome = new JTextField( 20 );
		cons.gridy = 0;
		cons.gridx = 1;
		cons.gridwidth = 2;
		panelItens.add( tfNome, cons );

		lbSenha = new JLabel( "Senha:" );
		cons.gridy = 1;
		cons.gridx = 0;
		panelItens.add( lbSenha, cons );

		tfSenha = new JPasswordField( 20 );
		cons.gridy = 1;
		cons.gridx = 1;
		cons.gridwidth = 2;
		panelItens.add( tfSenha, cons );

		btCriar = new JButton( "Inserir" );
		cons.gridy = 2;
		cons.gridx = 1;
		cons.gridwidth = 1;
		btCriar.addActionListener( new criarUsuarioListener() );
		panelItens.add( btCriar, cons );

		panelToolBar.add( panelItens, BorderLayout.PAGE_START );

		panelButtons = new JPanel( new FlowLayout() );

		btAlterar = new JButton( "Alterar" );
		btAlterar.addActionListener( new alterarUsuarioListener() );
		panelButtons.add( btAlterar );

		btDeletar = new JButton( "Deletar" );
		btDeletar.addActionListener( new deletarUsuarioListener() );
		panelButtons.add( btDeletar );

		tableModel = AcoesUsuario.getInstance().ler( "" );
		table = new JTable( tableModel ) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable( int row, int col ) {
				if ( col != 1 )
					return false;
				return true;
			}
		};
		TableRowFilterSupport.forTable( table ).searchable( true ).apply();
		table.addMouseListener( new selecionarLinhaTabela() );
		table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
		setarTamanhoColunas();
		scrollPanel = new JScrollPane( table );

		this.setLayout( new BorderLayout() );
		this.add( panelToolBar, BorderLayout.PAGE_START );
		this.add( scrollPanel, BorderLayout.CENTER );
		this.add( panelButtons, BorderLayout.PAGE_END );
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );

	}

	class CenterRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 4433840270638649209L;

		public CenterRenderer() {
			setHorizontalAlignment( CENTER );
		}
	}

	public void setarTamanhoColunas() {
		TableCellRenderer centerRenderer = new CenterRenderer();
		TableColumn column = table.getColumnModel().getColumn( 0 );
		column.setCellRenderer( centerRenderer );
	}

	public String criptografarSenha( String senha ) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		BigInteger hash = new BigInteger( 1, md.digest( senha.getBytes() ) );
		String s = hash.toString( 16 );
		if ( s.length() % 2 != 0 )
			s = "0" + s;
		return s;
	}

	private class criarUsuarioListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			String nome = tfNome.getText();
			String senha = tfSenha.getText();

			if ( nome.isEmpty() || senha.isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Todos os campos devem ser preenchidos!", "Alerta", JOptionPane.WARNING_MESSAGE );
			} else {
				try {
					senha = criptografarSenha( senha );
					table.setModel( AcoesUsuario.getInstance().criar( nome, senha ) );
					table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
					setarTamanhoColunas();
					tfNome.setText( null );
					tfSenha.setText( null );
					id = null;
				} catch ( SQLException e1 ) {
					JOptionPane.showMessageDialog( null, "Usuário já existente!", "Erro", JOptionPane.ERROR_MESSAGE );
				} catch ( NoSuchAlgorithmException e2 ) {
				}
			}
		}
	}

	// private class lerUsuarioListener implements ActionListener {
	//
	// @Override
	// public void actionPerformed( ActionEvent e ) {
	// if ( tfNome.getText().equals( null ) ) {
	// JOptionPane.showMessageDialog( null, "Usuário não pode ser nulo!" );
	// } else {
	// table.setModel( AcoesUsuario.getInstance().ler( tfNome.getText() ) );
	// table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0
	// ) );
	// setarTamanhoColunas();
	// tfSenha.setText( null );
	// id = null;
	// }
	// }
	// }

	private class alterarUsuarioListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( id == null || tfNome.getText().isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Selecione um usuário para alterar.", "Alerta", JOptionPane.WARNING_MESSAGE );
			} else {
				JDialog dialog = new AlterarSenhaDialog( id, tfNome.getText() );
				dialog.pack();
				dialog.setVisible( true );
				id = null;
				tfNome.setText( null );
			}
		}

	}

	private class deletarUsuarioListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			for ( int i = 0; i < table.getRowCount(); i++ ) {
				if ( (Boolean) table.getModel().getValueAt( i, 2 ) ) {
					String usuario = (String) table.getModel().getValueAt( i, 1 );
					int choice = TaskDialogs.choice( null, "O que você gostaria de fazer com o usuário " + usuario + "?", "", 1, new CommandLink(
							"Deletar o usuário", "Deletar o usuário e o mesmo não ter mais acesso ao sistema." ), new CommandLink( "Não deletar o usuário",
							"Não deletar o usuário e manter o acesso do mesmo ao sistema" ) );

					if ( choice == 0 ) {
						table.setModel( AcoesUsuario.getInstance().deletar( id ) );
						table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
						setarTamanhoColunas();
						tfNome.setText( null );
						tfSenha.setText( null );
						id = null;
					}

				}
			}
		}
	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked( MouseEvent e ) {
			int linha = table.getSelectedRow();
			for ( int i = 0; i < table.getRowCount(); i++ ) {
				if ( table.getModel().getValueAt( i, 2 ).equals( new Boolean( true ) ) && linha != i )
					table.getModel().setValueAt( new Boolean( false ), i, 2 );
			}
			id = new Long( ((String) table.getModel().getValueAt( linha, 0 )) );
		}

		@Override
		public void mouseEntered( MouseEvent e ) {
		}

		@Override
		public void mouseExited( MouseEvent e ) {
		}

		@Override
		public void mousePressed( MouseEvent e ) {
		}

		@Override
		public void mouseReleased( MouseEvent e ) {
		}
	}

}
