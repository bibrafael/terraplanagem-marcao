package control;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import model.MyTableModel;
import model.pojo.Transporte;
import model.services.ServicosTransporte;

public class AcoesTransporte {

	private static AcoesTransporte instance = null;

	public static AcoesTransporte getInstance() {
		if (instance == null) {
			instance = new AcoesTransporte();
		}
		return instance;
	}

	private Vector<String> colunas;

	private AcoesTransporte() {
		colunas = new Vector<String>();
		colunas.add("Id");
		colunas.add("Id Pedido");
		colunas.add("Descrição");
		colunas.add("Placa");
		colunas.add("Qtde Hora");
		colunas.add("Valor Hora");
		colunas.add("Valor Total");
	}

	public MyTableModel criar(Long pedidoId, String descricao, String placa,
			double qHora, double vHora, double vTotal) {
		Transporte transporte = new Transporte();
		transporte.setDescricao(descricao);
		transporte.setPedidoId(pedidoId);
		transporte.setPlaca(placa);
		transporte.setqHora(qHora);
		transporte.setvHora(vHora);
		transporte.setvTotal(vTotal);
		ServicosTransporte.getInstance().criar(transporte);
		return (createModel(ServicosTransporte.getInstance().lerPorPedido(
				pedidoId)));
	}

	public MyTableModel lerPorPedido(Long pedidoId) {
		return (createModel(ServicosTransporte.getInstance().lerPorPedido(
				pedidoId)));
	}

	public MyTableModel deletar(Long id, Long pedidoId) throws Exception {
		ServicosTransporte.getInstance().deletar(id);
		return (createModel(ServicosTransporte.getInstance().lerPorPedido(
				pedidoId)));
	}

	private MyTableModel createModel(List<Transporte> transportes) {
		MyTableModel model = null;
		Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
		if (transportes != null) {
			for (int i = 0; i < transportes.size(); i++) {
				Vector<Object> linha = new Vector<Object>();
				Transporte aux = transportes.get(i);
				linha.add(aux.getId().toString());
				linha.add(aux.getPedidoId().toString());
				linha.add(aux.getDescricao());
				linha.add(aux.getPlaca());
				linha.add(String.valueOf(aux.getqHora()));
				linha.add(String.valueOf(aux.getvHora()));
				linha.add(String.valueOf(aux.getvTotal()));
				dados.add(linha);
			}
			double valorTotal = 0;
			for (int i = 0; i < transportes.size(); i++) {
				valorTotal += transportes.get(i).getvTotal();
			}

			if (!transportes.isEmpty()) {

				DecimalFormat format = new DecimalFormat("####.##");
				format.setMaximumFractionDigits(2);

				Vector<Object> linha = new Vector<Object>();
				linha.add(null);
				linha.add(null);
				linha.add("TOTAL");
				linha.add("------------");
				linha.add("------------");
				linha.add("------------");
				linha.add(String.valueOf(format.format(valorTotal)).replace(
						",", "."));
				dados.add(linha);
			}
		}
		model = new MyTableModel(dados, colunas);
		return (model);
	};

}
