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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import control.AcoesCliente;

public class ClienteDialog extends JDialog {

	private static final long serialVersionUID = -7213663864546752952L;

	private JPanel panelToolBar;
	private Long id;

	private JPanel panelItens;
	private JLabel lbNome;
	private JLabel lbEndereco;
	private JLabel lbCidade;
	private JLabel lbDocumento;
	private JLabel lbTelefone;
	private JTextField tfNome;
	private JTextField tfEndereco;
	private JTextField tfCidade;
	private JFormattedTextField tfTelefone;
	private JFormattedTextField tfDocumento;
	private JRadioButton rbCpf;
	private JRadioButton rbCnpj;
	private ButtonGroup grupo;

	private JPanel panelButtons; 	
	private JButton btCriar;
	private JButton btAlterar;
	private JButton btDeletar;
	private JButton btLimpar;

	private JScrollPane scrollPanel;	
	private JTable table;
	private DefaultTableModel tableModel;

	public ClienteDialog() {
		this.setTitle("Terraplanagem São Marcos - Cadastro de Clientes");
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);

		panelToolBar = new JPanel();
		panelToolBar.setLayout(new BorderLayout());

		panelItens = new JPanel();
		panelItens.setLayout(new GridBagLayout());		
		id = new Long(0);

		lbNome = new JLabel("Nome:");
		cons.gridy = 0;
		cons.gridx = 0;
		cons.anchor = GridBagConstraints.LINE_START;
		panelItens.add(lbNome, cons);

		tfNome = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 1;
		tfNome.addKeyListener(new pesquisaClienteListener());
		panelItens.add(tfNome, cons);

		lbTelefone = new JLabel("Telefone:");
		cons.gridy = 0;
		cons.gridx = 2;
		panelItens.add(lbTelefone, cons);

		tfTelefone = new JFormattedTextField(setMascara("(##)####-####"));
		tfTelefone.setColumns(10);
		cons.gridy = 0;
		cons.gridx = 3;
		panelItens.add(tfTelefone, cons);

		lbEndereco = new JLabel("Endereço:");
		cons.gridy = 1;
		cons.gridx = 0;
		panelItens.add(lbEndereco, cons);

		tfEndereco = new JTextField(20);
		cons.gridy = 1;
		cons.gridx = 1;
		panelItens.add(tfEndereco, cons);

		lbCidade = new JLabel("Cidade:");
		cons.gridy = 1;
		cons.gridx = 2;
		panelItens.add(lbCidade, cons);

		tfCidade = new JTextField(15);
		cons.gridy = 1;
		cons.gridx = 3;
		panelItens.add(tfCidade, cons);

		rbCpf = new JRadioButton("CPF");
		rbCpf.setActionCommand("cpf");
		rbCpf.setSelected(true);
		rbCpf.addActionListener(new documentoClienteListener());
		cons.gridy = 2;
		cons.gridx = 2;
		panelItens.add(rbCpf, cons);

		rbCnpj = new JRadioButton("CNPJ");
		rbCnpj.setActionCommand("cnpj");
		rbCnpj.addActionListener(new documentoClienteListener());
		cons.gridy = 2;
		cons.gridx = 3;
		panelItens.add(rbCnpj, cons);

		grupo = new ButtonGroup();
		grupo.add(rbCpf);
		grupo.add(rbCnpj);

		lbDocumento = new JLabel("Documento:");
		cons.gridy = 2;
		cons.gridx = 0;
		panelItens.add(lbDocumento, cons);

		tfDocumento = new JFormattedTextField(setMascara("###.###.###-##"));
		tfDocumento.setColumns(20);
		cons.gridy = 2;
		cons.gridx = 1;
		panelItens.add(tfDocumento, cons);
		
		btLimpar = new JButton("Limpar");
		cons.gridy = 3;
		cons.gridx = 1;
		btLimpar.addActionListener(new limparCliente());
		panelItens.add(btLimpar, cons);

