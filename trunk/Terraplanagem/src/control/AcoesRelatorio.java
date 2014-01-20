package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.pojo.Cliente;
import model.pojo.ComboBoxItem;
import model.pojo.ItemPedido;
import model.services.ConnectionManager;
import model.services.ServicosCliente;
import model.services.ServicosRelatorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class AcoesRelatorio {

	private static AcoesRelatorio instance = null;

	public static AcoesRelatorio getInstance() {
		if(instance == null) {
			instance = new AcoesRelatorio();
		}
		return instance;
	}

	private AcoesRelatorio() {}

	@SuppressWarnings("unchecked")
	public void criar(Long idCliente, Date dataInicio, Date dataTermino, boolean tdPedidos, boolean tipo) {

		List<ItemPedido> itens = new ArrayList<ItemPedido>(); 
		itens = (ArrayList)ServicosRelatorio.getInstance().criar(idCliente, dataInicio, dataTermino, tdPedidos, tipo);

		java.util.Date dtInicio = new java.util.Date(dataInicio.getTime());
		java.util.Date dtTermino = new java.util.Date(dataTermino.getTime());
		Timestamp timeInicio = new Timestamp(dataInicio.getTime());
		Timestamp timeTermino = new Timestamp(dataTermino.getTime());
		Connection conn = ConnectionManager.getInstance().getConnection();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


		String caminhoRelJasper;
		// Caminho do .jasper do relatorio
		if(tipo) {
			caminhoRelJasper = "C:/Arquivos de programas/Terraplanagem/relatorio/relatorio.jasper";
//			caminhoRelJasper = "D:/Workspace/Terraplanagem/relatorio/relatorio.jasper";
		}else {
			caminhoRelJasper = "C:/Arquivos de programas/Terraplanagem/relatorio/relatorioTransporte.jasper";
//			caminhoRelJasper = "D:/Workspace/Terraplanagem/relatorio/relatorioTransporte.jasper";
		}

		// Stream com o .jasper
		InputStream relJasper = null;
		try {
			relJasper = new FileInputStream(caminhoRelJasper);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// O datasource

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(itens);

		// Parametros do relatorios
		Map parametros = new HashMap();
		parametros.put("cliente", (AcoesCliente.getInstance().lerPorId(idCliente)).getNome());
		parametros.put("dataInicio", sdf.format(dtInicio));
		parametros.put("dataTermino", sdf.format(dtTermino));
		parametros.put("SUBREPORT_DIR", "C:/Arquivos de programas/Terraplanagem/relatorio/");
		parametros.put("conn", conn);
		parametros.put("dataInicioDate", timeInicio);
		parametros.put("dataTerminoDate", timeTermino);
		parametros.put("idCliente", idCliente);
		parametros.put("tdPedidos", tdPedidos);

		JasperPrint impressao = null;
		try {

			impressao = JasperFillManager.fillReport(relJasper, parametros, ds);
			JasperViewer viewer = new JasperViewer(impressao, false);
			viewer.setVisible(true);
		} catch (JRException e) {
			System.out.println(e.getMessage());
		}



	}

	public Vector<ComboBoxItem> getComboBoxItens(){
		Vector<ComboBoxItem> itens = new Vector<ComboBoxItem>();
		List<Cliente> clientes = ServicosCliente.getInstance().ler(null);
		ComboBoxItem item;
		item = new ComboBoxItem();
		item.setId(new Long(0));
		item.setLabel("Selecione...");
		itens.add(item);
		for (Cliente cliente : clientes) {
			item = new ComboBoxItem();
			item.setId(cliente.getId());
			item.setLabel(cliente.getNome());
			itens.add(item);
		}
		return(itens);
	}	
}
