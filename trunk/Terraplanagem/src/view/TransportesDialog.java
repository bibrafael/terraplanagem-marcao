package view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

import model.pojo.Pedido;
import model.pojo.Transporte;

import control.AcoesCliente;
import control.AcoesPedido;
import control.AcoesTransporte;

public class TransportesDialog extends JDialog {

	private static final long serialVersionUID = 996251228229383373L;

	private JLabel lbDescricao;
	private JLabel lbPlaca;
	private JLabel lbQHora;
	private JLabel lbVHora;
	private JLabel lbVTotal;
	private JLabel lbCliente;

	private JTextField tfDescricao;
	private JFormattedTextField tfPlaca;
	private JTextField tfQHora;
	private JTextField tfVHora;
	private JTextField tfVTotal;
	private JTextField tfCliente;

	private JButton btAdicionar;
	private JButton btRemover;
	private JButton btFinalizar;
	private JButton btCancelar;

	private Checkbox chbPagamento;

	private Pedido pedido;

	private Long transporteId;
	private Long pedidoId;
	private Long clienteId;

	private JPanel panelToolBar;
	private JPanel panelItens;
	private JPanel panelButtons;

	private JScrollPane scrollPanel;	
	private JTable table;

	public TransportesDialog(Long idCliente, Long idPedido, boolean tipo) {
		this.setTitle("Terraplanagem São Marcos - Transportes");
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
		cons.fill = GridBagConstraints.HORIZONTAL;

		pedidoId = idPedido;
		clienteId = idCliente;

		pedido = AcoesPedido.getInstance(false).lerPorId(pedidoId);

		panelToolBar = new JPanel(new BorderLayout());

		panelItens = new JPanel(new GridBagLayout());

		lbCliente = new JLabel("Cliente");
		cons.gridy = 0;
		cons.gridx = 0;
		panelItens.add(lbCliente, cons);

		tfCliente = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 1;
		tfCliente.setText(AcoesCliente.getInstance().lerPorId(clienteId).getNome());
		tfCliente.setEnabled(false);
		panelItens.add(tfCliente);

		lbDescricao = new JLabel("Descrição");
		cons.gridy = 1;
		cons.gridx = 0;
		panelItens.add(lbDescricao, cons);

		tfDescricao = new JTextField(20);
		cons.gridy = 1;
		cons.gridx = 1;
		panelItens.add(tfDescricao, cons);

		lbPlaca = new JLabel("Placa");
		cons.gridy = 1;
		cons.gridx = 2;
		panelItens. add(lbPlaca, cons);

		tfPlaca = new JFormattedTextField(setMascara("UUU-####"));
		InputMap mapPlaca = tfPlaca.getInputMap();
		mapPlaca.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");
		mapPlaca.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 1;
		cons.gridx = 3;
		panelItens.add(tfPlaca, cons);

		lbQHora = new JLabel("Horas trabalhadas");
		cons.gridy = 2;
		cons.gridx = 0;
		panelItens.add(lbQHora, cons);

		tfQHora = new JTextField(10);
		InputMap mapQHora = tfQHora.getInputMap();
		mapQHora.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");
		mapQHora.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 2;
		cons.gridx = 1;
		tfQHora.addKeyListener(new ValorTotalListener());
		panelItens.add(tfQHora, cons);

		lbVHora = new JLabel("Valor Hora");
		cons.gridy = 2;
		cons.gridx = 2;
		panelItens.add(lbVHora, cons);

		tfVHora = new JTextField(10);
		InputMap mapVHora = tfVHora.getInputMap();
		mapVHora.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");
		mapVHora.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 2;
		cons.gridx = 3;
		tfVHora.addKeyListener(new ValorTotalListener());
		panelItens.add(tfVHora, cons);

		chbPagamento = new Checkbox("Pagamento efetuado", pedido.isPagEfetuado());
		cons.gridy = 3;
		cons.gridx = 0;
		panelItens.add(chbPagamento, cons);

		lbVTotal = new JLabel("Valor Total");
		cons.gridy = 3;
		cons.gridx = 2;
		panelItens.add(lbVTotal, cons);

		tfVTotal = new JTextField(10);
		InputMap mapVTotal = tfVTotal.getInputMap();
		mapVTotal.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");
		mapVTotal.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 3;
		cons.gridx = 3;
		panelItens.add(tfVTotal, cons);

		panelToolBar.add(panelItens, BorderLayout.PAGE_START);

		panelButtons = new JPanel(new FlowLayout());

		btAdicionar = new JButton("Adicionar");
		btAdicionar.addActionListener(new AdicionarListener());
		panelButtons.add(btAdicionar);
		btRemover = new JButton("Remover");
		btRemover.addActionListener(new DeletarTransporteListener());
		panelButtons.add(btRemover);
		btFinalizar = new JButton("Finalizar");
		btFinalizar.addActionListener(new FinalizarTransporte(this));
		panelButtons.add(btFinalizar);
		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new CancelarTransporteListener(this));
		panelButtons.add(btCancelar);

