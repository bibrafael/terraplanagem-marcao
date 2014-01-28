package control;

import java.util.List;
import java.util.Vector;

import model.MyTableModel;
import model.pojo.Produto;
import model.services.ServicosProduto;

public class AcoesProduto {

	private static AcoesProduto instance = null;

	public static AcoesProduto getInstance() {
		if ( instance == null ) {
			instance = new AcoesProduto();
		}
		return instance;
	}

	private Vector< String > colunas;

	private AcoesProduto() {
		colunas = new Vector< String >();
		colunas.add( "Id" );
		colunas.add( "Descrição" );
		colunas.add( "Preço Custo" );
		colunas.add( "Preço à Vista" );
		colunas.add( "Prelo à Prazo" );
		colunas.add( "Densidade" );
	}

	public Produto getProduto( Long id ) {
		Produto produto = null;
		if ( id != null ) {
			produto = ServicosProduto.getInstance().lerPorId( id );
		}
		return produto;
	}

	public MyTableModel criar( String descricao, double pCusto, double densidade, double pVista, double pPrazo ) {
		Produto produto = new Produto();
		produto.setDescricao( descricao );
		produto.setpCusto( pCusto );
		produto.setDensidade( densidade );
		produto.setpVista( pVista );
		produto.setpPrazo( pPrazo );
		ServicosProduto.getInstance().criar( produto );
		return ( createModel( ServicosProduto.getInstance().ler( null ) ) );

	}

	public MyTableModel ler( String descricao ) {
		Produto produto = new Produto();
		produto.setDescricao( descricao );
		return ( createModel( ServicosProduto.getInstance().ler( produto ) ) );
	}

	public MyTableModel atualizar( Long id, String descricao, double pCusto, double densidade, double pVista,
			double pPrazo ) {
		Produto produto = new Produto();
		produto.setId( id );
		produto.setDescricao( descricao );
		produto.setpCusto( pCusto );
		produto.setDensidade( densidade );
		produto.setpVista( pVista );
		produto.setpPrazo( pPrazo );
		ServicosProduto.getInstance().atualizar( produto );
		return ( createModel( ServicosProduto.getInstance().ler( null ) ) );
	}

	public MyTableModel deletar( Long id ) {
		if ( id != null ) {
			ServicosProduto.getInstance().deletar( id );
		}
		return ( createModel( ServicosProduto.getInstance().ler( null ) ) );
	}

	public Produto lerPorId( Long id ) {
		return ServicosProduto.getInstance().lerPorId( id );
	}

	private MyTableModel createModel( List< Produto > produtos ) {
		MyTableModel model = null;
		Vector< Vector< Object >> dados = new Vector< Vector< Object >>();
		if ( produtos != null ) {
			for ( int i = 0; i < produtos.size(); i++ ) {
				Produto aux = produtos.get( i );
				Vector< Object > linha = new Vector< Object >();
				linha.add( aux.getId().toString() );
				linha.add( aux.getDescricao() );
				linha.add( String.valueOf( aux.getpCusto() ) );
				linha.add( String.valueOf( aux.getpVista() ) );
				linha.add( String.valueOf( aux.getpPrazo() ) );
				linha.add( String.valueOf( aux.getDensidade() ) );
				dados.add( linha );
			}
		}
		model = new MyTableModel( dados, colunas );
		return ( model );
	};
}
