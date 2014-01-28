package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import control.AcoesCliente;

public class VendasDialog extends JDialog {

	private static final long serialVersionUID = -1688675313721762585L;

	private JPanel panelToolBar;
	private JPanel panelItens;
	private JPanel panelButtons;

	private JLabel lbCliente;

	private JTextField tfCliente;

	private JButton btVendas;
	private JButton btLimpar;
	private JButton btCancelar;

	private Long id;
	private boolean tipo;

	private JScrollPane scrollPanel;	
	private JTable table;
	private DefaultTableModel tableModel;

	public VendasDialog(boolean pTipo) {
		this.setTitle("Terraplanagem São Marcos - Vendas");
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
		
		tipo = pTipo;

		panelToolBar = new JPanel();
		panelToolBar.setLayout(new BorderLayout());

		panelItens = new JPanel();
		panelItens.setLayout(new GridBagLayout());		

		lbCliente = new JLabel("Cliente:");
		cons.gridy = 0;
		cons.gridx = 0;
		panelItens.add(lbCliente, cons);

		tfCliente = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 1;
//		cons.gridwidth = 2;
		tfCliente.addKeyListener(new pesquisaClienteListener());
		panelItens.add(tfCliente, cons);

		panelToolBar.add(panelItens, BorderLayout.PAGE_START);

		panelButtons = new JPanel(new FlowLayout());

		btVendas = new JButton("Pedido");
		btVendas.addActionListener(new efetuarVendasListener(this));
		panelButtons.add(btVendas);

		btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(new limparFormularioListener());
		panelButtons.add(btLimpar);

		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new cancelarVenda(this));
		panelButtons.add(btCancelar);


		tableModel = AcoesCliente.getInstance().ler("");
		table = new JTable(tableModel){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new selecionarLinhaTabela());
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));

		setarTamanhoColunas();

		scrollPanel = new JScrollPane(table);	

		this.setLayout(new BorderLayout());
		this.add(panelToolBar, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private class cancelarVenda implements ActionListener {

		private VendasDialog dialog = null;

		public cancelarVenda(VendasDialog vendasDialog) {
			dialog = vendasDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}

	}

	private class limparFormularioListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			tfCliente.setText(null);
			id = null;
		}

	}

	private class efetuarVendasListener implements ActionListener {

		private VendasDialog dialog = null;

		public efetuarVendasListener(VendasDialog vendasDialog) {
			dialog = vendasDialog;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(id != null) {
				dialog.dispose();
				JDialog cpDialog = new PedidoDialog(id, tfCliente.getText(), tipo);
				cpDialog.setModal(true);
				cpDialog.setSize(800, 500);
				cpDialog.show(true);
			}else {
				JOptionPane.showMessageDialog(null, "Selecione um cliente.", "Alerta", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class pesquisaClienteListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {
			table.setModel(AcoesCliente.getInstance().ler(tfCliente.getText()));
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
			setarTamanhoColunas();
		}
		@Override
		public void keyTyped(KeyEvent e) {}
	}

	public void setarTamanhoColunas() {
		table.getColumnModel().getColumn(0).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);

		TableCellRenderer centerRenderer = new CenterRenderer();  
		TableColumn column = table.getColumnModel().getColumn(3);  
		column.setCellRenderer(centerRenderer);
	}

	class CenterRenderer extends DefaultTableCellRenderer {  
		private static final long serialVersionUID = 4433840270638649209L;

		public CenterRenderer() {  
			setHorizontalAlignment(CENTER);  
		}  
	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int linha = table.getSelectedRow();
			id = new Long(((String)table.getModel().getValueAt(linha, 0)));	
			tfCliente.setText((String)table.getModel().getValueAt(linha, 1));
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
