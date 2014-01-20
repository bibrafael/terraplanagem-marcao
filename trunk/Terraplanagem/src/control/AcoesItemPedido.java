package control;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.pojo.ItemPedido;
import model.pojo.Produto;
import model.services.ServicosItemPedido;

public class AcoesItemPedido {

	private static AcoesItemPedido instance = null;

	public static AcoesItemPedido getInstance() {
		if(instance == null) {
			instance = new AcoesItemPedido();
		}
		return instance;
	}

	private Vector<String> colunas;

	private AcoesItemPedido(){
		colunas = new Vector<String>();
		colunas.add("Id");
		colunas.add("Id Pedido");
		colunas.add("Id Produto");
		colunas.add("Produto");
		colunas.add("Densidade");
		colunas.add("Peso do Caminhão");
		colunas.add("Quantidade");
		colunas.add("Preço Unitário");
		colunas.add("Preço Total");
	}

	public DefaultTableModel criar(Long pedidoId, Long produtoId, double quantidade, double pUnitario, double pTotal, double pesoCaminhao, double densidade) {
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPedidoId(pedidoId);
		itemPedido.setProdutoId(produtoId);
		itemPedido.setQuantidade(quantidade);
		itemPedido.setpUnitario(pUnitario);
		itemPedido.setpTotal(pTotal);
		itemPedido.setPesoCaminhao(pesoCaminhao);
		itemPedido.setDensidade(densidade);
		ServicosItemPedido.getInstance().criar(itemPedido);

		return(createModel(ServicosItemPedido.getInstance().lerPorPedido(pedidoId)));
	}

	public DefaultTableModel removerProduto(Long id, Long pedidoId) {
		if(id != null) {
			ServicosItemPedido.getInstance().removerProduto(id);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione um produto!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		return(createModel(ServicosItemPedido.getInstance().lerPorPedido(pedidoId)));
	}

	public DefaultTableModel lerPorPedido(Long pedidoId) {
		return(createModel(ServicosItemPedido.getInstance().lerPorPedido(pedidoId)));
	}

	private DefaultTableModel createModel(List<ItemPedido> itemPedidos) {
		DefaultTableModel model = null;
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		if(itemPedidos != null){
			for(int i=0; i < itemPedidos.size();i++){
				Vector<String> linha = new Vector<String>();
				ItemPedido aux = itemPedidos.get(i);
				linha.add(aux.getId().toString());
				linha.add(aux.getPedidoId().toString());
				linha.add(aux.getProdutoId().toString());
				Produto produto = AcoesProduto.getInstance().lerPorId(aux.getProdutoId());
				linha.add(produto.getDescricao());
				linha.add(String.valueOf(aux.getDensidade()));
				linha.add(String.valueOf(aux.getPesoCaminhao()));
				linha.add(String.valueOf(aux.getQuantidade()));
				linha.add(String.valueOf(aux.getpUnitario()));
				linha.add(String.valueOf(aux.getpTotal()));
				dados.add(linha);
			}
			double valorTotal = 0;
			for (int i = 0; i < itemPedidos.size(); i++) {
				valorTotal += itemPedidos.get(i).getpTotal();
			}

			if(!itemPedidos.isEmpty()) {

				DecimalFormat format = new DecimalFormat("####.##");
				format.setMaximumFractionDigits(2);

				Vector<String> linha = new Vector<String>();
				linha.add(null);
				linha.add(null);
				linha.add(null);
				linha.add("TOTAL");
				linha.add("------------");
				linha.add("------------");
				linha.add("------------");
				linha.add("------------");
				linha.add(String.valueOf(format.format(valorTotal)).replace(",", "."));
				dados.add(linha);
			}
		}
		model = new DefaultTableModel(dados,colunas);
		return(model);
	};
}
