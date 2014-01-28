package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.MyTableModel;
import control.AcoesProduto;

public class ProdutoDialog extends JDialog {

	private static final long serialVersionUID = 7834424589685852615L;

	private JPanel panelToolBar;
	private Long id;

	private JPanel panelItens;
	private JLabel lbDescricao;
	private JLabel lbPUnitario;
	private JLabel lbDensidade;
	private JLabel lbPVista;
	private JLabel lbPPrazo;
	private JTextField tfDescricao;
	private JFormattedTextField tfPUnitario;
	private JFormattedTextField tfDensidade;
	private JFormattedTextField tfPVista;
	private JFormattedTextField tfPPrazo;

	private JPanel panelButtons;
	private JButton btCriar;
	private JButton btAlterar;
	private JButton btDeletar;
	private JButton btLimpar;

	private JScrollPane scrollPanel;
	private JTable table;
	private MyTableModel tableModel;

	public ProdutoDialog() {
		this.setTitle( "Terraplanagem São Marcos - Cadastro de Produtos" );

		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets( 5, 5, 5, 5 );

		panelToolBar = new JPanel();
		panelToolBar.setLayout( new BorderLayout() );

		panelItens = new JPanel();
		panelItens.setLayout( new GridBagLayout() );
		id = new Long( 0 );

		lbDescricao = new JLabel( "Descrição:" );
		cons.gridy = 0;
		cons.gridx = 2;
		cons.anchor = GridBagConstraints.LINE_START;
		panelItens.add( lbDescricao, cons );

		tfDescricao = new JTextField( 15 );
		cons.gridy = 0;
		cons.gridx = 3;
		cons.gridwidth = 3;
		tfDescricao.addKeyListener( new pesquisaProdutoListener() );
		panelItens.add( tfDescricao, cons );

		lbPUnitario = new JLabel( "Preço Custo:" );
		cons.gridy = 1;
		cons.gridx = 0;
		cons.gridwidth = 1;
		panelItens.add( lbPUnitario, cons );

		tfPUnitario = new JFormattedTextField();
		InputMap mapPUnitario = tfPUnitario.getInputMap();
		mapPUnitario.put( KeyStroke.getKeyStroke( 'V', InputEvent.CTRL_DOWN_MASK ), "beep" );
		mapPUnitario.put( KeyStroke.getKeyStroke( 'C', InputEvent.CTRL_DOWN_MASK ), "beep" );
		tfPUnitario.setColumns( 7 );
		cons.gridy = 1;
		cons.gridx = 1;
		tfPUnitario.addKeyListener( new teclasNumericasListener() );
		panelItens.add( tfPUnitario, cons );

		lbPVista = new JLabel( "Preço à Vista" );
		cons.gridy = 1;
		cons.gridx = 2;
		panelItens.add( lbPVista, cons );

		tfPVista = new JFormattedTextField();
		InputMap mapPVista = tfPVista.getInputMap();
		mapPVista.put( KeyStroke.getKeyStroke( 'V', InputEvent.CTRL_DOWN_MASK ), "beep" );
		mapPVista.put( KeyStroke.getKeyStroke( 'C', InputEvent.CTRL_DOWN_MASK ), "beep" );
		tfPVista.setColumns( 7 );
		cons.gridy = 1;
		cons.gridx = 3;
		tfPVista.addKeyListener( new teclasNumericasListener() );
		panelItens.add( tfPVista, cons );

		lbPPrazo = new JLabel( "Preço à Prazo" );
		cons.gridy = 1;
		cons.gridx = 4;
		panelItens.add( lbPPrazo, cons );

		tfPPrazo = new JFormattedTextField();
		InputMap mapPPrazo = tfPPrazo.getInputMap();
		mapPPrazo.put( KeyStroke.getKeyStroke( 'V', InputEvent.CTRL_DOWN_MASK ), "beep" );
		mapPPrazo.put( KeyStroke.getKeyStroke( 'C', InputEvent.CTRL_DOWN_MASK ), "beep" );
		tfPPrazo.setColumns( 7 );
		cons.gridy = 1;
		cons.gridx = 5;
		tfPPrazo.addKeyListener( new teclasNumericasListener() );
		panelItens.add( tfPPrazo, cons );

		lbDensidade = new JLabel( "Densidade:" );
		cons.gridy = 1;
		cons.gridx = 6;
		panelItens.add( lbDensidade, cons );

		tfDensidade = new JFormattedTextField();
		InputMap mapDensidade = tfDensidade.getInputMap();
		mapDensidade.put( KeyStroke.getKeyStroke( 'V', InputEvent.CTRL_DOWN_MASK ), "beep" );
		mapDensidade.put( KeyStroke.getKeyStroke( 'C', InputEvent.CTRL_DOWN_MASK ), "beep" );
		tfDensidade.setColumns( 7 );
		cons.gridy = 1;
		cons.gridx = 7;
		tfDensidade.addKeyListener( new teclasNumericasListener() );
		panelItens.add( tfDensidade, cons );

		btLimpar = new JButton( "Limpar" );
		cons.gridy = 2;
		cons.gridx = 3;
		btLimpar.addActionListener( new limparTelaListener() );
		panelItens.add( btLimpar, cons );

		panelToolBar.add( panelItens, BorderLayout.PAGE_START );

		panelButtons = new JPanel( new FlowLayout() );

		btCriar = new JButton( "Inserir" );
		btCriar.addActionListener( new criarProdutoListener() );
		panelButtons.add( btCriar );

		btAlterar = new JButton( "Alterar" );
		btAlterar.addActionListener( new alterarProdutoListener() );
		panelButtons.add( btAlterar );

		btDeletar = new JButton( "Deletar" );
		btDeletar.addActionListener( new deletarProdutoListener() );
		panelButtons.add( btDeletar );

		tableModel = AcoesProduto.getInstance().ler( "" );
		table = new JTable( tableModel ) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable( int row, int col ) {
				return false;
			}
		};
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

