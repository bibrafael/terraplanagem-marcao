package view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import control.AcoesPedido;

public class PedidoDialog extends JDialog {

	private static final long serialVersionUID = -1323519338356268915L;

	private JPanel panelToolBar;
	private JPanel panelItens;
	private JPanel panelButtons;

	private JLabel lbCliente;
	private JLabel lbData;
	private JLabel lbLocalEntrega;

	private JTextField tfCliente;
	private JFormattedTextField tfData;
	private JTextField tfLocalEntrega;

	private JButton btNovoPedido;
	private JButton btCancelar;
	private JButton btVerPedido;
	private JButton btDeletar;

	private Long clienteId;
	private Long pedidoId;

	private JScrollPane scrollPanel;	
	private JTable table;

	private boolean pTipo = false;

	public PedidoDialog(Long idCliente, String cliente, boolean tipo) {
		this.setTitle("Terraplanagem São Marcos - Pedidos");
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 1, 5, 1);

		clienteId = idCliente;
		pTipo = tipo;

		panelToolBar = new JPanel();
		panelToolBar.setLayout(new BorderLayout());

		panelItens = new JPanel();
		panelItens.setLayout(new FlowLayout());		

		lbCliente = new JLabel("Cliente:");
		panelItens.add(lbCliente);

		tfCliente = new JTextField();
		tfCliente.setText(cliente);
		tfCliente.setEnabled(false);
		panelItens.add(tfCliente);

		lbData = new JLabel("Data:");
		panelItens.add(lbData);

		tfData = new JFormattedTextField(setMascara("##/##/####"));
		tfData.setColumns(6);
		panelItens.add(tfData);

		if(pTipo) {
			lbLocalEntrega = new JLabel("Local de Entrega");
			panelItens.add(lbLocalEntrega);

			tfLocalEntrega = new JTextField(20);
			panelItens.add(tfLocalEntrega);
		}else {
			tfLocalEntrega = new JTextField();
		}

		panelToolBar.add(panelItens, BorderLayout.PAGE_START);

		panelButtons = new JPanel(new FlowLayout());

		btVerPedido = new JButton("Ver Pedido");
		btVerPedido.addActionListener(new verPedidoListener(this));
		panelButtons.add(btVerPedido);

		btNovoPedido = new JButton("Novo Pedido");
		btNovoPedido.addActionListener(new novoPedidoListener(this));
		panelButtons.add(btNovoPedido);

		btDeletar = new JButton("Deletar Pedido");
		btDeletar.addActionListener(new DeletarPedidoListener());
		panelButtons.add(btDeletar);

		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new cancelarPedidoListener(this));
		panelButtons.add(btCancelar);


		table = new JTable(AcoesPedido.getInstance(tipo).ler(idCliente, tipo));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new selecionarLinhaTabela());
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(1));
		setarTamanhoColunas();

		scrollPanel = new JScrollPane(table);	

		this.setLayout(new BorderLayout());
		this.add(panelToolBar, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	private class DeletarPedidoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if(pedidoId != null) {
					AcoesPedido.getInstance(pTipo).deletar(pedidoId);
					table.setModel(AcoesPedido.getInstance(pTipo).ler(clienteId, pTipo));
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.addMouseListener(new selecionarLinhaTabela());
					table.getColumnModel().removeColumn(table.getColumnModel().getColumn(1));
					setarTamanhoColunas();
					pedidoId = null;
				}else {
					JOptionPane.showMessageDialog(null, "É preciso selecionar um pedido para ser deletado!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Não é possível deletar um pedido com itens cadastrados!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class verPedidoListener implements ActionListener {

		private PedidoDialog dialog = null;

		public verPedidoListener(PedidoDialog pedidoDialog) {
			dialog = pedidoDialog;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(pTipo) {
				if(clienteId != null && pedidoId != null) {
					dialog.dispose();
					JDialog itDialog = new ItemPedidoDialog(clienteId, pedidoId, false);
					itDialog.setModal(true);
					itDialog.setSize(800, 500);
					itDialog.show(true);
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um pedido!", "Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				if(clienteId != null && pedidoId != null) {
					dialog.dispose();
					JDialog tDialog = new TransportesDialog(clienteId, pedidoId, false);
					tDialog.setModal(true);
					tDialog.setSize(800, 500);
					tDialog.show(true);
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um pedido!", "Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

	}

	private class novoPedidoListener implements ActionListener {

		private PedidoDialog dialog = null;

		public novoPedidoListener(PedidoDialog pedidoDialog) {
			dialog = pedidoDialog;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(pTipo) {
				if(clienteId != null) {
					try {
						AcoesPedido.getInstance(pTipo).criar(clienteId, formataData(tfData.getText()), tfLocalEntrega.getText(), pTipo);
						dialog.dispose();
						pedidoId = AcoesPedido.getInstance(pTipo).getIdPedido();
						JDialog cpDialog = new ItemPedidoDialog(clienteId, pedidoId, true);
						cpDialog.setModal(true);
						cpDialog.setSize(800, 500);
						cpDialog.show(true);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "Data inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente.", "Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				try {
					AcoesPedido.getInstance(pTipo).criar(clienteId, formataData(tfData.getText()), tfLocalEntrega.getText(), pTipo);
					dialog.dispose();
					pedidoId = AcoesPedido.getInstance(pTipo).getIdPedido();
					JDialog cpDialog = new TransportesDialog(clienteId, pedidoId, false);
					cpDialog.setModal(true);
					cpDialog.setSize(800, 500);
					cpDialog.show(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Data inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public  static java.sql.Date formataData(String data) throws  Exception {   
		if (data == null || data.equals(""))  
			return null;  

		java.sql.Date date = null;  
		try {  
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			formatter.setLenient(false);
			date = new java.sql.Date( ((java.util.Date)formatter.parse(data)).getTime() );  
		} catch (ParseException e) {              
			throw e;  
		}  
		return date;  
	}  

	public void setarTamanhoColunas() {
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(175);

		TableCellRenderer centerRenderer = new CenterRenderer();  
		TableColumn column = table.getColumnModel().getColumn(0);  
		column.setCellRenderer(centerRenderer);
	}

	class CenterRenderer extends DefaultTableCellRenderer {  
		private static final long serialVersionUID = 4433840270638649209L;

		public CenterRenderer() {  
			setHorizontalAlignment(CENTER);  
		}  
	}

	private class cancelarPedidoListener implements ActionListener {

		private PedidoDialog dialog = null;

		public cancelarPedidoListener(PedidoDialog pedidoDialog) {
			dialog = pedidoDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}

	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int linha = table.getSelectedRow();
			pedidoId = new Long(((String)table.getModel().getValueAt(linha, 0)));
			clienteId = new Long(((String)table.getModel().getValueAt(linha, 1)));
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	private MaskFormatter setMascara(String mascara){  
		MaskFormatter mask = null;  
		try{  
			mask = new MaskFormatter(mascara);                    
		}catch(java.text.ParseException ex){}  
		return mask; 
	}

}
