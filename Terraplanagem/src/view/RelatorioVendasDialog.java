package view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

import model.pojo.ComboBoxItem;
import control.AcoesRelatorio;

public class RelatorioVendasDialog extends JDialog {

	private static final long serialVersionUID = -8522796070706886850L;

	private JLabel lbNome;
	private JLabel lbdtInicio;
	private JLabel lbdtTermino;

	private JComboBox cbCliente;
	private JFormattedTextField tfdtInicio;
	private JFormattedTextField tfdtTermino;
	private Checkbox chbItens;

	private JButton btRelatorio;
	private JButton btCancelar;

	private JPanel panelFields;
	private JPanel panelButtons;
	private JPanel panelToolbar;

	public RelatorioVendasDialog() {
		this.setTitle("Terraplanagem São Marcos - Relatório de Vendas");

		panelToolbar = new JPanel(new BorderLayout());

		panelFields = new JPanel(new FlowLayout());

		lbNome = new JLabel("Cliente");
		panelFields.add(lbNome);

		cbCliente = new JComboBox(AcoesRelatorio.getInstance().getComboBoxItens());
		panelFields.add(cbCliente);

		lbdtInicio = new JLabel("Data Início");
		panelFields.add(lbdtInicio);

		tfdtInicio = new JFormattedTextField(setMascara("##/##/####"));
		tfdtInicio.setColumns(6);
		panelFields.add(tfdtInicio);

		lbdtTermino = new JLabel("Data Término");
		panelFields.add(lbdtTermino);

		tfdtTermino = new JFormattedTextField(setMascara("##/##/####"));
		tfdtTermino.setColumns(6);
		panelFields.add(tfdtTermino);
		
		chbItens = new Checkbox("Mostrar pedidos pagos", false);
		panelFields.add(chbItens);

		panelToolbar.add(panelFields, BorderLayout.PAGE_START);

		panelButtons = new JPanel(new FlowLayout());

		btRelatorio = new JButton("Gerar Relatório");
		btRelatorio.addActionListener(new gerarRelatorioListener(this));
		panelButtons.add(btRelatorio);

		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new cancelarRelatorioListener(this));
		panelButtons.add(btCancelar);

		this.setLayout(new BorderLayout());
		this.add(panelToolbar, BorderLayout.PAGE_START);
		this.add(panelButtons, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}
	
	private class cancelarRelatorioListener implements ActionListener {

		private RelatorioVendasDialog dialog = null;
		
		public cancelarRelatorioListener(RelatorioVendasDialog relatorioDialog) {
			dialog = relatorioDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
		
	}

	private class gerarRelatorioListener implements ActionListener {

		private RelatorioVendasDialog dialog = null;

		public gerarRelatorioListener(RelatorioVendasDialog relatorioDialog) {
			dialog = relatorioDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean data = false;
			if(((ComboBoxItem)cbCliente.getSelectedItem()).getId().equals(new Long(0))) {
				JOptionPane.showMessageDialog(null, "Selecione um cliente", "Alerta", JOptionPane.WARNING_MESSAGE);
			}else if(tfdtInicio.getText().isEmpty() || tfdtTermino.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Data Início e/ou Data Término devem ser preenchidas.", "Alerta", JOptionPane.WARNING_MESSAGE);
			}else {
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date startDate = df.parse(tfdtInicio.getText());
					Date finishDate = df.parse(tfdtTermino.getText());
					
					if(startDate.equals(finishDate)) {
						data = true;
					}else if(startDate.before(finishDate)) {
						data = true;
					}
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "teste");
				}
				if(data) {
					try {
						AcoesRelatorio.getInstance().criar(((ComboBoxItem)cbCliente.getSelectedItem()).getId(), formataData(tfdtInicio.getText()),
								formataData(tfdtTermino.getText()), chbItens.getState(), true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Data Início não pode ser depois da Data Término.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				dialog.dispose();
			}
		}
	}

	public  static java.sql.Date formataData(String data) throws  Exception {   
		if (data == null || data.equals(""))  
			return null;  

		java.sql.Date date = null;  
		try {  
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			date = new java.sql.Date( ((java.util.Date)formatter.parse(data)).getTime() );  
		} catch (ParseException e) {              
			throw e;  
		}  
		return date;  
	} 
	
	private MaskFormatter setMascara(String mascara){  
		MaskFormatter mask = null;  
		try{  
			mask = new MaskFormatter(mascara);                    
		}catch(java.text.ParseException ex){}  
		return mask;  
	}  
}
