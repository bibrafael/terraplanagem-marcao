package model.services;

import java.util.List;

import model.dao.ProdutoDAO;
import model.pojo.Produto;

public class ServicosProduto {

	private static ServicosProduto instance = null;
	
	public static ServicosProduto getInstance() {
		if(instance == null) {
			instance = new ServicosProduto();
		}
		return instance;
	}
	
	private ServicosProduto() {}
	
	public void criar(Produto produto) {
		if(produto != null) {
			ProdutoDAO.getInstance().criar(produto);
		}
	}
	
	public List<Produto> ler(Produto produto) {
		if(produto == null) {
			produto = new Produto();
			produto.setDescricao("");
		}
		List<Produto> produtos = ProdutoDAO.getInstance().ler(produto);
		return produtos;
	}
	
	public Produto lerPorId(Long id) {
		Produto produto = null;
		if(id != null) {
			produto = ProdutoDAO.getInstance().lerPorId(id);
		}
		return produto;
	}
	
	public void atualizar(Produto produto) {
		if(produto != null) {
			ProdutoDAO.getInstance().atualizar(produto);
		}
	}
	
	public void deletar(Long id) {
		if(id != null) {
			ProdutoDAO.getInstance().deletar(id);
		}
	}
	
}
