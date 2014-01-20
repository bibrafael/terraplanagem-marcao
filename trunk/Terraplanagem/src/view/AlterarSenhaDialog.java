package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.AcoesUsuario;

public class AlterarSenhaDialog extends JDialog {

	private static final long serialVersionUID = 8057233861601458290L;

	private JPanel panelButtons;
	private JPanel panelFields;
	private JPanel panelToolBar;
	private JTextField tfNome;
	private JPasswordField tfSenha;
	private JPasswordField tfNovaSenha;
	private JPasswordField tfRNovaSenha;
	private JButton btAlterar;
	private JButton btCancel;
	private JLabel lbNome;
	private JLabel lbSenha;
	private JLabel lbNovaSenha;
	private JLabel lbRNovaSenha;
	private Long pId;
	private String pNome;

	public AlterarSenhaDialog(Long id, String nome) {
		this.setTitle("Terraplanagem São Marcos - Alterar Senha de Usuário");
		this.setModalityType(DEFAULT_MODALITY_TYPE);

		pId = id;
		pNome = nome;

		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);

		panelToolBar = new JPanel();
		panelToolBar.setLayout(new BorderLayout());

		panelFields = new JPanel();
		panelFields.setLayout(new GridBagLayout());

		lbNome = new JLabel("Usuário:");
		cons.gridy = 0;
		cons.gridx = 0;
		panelFields.add(lbNome, cons);

		tfNome = new JTextField(20);
		cons.gridy = 0;
		cons.gridx = 1;
		tfNome.setEnabled(false);
		tfNome.setText(nome);
		panelFields.add(tfNome, cons);

		lbSenha = new JLabel("Senha atual:");
		cons.gridy = 1;
		cons.gridx = 0;
		panelFields.add(lbSenha, cons);

		tfSenha = new JPasswordField(20);
		cons.gridy = 1;
		cons.gridx = 1;
		panelFields.add(tfSenha, cons);

		lbNovaSenha = new JLabel("Nova senha:");
		cons.gridy = 2;
		cons.gridx = 0;
		panelFields.add(lbNovaSenha, cons);

		tfNovaSenha = new JPasswordField(20);
		cons.gridy = 2;
		cons.gridx = 1;
		panelFields.add(tfNovaSenha, cons);

		lbRNovaSenha = new JLabel("Repetir nova senha:");
		cons.gridy = 3;
		cons.gridx = 0;
		panelFields.add(lbRNovaSenha, cons);

		tfRNovaSenha = new JPasswordField(20);
		cons.gridy = 3;
		cons.gridx = 1;
		panelFields.add(tfRNovaSenha, cons);

		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());

		btAlterar = new JButton("Alterar Senha");
		btAlterar.addActionListener(new alterarSenhaUsuarioListener(this));
		panelButtons.add(btAlterar, cons);

		btCancel = new JButton("Cancelar");
		btCancel.addActionListener(new cancelarListener(this));
		panelButtons.add(btCancel, cons);

		panelToolBar.add(panelFields, BorderLayout.PAGE_START);
		panelToolBar.add(panelButtons, BorderLayout.PAGE_END);

		this.setLayout(new BorderLayout());
		this.add(panelToolBar);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public String criptografarSenha (String senha) throws NoSuchAlgorithmException {  
		MessageDigest md = MessageDigest.getInstance("MD5");  
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
		String s = hash.toString(16);  
		if (s.length() %2 != 0)  
			s = "0" + s;  
		return s;  
	} 

	private class alterarSenhaUsuarioListener implements ActionListener {
		
		private AlterarSenhaDialog dialog = null;

		public alterarSenhaUsuarioListener(AlterarSenhaDialog alterarSenhaDialog) {
			dialog = alterarSenhaDialog;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			String senhaAtual, novaSenha, rNovaSenha;
			senhaAtual = tfSenha.getText();
			novaSenha = tfNovaSenha.getText();
			rNovaSenha = tfRNovaSenha.getText();
			String novaSenhacripto = null;
			try {
				novaSenhacripto = criptografarSenha(novaSenha);
				senhaAtual = criptografarSenha(senhaAtual);
			} catch (NoSuchAlgorithmException e1) {}

			if(tfSenha.getText().isEmpty() || tfNovaSenha.getText().isEmpty() || tfRNovaSenha.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
			}else {
				if(AcoesUsuario.getInstance().verificarLogin(pNome, senhaAtual) != true) {
					JOptionPane.showMessageDialog(null, "Senha atual incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {
					if(senhaAtual.equals(novaSenhacripto)) {
						JOptionPane.showMessageDialog(null, "A senha atual e a nova senha não podem ser iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
					}else {
						if(!novaSenha.equals(rNovaSenha)) {
							JOptionPane.showMessageDialog(null, "A nova senha e a senha de confirmação não coincidem. Digite a mesma senha em ambas as caixas.", "Erro", JOptionPane.ERROR_MESSAGE);
						}else {
							AcoesUsuario.getInstance().atualizar(pId, pNome, novaSenhacripto);
							JOptionPane.showMessageDialog(null, "Senha alterada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							dialog.dispose();
						}
					}
				}
			}
		}
	}

	private class cancelarListener implements ActionListener {

		private AlterarSenhaDialog dialog = null;

		public cancelarListener(AlterarSenhaDialog alterarSenhaDialog) {
			dialog = alterarSenhaDialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}

	}
}