		panelToolBar.add(panelItens, BorderLayout.PAGE_START);

		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());
		
		btCriar = new JButton("Inserir");
		btCriar.addActionListener(new criarClienteListener());
		panelButtons.add(btCriar);
		

		btAlterar = new JButton("Alterar");
		btAlterar.addActionListener(new alterarClienteListener());
		panelButtons.add(btAlterar);				

		btDeletar = new JButton("Deletar");
		btDeletar.addActionListener(new deletarClienteListener());
		panelButtons.add(btDeletar);		

		//Montando a tabela
		tableModel = AcoesCliente.getInstance().ler("");
		table = new JTable(tableModel){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new selecionarLinhaTabela());
		table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
		setarTamanhoColunas();

		scrollPanel = new JScrollPane(table);		

		//Mostrando o Frame
		this.setLayout(new BorderLayout());
		this.add(panelToolBar, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private class limparCliente implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			tfNome.setText(null);
			tfEndereco.setText(null);
			tfCidade.setText(null);
			tfDocumento.setText(null);
			tfTelefone.setText(null);
			id = null;
			table.setModel(AcoesCliente.getInstance().ler(tfNome.getText()));
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
			setarTamanhoColunas();
		}
	}

	private class pesquisaClienteListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {
			table.setModel(AcoesCliente.getInstance().ler(tfNome.getText()));
			table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
			setarTamanhoColunas();
			tfEndereco.setText(null);
			tfCidade.setText(null);
			tfDocumento.setText(null);
			tfTelefone.setText(null);
			id = null;
		}
		@Override
		public void keyTyped(KeyEvent e) {}

	}

	class CenterRenderer extends DefaultTableCellRenderer {  
		private static final long serialVersionUID = 4433840270638649209L;

		public CenterRenderer() {  
			setHorizontalAlignment(CENTER);  
		}  
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

	private class criarClienteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tfNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite o nome do cliente!", "Alerta", JOptionPane.WARNING_MESSAGE);
			}else {
				table.setModel(AcoesCliente.getInstance().criar(tfNome.getText(), tfEndereco.getText(), tfCidade.getText(), tfDocumento.getText(), tfTelefone.getText()));
				table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
				setarTamanhoColunas();
				tfNome.setText(null);
				tfEndereco.setText(null);
				tfCidade.setText(null);
				tfDocumento.setText(null);
				tfTelefone.setText(null);
				id = null;
				JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	private class alterarClienteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tfNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite o nome do cliente!", "Alerta", JOptionPane.WARNING_MESSAGE);
			}else {
				table.setModel(AcoesCliente.getInstance().atualizar(id, tfNome.getText(), tfEndereco.getText(), tfCidade.getText(), tfDocumento.getText(), tfTelefone.getText()));
				table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
				setarTamanhoColunas();
				tfNome.setText(null);
				tfEndereco.setText(null);
				tfCidade.setText(null);
				tfDocumento.setText(null);
				tfTelefone.setText(null);
				id = null;
			}
		}

	}

	private class deletarClienteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(id == null) {
				JOptionPane.showMessageDialog(null, "Selecione um usuário para deletar!", "Erro", JOptionPane.ERROR_MESSAGE);
			}else {
				Object[] options = { "Sim", "Não" };   
				int sd =   
					JOptionPane.showOptionDialog(   
							null,   
							"Deseja deletar cliente?",   
							"Alerta",   
							JOptionPane.DEFAULT_OPTION,   
							JOptionPane.WARNING_MESSAGE,   
							null,   
							options,   
							options[0]);  
				if (sd == 0) {   
					table.setModel(AcoesCliente.getInstance().deletar(id));
					table.getColumnModel().removeColumn( table.getColumnModel().getColumn(0));
					setarTamanhoColunas();
					tfNome.setText(null);
					tfEndereco.setText(null);
					tfCidade.setText(null);
					tfDocumento.setText(null);
					tfTelefone.setText(null);
					id = null;
				}else {}
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

	private class documentoClienteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(grupo.getSelection().getActionCommand().equals("cnpj")) {
				DefaultFormatterFactory factory = new DefaultFormatterFactory(setMascara("##.###.###/####-##"));
				tfDocumento.setFormatterFactory(factory);
			}else {
				DefaultFormatterFactory factory = new DefaultFormatterFactory(setMascara("###.###.###-##"));
				tfDocumento.setFormatterFactory(factory);
			}
		}
	}

	private class selecionarLinhaTabela implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int linha = table.getSelectedRow();
			id = new Long(((String)table.getModel().getValueAt(linha, 0)));	
			tfNome.setText((String)table.getModel().getValueAt(linha, 1));
			if(table.getModel().getValueAt(linha, 2).toString().length() == 14) {
				rbCpf.setSelected(true);
				DefaultFormatterFactory factory = new DefaultFormatterFactory(setMascara("###.###.###-##"));
				tfDocumento.setFormatterFactory(factory);
			}else if(table.getModel().getValueAt(linha, 2).toString().length() == 18) {
				rbCnpj.setSelected(true);
				DefaultFormatterFactory factory = new DefaultFormatterFactory(setMascara("##.###.###/####-##"));
				tfDocumento.setFormatterFactory(factory);
			}
			tfDocumento.setText((String)table.getModel().getValueAt(linha, 2));
			tfEndereco.setText((String)table.getModel().getValueAt(linha, 3));
			tfCidade.setText((String)table.getModel().getValueAt(linha, 4));
			tfTelefone.setText((String)table.getModel().getValueAt(linha, 5));

		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}