	private class pesquisaProdutoListener implements KeyListener {

		@Override
		public void keyPressed( KeyEvent e ) {
		}

		@Override
		public void keyReleased( KeyEvent e ) {
			table.setModel( AcoesProduto.getInstance().ler( tfDescricao.getText() ) );
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
			setarTamanhoColunas();
			tfPUnitario.setText( null );
			tfDensidade.setText( null );
			id = null;
		}

		@Override
		public void keyTyped( KeyEvent e ) {
		}

	}

	private class teclasNumericasListener implements KeyListener {

		@Override
		public void keyPressed( KeyEvent e ) {
		}

		@Override
		public void keyReleased( KeyEvent e ) {
			if ( !( e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4'
					|| e.getKeyChar() == '5' || e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8'
					|| e.getKeyChar() == '9' || e.getKeyChar() == '0' || e.getKeyChar() == ','
					|| e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27
					|| e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == 16 || e.getKeyCode() == 127
					|| e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e
					.getKeyCode() == 46 ) || e.getKeyCode() == 17 ) {
			}
		}

		@Override
		public void keyTyped( KeyEvent e ) {
			if ( !( e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4'
					|| e.getKeyChar() == '5' || e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8'
					|| e.getKeyChar() == '9' || e.getKeyChar() == '0' || e.getKeyCode() == KeyEvent.VK_ENTER
					|| e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyChar() == '.' ) ) {
				e.consume();
			} else {

			}
		}
	}

	class CenterRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 4433840270638649209L;

