package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.MyTableModel;
import control.AcoesProduto;

public class SelecionarProdutoDialog extends JDialog {

	private static final long serialVersionUID = 799249096234142312L;

	private JPanel panelToolBar;
	private Long id;
	protected boolean okSelecionado;

	private JPanel panelItens;
	private JLabel lbDescricao;
	private JTextField tfDescricao;

	private JPanel panelButtons;
	private JButton btAdicionar;
	private JButton btCancelar;

	private JScrollPane scrollPanel;
	private JTable table;
	private MyTableModel tableModel;

	public SelecionarProdutoDialog() {
		this.setTitle( "Terraplanagem São Marcos - Selecionar Produto" );

		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets( 5, 5, 5, 5 );

		panelToolBar = new JPanel();
		panelToolBar.setLayout( new BorderLayout() );

		panelItens = new JPanel();
		panelItens.setLayout( new GridBagLayout() );

		lbDescricao = new JLabel( "Descrição:" );
		cons.gridy = 0;
		cons.gridx = 0;
		panelItens.add( lbDescricao, cons );

		tfDescricao = new JTextField( 20 );
		cons.gridy = 0;
		cons.gridx = 1;
		tfDescricao.addKeyListener( new PesquisaProdutoListener() );
		panelItens.add( tfDescricao, cons );

		panelToolBar.add( panelItens, BorderLayout.PAGE_START );

		panelButtons = new JPanel( new FlowLayout() );

		btAdicionar = new JButton( "Adicionar Produto" );
		btAdicionar.addActionListener( new adicionarProdutoListener() );
		panelButtons.add( btAdicionar );

		btCancelar = new JButton( "Cancelar" );
		btCancelar.addActionListener( new cancelarProdutoListener( this ) );
		panelButtons.add( btCancelar );

		tableModel = AcoesProduto.getInstance().ler( "" );
		table = new JTable( tableModel ) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable( int row, int col ) {
				return false;
			}
		};
		table.addMouseListener( new selecionarLinhaTabela() );
		table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
		scrollPanel = new JScrollPane( table );

		this.setLayout( new BorderLayout() );
		this.add( panelToolBar, BorderLayout.PAGE_START );
		this.add( scrollPanel, BorderLayout.CENTER );
		this.add( panelButtons, BorderLayout.PAGE_END );
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );

	}

	private class PesquisaProdutoListener implements KeyListener {

		@Override
		public void keyPressed( KeyEvent e ) {
		}

		@Override
		public void keyReleased( KeyEvent e ) {
			table.setModel( AcoesProduto.getInstance().ler( tfDescricao.getText() ) );
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn( 0 ) );
			id = null;
		}

		@Override
		public void keyTyped( KeyEvent e ) {
		}

	}

	private class cancelarProdutoListener implements ActionListener {

		private SelecionarProdutoDialog dialog = null;

		public cancelarProdutoListener( SelecionarProdutoDialog selecionarProdutoDialog ) {
			dialog = selecionarProdutoDialog;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			dialog.dispose();
		}

	}

	public Long getID() {
		return id;
	}

	private class adicionarProdutoListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( id == null ) {
				JOptionPane.showMessageDialog( null, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE );
			} else {
				okSelecionado = true;
				dispose();
			}
		}

	}

	public boolean produtoSelecionado() {
		okSelecionado = false;
		pack();
		setModal( true );
		setVisible( true );
		return okSelecionado;
	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked( MouseEvent e ) {
			int linha = table.getSelectedRow();
			id = new Long( ( (String) table.getModel().getValueAt( linha, 0 ) ) );
			tfDescricao.setText( (String) table.getModel().getValueAt( linha, 1 ) );
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
