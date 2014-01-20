package control;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.pojo.Produto;
import model.services.ServicosProduto;

public class AcoesProduto {

	private static AcoesProduto instance = null;

	public static AcoesProduto getInstance() {
		if(instance == null) {
			instance = new AcoesProduto();
		}
		return instance;
	}

	private Vector<String> colunas;

	private AcoesProduto() {
		colunas = new Vector<String>();
		colunas.add("Id");
		colunas.add("Descrição");
		colunas.add("Preço Custo");
		colunas.add("Preço À Vista");
		colunas.add("Preço À Prazo");
		colunas.add("Densidade");
	}

	public Produto getProduto(Long id) {
		Produto produto = null;
		if(id != null) {
			produto = ServicosProduto.getInstance().lerPorId(id);
		}
		return produto;
	}


	public DefaultTableModel criar(String descricao, double pCusto, double densidade, double pVista, double pPrazo) {
		Produto produto = new Produto();
		produto.setDescricao(descricao);
		produto.setpCusto(pCusto);
		produto.setDensidade(densidade);
		produto.setpVista(pVista);
		produto.setpPrazo(pPrazo);
		ServicosProduto.getInstance().criar(produto);
		return (createModel(ServicosProduto.getInstance().ler(null)));

	}

	public DefaultTableModel ler(String descricao) {
		Produto produto = new Produto();
		produto.setDescricao(descricao);
		return (createModel(ServicosProduto.getInstance().ler(produto)));
	}

	public DefaultTableModel atualizar(Long id, String descricao, double pCusto, double densidade, double pVista, double pPrazo) {
		Produto produto = new Produto();
		produto.setId(id);
		produto.setDescricao(descricao);
		produto.setpCusto(pCusto);
		produto.setDensidade(densidade);
		produto.setpVista(pVista);
		produto.setpPrazo(pPrazo);
		ServicosProduto.getInstance().atualizar(produto);
		return (createModel(ServicosProduto.getInstance().ler(null)));
	}

	public DefaultTableModel deletar(Long id) {
		if(id != null) {
			ServicosProduto.getInstance().deletar(id);
		}
		return (createModel(ServicosProduto.getInstance().ler(null)));
	}
	
	public Produto lerPorId(Long id) {
		return ServicosProduto.getInstance().lerPorId(id);
	}

	private DefaultTableModel createModel(List<Produto> produtos) {
		DefaultTableModel model = null;
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		if(produtos != null){
			for(int i=0; i < produtos.size();i++){
				Produto aux = produtos.get(i);
				Vector<String> linha = new Vector<String>();
				linha.add(aux.getId().toString());
				linha.add(aux.getDescricao());
				linha.add(String.valueOf(aux.getpCusto()));
				linha.add(String.valueOf(aux.getpVista()));
				linha.add(String.valueOf(aux.getpPrazo()));
				linha.add(String.valueOf(aux.getDensidade()));
				dados.add(linha);
			}
		}
		model = new DefaultTableModel(dados,colunas);
		return(model);
	};
}
