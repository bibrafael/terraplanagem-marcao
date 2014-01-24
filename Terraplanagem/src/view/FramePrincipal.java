package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class FramePrincipal extends JFrame {

	private static final long serialVersionUID = -2481060993904993412L;

	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem menuItemCliente;
	private JMenuItem menuItemProduto;
	private JMenuItem menuItemUsuario;
	private JMenuItem menuItemVendas;
	private JMenuItem menuItemRelatorio;
	private JMenuItem menuItemTransporte;
	private JMenuItem menuItemSair;

	public FramePrincipal() {
		this.setTitle( "Terraplanagem São Marcos" );
		menuBar = new JMenuBar();
		menu = new JMenu( "Cadastros" );
		menuItemCliente = new JMenuItem( "Cliente" );
		menuItemCliente.addActionListener( new ClienteListener() );
		menu.add( menuItemCliente );
		menuItemProduto = new JMenuItem( "Produto" );
		menuItemProduto.addActionListener( new ProdutoListener() );
		menu.add( menuItemProduto );
		menuItemUsuario = new JMenuItem( "Usuário" );
		menuItemUsuario.addActionListener( new UsuarioListener() );
		menu.add( menuItemUsuario );
		menuBar.add( menu );

		menu = new JMenu( "Serviços" );
		menuItemTransporte = new JMenuItem( "Transportes" );
		menuItemTransporte.addActionListener( new TransporteListener() );
		menu.add( menuItemTransporte );
		menuItemVendas = new JMenuItem( "Vendas" );
		menuItemVendas.addActionListener( new VendasListener() );
		menu.add( menuItemVendas );
		menuItemRelatorio = new JMenuItem( "Relatório de Transporte" );
		menuItemRelatorio.addActionListener( new RelatorioTransporteListener() );
		menu.add( menuItemRelatorio );
		menuItemRelatorio = new JMenuItem( "Relatório de Vendas" );
		menuItemRelatorio.addActionListener( new RelatorioVendasListener() );
		menu.add( menuItemRelatorio );
		menuBar.add( menu );

		menu = new JMenu( "Sair" );
		menuItemSair = new JMenuItem( "Fechar Programa" );
		menuItemSair.addActionListener( new FecharProgramaListener() );
		menu.add( menuItemSair );
		menuBar.add( menu );

		this.setJMenuBar( menuBar );
		this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	}

	private class RelatorioTransporteListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new RelatorioTransporteDialog();
			dialog.pack();
			dialog.show( true );
		}

	}

	private class FecharProgramaListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			System.exit( EXIT_ON_CLOSE );
		}

	}

	private class RelatorioVendasListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new RelatorioVendasDialog();
			dialog.pack();
			dialog.show( true );
		}

	}

	private class VendasListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new VendasDialog( true );
			dialog.setModal( true );
			dialog.setSize( 800, 500 );
			dialog.show( true );
		}
	}

	private class TransporteListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new VendasDialog( false );
			dialog.setModal( true );
			dialog.setSize( 800, 500 );
			dialog.show( true );
		}

	}

	private class ClienteListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new ClienteDialog();
			dialog.setModal( true );
			dialog.setSize( 800, 500 );
			dialog.setVisible( true );
		}
	}

	private class ProdutoListener implements ActionListener {

		@SuppressWarnings( "deprecation" )
		@Override
		public void actionPerformed( ActionEvent e ) {
			JDialog dialog = new ProdutoDialog();
			dialog.setModal( true );
			dialog.setSize( 800, 500 );
			dialog.show( true );
		}
	}

	private class UsuarioListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			DialogUsuario dlgUsuario = new DialogUsuario();
			dlgUsuario.setModal( true );
			dlgUsuario.setLocationRelativeTo( null );
			dlgUsuario.setVisible( true );
		}

	}

}