		table = new JTable(AcoesTransporte.getInstance().lerPorPedido(idPedido)) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new selecionarLinhaTabela());
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

		scrollPanel = new JScrollPane(table);

		this.setLayout(new BorderLayout());
		this.add(panelToolBar, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private class CancelarTransporteListener implements ActionListener {

		private TransportesDialog dialog = null;

		public CancelarTransporteListener(TransportesDialog transportesDialog) {
			dialog = transportesDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}

	}

	private class DeletarTransporteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				table.setModel(AcoesTransporte.getInstance().deletar(transporteId, pedidoId));
				table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
				table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Selecione um item!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class FinalizarTransporte implements ActionListener {

		private TransportesDialog dialog = null;

		public FinalizarTransporte(TransportesDialog transportesDialog) {
			dialog = transportesDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AcoesPedido.getInstance(true).atualizar(pedidoId, chbPagamento.getState(), new Double((String)table.getModel().getValueAt(table.getRowCount()-1, table.getColumnCount()+1)));
				dialog.dispose();
			}catch (Exception e1) {
				AcoesPedido.getInstance(true).atualizar(pedidoId, chbPagamento.getState(), new Double(0));
				dialog.dispose();
			}
		}

	}

	private class AdicionarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			table.setModel(AcoesTransporte.getInstance().criar(pedidoId, tfDescricao.getText(), tfPlaca.getText(), Double.parseDouble(tfQHora.getText()), 
					Double.parseDouble(tfVHora.getText()), Double.parseDouble(tfVTotal.getText())));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			tfDescricao.setText(null);
			tfPlaca.setText(null);
			tfQHora.setText(null);
			tfVHora.setText(null);
			tfVTotal.setText(null);
		}

	}

	private class ValorTotalListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {
			if(!tfQHora.getText().isEmpty() && !tfVHora.getText().isEmpty()) {
				double qtde, vhora, vtotal;
				qtde = Double.parseDouble(tfQHora.getText());
				vhora = Double.parseDouble(tfVHora.getText());
				vtotal = qtde * vhora;

				DecimalFormat format = new DecimalFormat("####.##");
				format.setMaximumFractionDigits(2);

				tfVTotal.setText(String.valueOf(format.format(vtotal).replace(',', '.')));
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0' ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyChar() == '.')) {
				e.consume();
			}
		}

	}

	private MaskFormatter setMascara(String mascara){  
		MaskFormatter mask = null;  
		try{  
			mask = new MaskFormatter(mascara);                    
		}catch(java.text.ParseException ex){}  
		return mask;  
	}  

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(table.getSelectedRow() != table.getRowCount()-1) {
				int linha = table.getSelectedRow();
				transporteId = new Long(((String)table.getModel().getValueAt(linha, 0)));
				pedidoId = new Long(((String)table.getModel().getValueAt(linha, 1)));
			}

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

}
