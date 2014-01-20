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
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import model.pojo.Pedido;
import model.pojo.Produto;
import control.AcoesCliente;
import control.AcoesItemPedido;
import control.AcoesPedido;
import control.AcoesProduto;

public class ItemPedidoDialog extends JDialog {

	private static final long serialVersionUID = -6656401984941413946L;

	private Long idProduto;
	private Long idPedido;
	private Long idItemProduto;

	private JPanel panelToolBar;
	private JPanel panelButtons;
	private JPanel panelItens;

	private JLabel lbCliente;
	private JLabel lbProduto;
	private JLabel lbPUnitario;
	private JLabel lbPesoCaminhao;
	private JLabel lbQuantidade;
	private JLabel lbPTotal;
	private JLabel lbDensidade;

	private JTextField tfCliente;
	private JTextField tfProduto;
	private JTextField tfPUnitario;
	private JTextField tfPesoCaminhao;
	private JTextField tfQuantidade;
	private JTextField tfPTotal;
	private JTextField tfDensidade;

	private JButton btAdicionar;
	private JButton btRemover;
	private JButton btCancelar;
	private JButton btFinalizar;

	private Pedido pedido;

	private Checkbox chbPagamento;

	private JScrollPane scrollPanel;	
	private JTable table;

	public ItemPedidoDialog(Long clienteId, Long pedidoId, boolean flag) {
		this.setTitle("Terraplanagem São Marcos - Vendas");
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
		cons.fill = GridBagConstraints.HORIZONTAL;

		pedido = AcoesPedido.getInstance(true).lerPorId(pedidoId);

		panelToolBar = new JPanel();
		panelToolBar.setLayout(new BorderLayout());

		panelItens = new JPanel();
		panelItens.setLayout(new GridBagLayout());		
		idProduto = new Long(0);

		idPedido = pedidoId;

		lbCliente = new JLabel("Cliente:");
		cons.gridy = 0;
		cons.gridx = 0;
		cons.anchor = GridBagConstraints.LINE_START;
		panelItens.add(lbCliente, cons);

		tfCliente = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 1;
		tfCliente.setEnabled(false);
		tfCliente.setText(AcoesCliente.getInstance().lerPorId(clienteId).getNome());
		panelItens.add(tfCliente, cons);

		lbProduto = new JLabel("Produto:");
		cons.gridy = 0;
		cons.gridx = 2;
		panelItens.add(lbProduto, cons);

		tfProduto = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 3;
		tfProduto.setEnabled(false);
		tfProduto.addMouseListener(new pesquisaProdutoListener());
		panelItens.add(tfProduto, cons);

		lbDensidade = new JLabel("Densidade:");
		cons.gridy = 1;
		cons.gridx = 0;
		panelItens.add(lbDensidade, cons);

		tfDensidade = new JTextField(20);
		InputMap mapDensidade = tfDensidade.getInputMap();  
		mapDensidade.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
		mapDensidade.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 1;
		cons.gridx = 1;
		tfDensidade.addKeyListener(new pesoCaminhaoListener());
		panelItens.add(tfDensidade, cons);

		lbPesoCaminhao = new JLabel("Peso da carga:");
		cons.gridy = 1;
		cons.gridx = 2;
		panelItens.add(lbPesoCaminhao, cons);

		tfPesoCaminhao = new JTextField(10);
		InputMap mapPCaminhao = tfPesoCaminhao.getInputMap();  
		mapPCaminhao.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
		mapPCaminhao.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 1;
		cons.gridx = 3;
		tfPesoCaminhao.addKeyListener(new pesoCaminhaoListener());
		panelItens.add(tfPesoCaminhao, cons);

		lbQuantidade = new JLabel("Quantidade:");
		cons.gridy = 2;
		cons.gridx = 0;
		panelItens.add(lbQuantidade, cons);

		tfQuantidade = new JTextField(20);
		InputMap mapQtde = tfQuantidade.getInputMap();
		mapQtde.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
		mapQtde.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 2;
		cons.gridx = 1;
		tfQuantidade.addKeyListener(new TeclasNumericas());
		panelItens.add(tfQuantidade, cons);

		lbPUnitario = new JLabel("Valor unitário:");
		cons.gridy = 2;
		cons.gridx = 2;
		panelItens.add(lbPUnitario, cons);

		tfPUnitario = new JTextField(10);
		InputMap mapPUnitario = tfPUnitario.getInputMap();  
		mapPUnitario.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
		mapPUnitario.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 2;
		cons.gridx = 3;
		tfPUnitario.addKeyListener(new pesoCaminhaoListener());
		panelItens.add(tfPUnitario, cons);

		lbPTotal = new JLabel("Valor total:");
		cons.gridy = 3;
		cons.gridx = 2;
		panelItens.add(lbPTotal, cons);

		tfPTotal = new JTextField(20);
		InputMap mapPTotal = tfPTotal.getInputMap();  
		mapPTotal.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
		mapPTotal.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep"); 
		cons.gridy = 3;
		cons.gridx = 3;
		tfPTotal.addKeyListener(new teclasNumericasListener());
		panelItens.add(tfPTotal, cons);

		chbPagamento = new Checkbox("Pagamento Efetuado", pedido.isPagEfetuado());
		cons.gridy = 4;
		cons.gridx = 3;
		panelItens.add(chbPagamento, cons);

		panelToolBar.add(panelItens, BorderLayout.PAGE_START);

		panelButtons = new JPanel(new FlowLayout());

		btAdicionar = new JButton("Adicionar Produto");
		btAdicionar.addActionListener(new adicionarProdutoListener());
		panelButtons.add(btAdicionar);

		btRemover = new JButton("Remover Produto");
		btRemover.addActionListener(new removerProdutoListener());
		panelButtons.add(btRemover);

		if(flag) {
			btFinalizar = new JButton("Finalizar Pedido");
			btFinalizar.addActionListener(new finalizarPedidoListener(this));
			panelButtons.add(btFinalizar);

			btCancelar = new JButton("Cancelar Pedido");
			btCancelar.addActionListener(new cancelarPedidoListener(this));
			panelButtons.add(btCancelar);
		}else {
			btFinalizar = new JButton("Alterar Pedido");
			btFinalizar.addActionListener(new alterarPedidoListener(this));
			panelButtons.add(btFinalizar);
		}

		table = new JTable(AcoesItemPedido.getInstance().lerPorPedido(idPedido)) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}

		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new selecionarLinhaTabela());
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

		scrollPanel = new JScrollPane(table);

		this.setLayout(new BorderLayout());
		this.add(panelToolBar, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private class TeclasNumericas implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0' || e.getKeyChar() == ',' ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == 16 ||
					e.getKeyCode() == 127 || e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 46) || e.getKeyCode() == 17) {
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0' ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyChar() == '.')) {
				e.consume();
			}else {

			}
		}
	}

	private class removerProdutoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			table.setModel(AcoesItemPedido.getInstance().removerProduto(idItemProduto, idPedido));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		}

	}

	private class alterarPedidoListener implements ActionListener {

		private ItemPedidoDialog dialog = null;

		public alterarPedidoListener(ItemPedidoDialog itemPedidoDialog) {
			dialog = itemPedidoDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AcoesPedido.getInstance(true).atualizar(idPedido, chbPagamento.getState(), new Double((String)table.getModel().getValueAt(table.getRowCount()-1, 8)));
				dialog.dispose();
			}catch (Exception e1) {
				AcoesPedido.getInstance(true).atualizar(idPedido, chbPagamento.getState(), new Double(0));
				dialog.dispose();
			}
		}
	}

	private class finalizarPedidoListener implements ActionListener {

		private ItemPedidoDialog dialog = null;

		public finalizarPedidoListener(ItemPedidoDialog clienteProdutoDialog) {
			dialog = clienteProdutoDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AcoesPedido.getInstance(true).atualizar(idPedido, chbPagamento.getState(), new Double((String)table.getModel().getValueAt(table.getRowCount()-1, 8)));
				dialog.dispose();
			}catch (Exception e1) {
				AcoesPedido.getInstance(true).atualizar(idPedido, chbPagamento.getState(), new Double(0));
				dialog.dispose();
			}

		}
	}

	private class adicionarProdutoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			table.setModel(AcoesItemPedido.getInstance().criar(idPedido, idProduto, Double.parseDouble(tfQuantidade.getText()), Double.parseDouble(tfPUnitario.getText()),
					Double.parseDouble(tfPTotal.getText()), Double.parseDouble(tfPesoCaminhao.getText()), Double.parseDouble(tfDensidade.getText())));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
			tfDensidade.setText(null);
			tfPesoCaminhao.setText(null);
			tfProduto.setText(null);
			tfPTotal.setText(null);
			tfPUnitario.setText(null);
			tfQuantidade.setText(null);
			idProduto = null;
		}

	}

	private class cancelarPedidoListener implements ActionListener {

		private ItemPedidoDialog dialog = null;

		public cancelarPedidoListener(ItemPedidoDialog clienteProdutoDialog) {
			dialog = clienteProdutoDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				AcoesPedido.getInstance(true).deletar(idPedido);
				dialog.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "teste");
			}

		}
	}

	private class pesoCaminhaoListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {
			String sPeso, sPUnitario, sTotal;
			if(!tfPesoCaminhao.getText().isEmpty()) {
				sPeso = tfPesoCaminhao.getText();
				sPUnitario = tfPUnitario.getText();
				sTotal = tfPTotal.getText();
				if(sPeso.contains(",")) {
					sPeso = sPeso.replace(",", ".");
				}
				try {
					double peso = Double.parseDouble(sPeso);
					double densidade = Double.parseDouble(tfDensidade.getText());
					double quantidade = peso / densidade;

					DecimalFormat format = new DecimalFormat("####.###");
					format.setMaximumFractionDigits(3);

					tfQuantidade.setText(String.valueOf(format.format(quantidade)).replace(",", "."));

					double vTotal = Double.parseDouble(tfPUnitario.getText()) * quantidade;

					DecimalFormat format2 = new DecimalFormat("####.##");
					format2.setMaximumFractionDigits(2);

					tfPTotal.setText(String.valueOf(format2.format(vTotal)).replace(",", "."));
				}catch (Exception e1) {}
			}else {
				e.consume();
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0'  ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyChar() == '.')) {
				e.consume();
			}
		}
	}

	private class pesquisaProdutoListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			SelecionarProdutoDialog dialog = new SelecionarProdutoDialog();
			Produto produto = null;
			if(dialog.produtoSelecionado()) {
				idProduto = dialog.getID();
				produto = AcoesProduto.getInstance().getProduto(idProduto);
				tfProduto.setText(produto.getDescricao());
				tfPUnitario.setText(String.valueOf(produto.getpVista()));
				tfDensidade.setText(String.valueOf(produto.getDensidade()));
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

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(table.getSelectedRow() != table.getRowCount()-1) {
				int linha = table.getSelectedRow();
				idItemProduto = new Long(((String)table.getModel().getValueAt(linha, 0)));
				idPedido = new Long(((String)table.getModel().getValueAt(linha, 1)));
				idProduto = new Long(((String)table.getModel().getValueAt(linha, 2)));
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

	private class teclasNumericasListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0' || e.getKeyChar() == ',' ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == 16 ||
					e.getKeyCode() == 127 || e.getKeyCode() == 37 || e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 46) || e.getKeyCode() == 17) {
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			if(!(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' ||
					e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9' || e.getKeyChar() == '0' || e.getKeyChar() == ',' ||
					e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 8 || e.getKeyCode() == 27 || e.getKeyChar() == '.')) {
				e.consume();
			}
		}
	}
}
