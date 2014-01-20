package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Relatorio;
import model.services.ConnectionManager;

public class RelatorioDAO {

	private static RelatorioDAO instance = null;

	public static RelatorioDAO getInstance() {
		if(instance == null) {
			instance = new RelatorioDAO();
		}
		return instance;
	}

	private RelatorioDAO (){}

	public List<Relatorio> criar(Long idCliente, Date dataInicio, Date dataTermino, boolean tdPedidos, boolean tipo) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Date sqlDate = null;
		java.util.Date utilDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Relatorio> relatorios = new ArrayList<Relatorio>();
		String sql = "select * from pedido, cliente where ped_cli_id = ? and ped_data between ? and ? and ped_cli_id = cli_id and ped_tipo = ? order by ped_data asc";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			stm = conn.prepareStatement(sql);
			stm.setLong(1, idCliente);
			stm.setDate(2, dataInicio);
			stm.setDate(3, dataTermino);
			stm.setBoolean(4, tipo);
			rs = stm.executeQuery();
			while (rs.next()) {
				if(tdPedidos) {
					Relatorio aux = new Relatorio();
					aux.setNumeroPedido(rs.getLong("ped_id"));
					aux.setCliente(rs.getString("cli_nome"));
					sqlDate = rs.getDate("ped_data");
					utilDate = new java.util.Date(sqlDate.getTime());
					aux.setDataPedido(sdf.format(utilDate));
					if(rs.getBoolean("ped_pag_efetuado"))
						aux.setPagamento("Sim");
					else
						aux.setPagamento("Não");
					aux.setValorPedido(rs.getDouble("ped_valor_total"));
					aux.setLocalPedido(rs.getString("ped_local_entrega"));
					relatorios.add(aux);
				}else {
					if(!rs.getBoolean("ped_pag_efetuado")) {
						Relatorio aux = new Relatorio();
						aux.setNumeroPedido(rs.getLong("ped_id"));
						aux.setCliente(rs.getString("cli_nome"));
						sqlDate = rs.getDate("ped_data");
						utilDate = new java.util.Date(sqlDate.getTime());
						aux.setDataPedido(sdf.format(utilDate));
						if(rs.getBoolean("ped_pag_efetuado"))
							aux.setPagamento("Sim");
						else
							aux.setPagamento("Não");
						aux.setValorPedido(rs.getDouble("ped_valor_total"));
						aux.setLocalPedido(rs.getString("ped_local_entrega"));
						relatorios.add(aux);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return relatorios;
	}

}