		public CenterRenderer() {
			setHorizontalAlignment( CENTER );
		}
	}

	public void setarTamanhoColunas() {
		table.getColumnModel().getColumn( 0 ).setPreferredWidth( 200 );
		table.getColumnModel().getColumn( 1 ).setPreferredWidth( 150 );
		table.getColumnModel().getColumn( 2 ).setPreferredWidth( 150 );

		TableCellRenderer centerRenderer = new CenterRenderer();
		TableColumn column = table.getColumnModel().getColumn( 2 );
		column.setCellRenderer( centerRenderer );
		column = table.getColumnModel().getColumn( 1 );
		column.setCellRenderer( centerRenderer );
	}

	private class criarProdutoListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( tfDescricao.getText().isEmpty() || tfPUnitario.getText().isEmpty() || tfDensidade.getText().isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Todos os campos devem ser preenchidos!", "Alerta",
						JOptionPane.WARNING_MESSAGE );
			} else {
				String pUnitario = tfPUnitario.getText();
				String densidade = tfDensidade.getText();
				String pVista = tfPVista.getText();
				String pPrazo = tfPPrazo.getText();
				if ( tfPUnitario.getText().contains( "," ) ) {
					pUnitario = pUnitario.replace( ",", "." );
				}
				if ( tfDensidade.getText().contains( "," ) ) {
					densidade = densidade.replace( ",", "." );
				}
				if ( tfPPrazo.getText().contains( "," ) ) {
					pPrazo = pPrazo.replace( ",", "." );
				}
				if ( tfPVista.getText().contains( "," ) ) {
					pVista = pVista.replace( ",", "." );
				}
				table.setModel( AcoesProduto.getInstance().criar( tfDescricao.getText(),
						Double.parseDouble( pUnitario ), Double.parseDouble( densidade ), Double.parseDouble( pVista ),
						Double.parseDouble( pPrazo ) ) );
				table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
				setarTamanhoColunas();
				tfDescricao.setText( null );
				tfPUnitario.setText( null );
				tfDensidade.setText( null );
				tfPVista.setText( null );
				tfPPrazo.setText( null );
				id = null;
				JOptionPane.showMessageDialog( null, "Produto cadastrado com sucesso.", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE );
			}
		}
	}

	private class alterarProdutoListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( tfDescricao.getText().isEmpty() || tfPUnitario.getText().isEmpty() || tfDensidade.getText().isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Todos os campos devem ser preenchidos!", "Alerta",
						JOptionPane.WARNING_MESSAGE );
			} else {
				String pUnitario = tfPUnitario.getText();
				String densidade = tfDensidade.getText();
				String pVista = tfPVista.getText();
				String pPrazo = tfPPrazo.getText();
				if ( tfPUnitario.getText().contains( "," ) ) {
					pUnitario = pUnitario.replace( ",", "." );
				}
				if ( tfDensidade.getText().contains( "," ) ) {
					densidade = densidade.replace( ",", "." );
				}
				if ( tfPPrazo.getText().contains( "," ) ) {
					pPrazo = pPrazo.replace( ",", "." );
				}
				if ( tfPVista.getText().contains( "," ) ) {
					pVista = pVista.replace( ",", "." );
				}
				table.setModel( AcoesProduto.getInstance().atualizar( id, tfDescricao.getText(),
						Double.parseDouble( pUnitario ), Double.parseDouble( densidade ), Double.parseDouble( pVista ),
						Double.parseDouble( pPrazo ) ) );
				table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
				setarTamanhoColunas();
				tfDescricao.setText( null );
				tfPUnitario.setText( null );
				tfDensidade.setText( null );
				tfPVista.setText( null );
				tfPPrazo.setText( null );
				id = null;
			}
		}
	}

	private class deletarProdutoListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( id == null ) {
				JOptionPane.showMessageDialog( null, "Selecione um usuário para deletar!", "Erro",
						JOptionPane.ERROR_MESSAGE );
			} else {
				Object[] options = { "Sim", "Não" };
				int sd = JOptionPane.showOptionDialog( null, "Deseja deletar produto?", "Alerta",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0] );
				if ( sd == 0 ) {
					table.setModel( AcoesProduto.getInstance().deletar( id ) );
					table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
					setarTamanhoColunas();
					tfDescricao.setText( null );
					tfPUnitario.setText( null );
					tfDensidade.setText( null );
					id = null;
				} else {
				}
			}
		}

	}

	private class limparTelaListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			tfDescricao.setText( null );
			tfDensidade.setText( null );
			tfPUnitario.setText( null );
			table.setModel( AcoesProduto.getInstance().ler( tfDescricao.getText() ) );
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
			setarTamanhoColunas();
		}

	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked( MouseEvent e ) {
			int linha = table.getSelectedRow();
			id = new Long( ( (String) table.getModel().getValueAt( linha, 0 ) ) );
			tfDescricao.setText( (String) table.getModel().getValueAt( linha, 1 ) );
			tfPUnitario.setText( (String) table.getModel().getValueAt( linha, 2 ) );
			tfPVista.setText( (String) table.getModel().getValueAt( linha, 3 ) );
			tfPPrazo.setText( (String) table.getModel().getValueAt( linha, 4 ) );
			tfDensidade.setText( (String) table.getModel().getValueAt( linha, 5 ) );

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
