package control;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.table.TableModel;

import model.MyTableModel;
import model.pojo.Pedido;
import model.services.ServicosPedido;

public class AcoesPedido {

	private static AcoesPedido instance = null;

	public static AcoesPedido getInstance(boolean tipo) {
		instance = new AcoesPedido(tipo);
		return instance;
	}

	private Vector<String> colunas;

	private boolean pTipo = false;

	private AcoesPedido(boolean tipo) {
		pTipo = tipo;
		if(tipo) {
			colunas = new Vector<String>();
			colunas.add("Nº do Pedido");
			colunas.add("Id Cliente");
			colunas.add("Data");
			colunas.add("Local de Entrega");
			colunas.add("Valor Total");
			colunas.add("Pagamento Efetuado");
		}else {
			colunas = new Vector<String>();
			colunas.add("Nº do Pedido");
			colunas.add("Id Cliente");
			colunas.add("Data");
			colunas.add("Valor Total");
			colunas.add("Pagamento Efetuado");
		}
	}

	public TableModel criar(Long idCliente, Date data, String localEntrega, boolean tipo) {
		Pedido pedido = new Pedido();
		pedido.setClienteId(idCliente);
		pedido.setData(data);
		pedido.setLocalEntrega(localEntrega);
		pedido.setTipo(tipo);
		ServicosPedido.getInstance().criar(pedido);
		return (createModel(ServicosPedido.getInstance().ler(null)));
	}

	public TableModel ler(Long idCliente, boolean tipo) {
		Pedido pedido = new Pedido();
		pedido.setClienteId(idCliente);
		pedido.setTipo(tipo);
		return(createModel(ServicosPedido.getInstance().ler(pedido)));
	}

	public void deletar(Long pedidoId) throws SQLException {
		ServicosPedido.getInstance().deletar(pedidoId);
	}

	public void atualizar(Long pedidoId, boolean pagamento, double vTotal) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoId);
		pedido.setvTotal(vTotal);
		pedido.setPagEfetuado(pagamento);
		ServicosPedido.getInstance().atualizar(pedido);
	}

	private TableModel createModel(List<Pedido> pedidos) {
		TableModel model = null;
		Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
		if(pedidos != null){
			for(int i=0; i < pedidos.size();i++){
				Pedido aux = pedidos.get(i);
				Vector<Object> linha = new Vector<Object>();
				linha.add(aux.getId().toString());
				linha.add(aux.getClienteId().toString());
				Date date = aux.getData();
				java.util.Date date2 = new java.util.Date(date.getTime());
				linha.add(new SimpleDateFormat("dd/MM/yyyy").format(date2).toString());
				if(pTipo) {
					linha.add(aux.getLocalEntrega());
				}
				linha.add(String.valueOf(aux.getvTotal()));
				linha.add(new Boolean(aux.isPagEfetuado()));
				dados.add(linha);
			}
		}
		model = new MyTableModel(dados,colunas);
		return(model);
	};

	public Pedido lerPorId(Long pedidoId) {
		return ServicosPedido.getInstance().lerPorId(pedidoId);
	}

	public Long getIdPedido() {
		return ServicosPedido.getInstance().getIdPedido();
	}

}
