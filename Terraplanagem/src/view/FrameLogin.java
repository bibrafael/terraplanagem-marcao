package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.AcoesUsuario;

public class FrameLogin extends JFrame {

	private static final long serialVersionUID = -3461231942816557733L;

	private JPanel panelButtons;
	private JPanel panelFields;
	private JPanel panelToolBar;
	private JTextField tfNome;
	private JPasswordField tfSenha;
	private JButton btLogin;
	private JButton btCancel;
	private JLabel lbNome;
	private JLabel lbSenha;

	public FrameLogin() {
		super("Terraplanagem São Marcos");

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
		panelFields.add(tfNome, cons);

		lbSenha = new JLabel("Senha:");
		cons.gridy = 1;
		cons.gridx = 0;
		panelFields.add(lbSenha, cons);

		tfSenha = new JPasswordField(20);
		cons.gridy = 1;
		cons.gridx = 1;
		panelFields.add(tfSenha, cons);

		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());

		btLogin = new JButton("Login");
		btLogin.addActionListener(new LoginListener(this));
		btLogin.setMnemonic('L');
		panelButtons.add(btLogin, cons);

		btCancel = new JButton("Cancelar");
		btCancel.addActionListener(new CancelarListener());
		btCancel.setMnemonic('C');
		panelButtons.add(btCancel, cons);

		panelToolBar.add(panelFields, BorderLayout.PAGE_START);
		panelToolBar.add(panelButtons, BorderLayout.PAGE_END);

		this.setLayout(new BorderLayout());
		this.add(panelToolBar);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class LoginListener implements ActionListener {

		private FrameLogin frLogin = null;

		public LoginListener(FrameLogin frameLogin) {
			frLogin = frameLogin;
		}

		public void actionPerformed(ActionEvent e) {
			String nome = tfNome.getText().toString();
			String senha = tfSenha.getText().toString();
			if (nome.isEmpty() && senha.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite o usuário e a senha!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
			} else if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite o usuário!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
			} else if (senha.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Digite a senha do usuário!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
			} else if ((!nome.isEmpty()) && (!senha.isEmpty())) {
				try {
					senha = criptografarSenha(senha);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}

				boolean flag = AcoesUsuario.getInstance().verificarLogin(nome, senha);

				if (flag == false) {
					JOptionPane.showConfirmDialog(null, "Usuário ou senha incorretos!", "Alerta", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				} else {
					JFrame frame = new FramePrincipal();
					frame.setSize(600, 400);
					frame.show();
					frLogin.dispose();
				}
			}
		}
	}

	protected String criptografarSenha(String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		String s = hash.toString(16);
		if (s.length() % 2 != 0)
			s = "0" + s;
		return s;
	}

	private class CancelarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(EXIT_ON_CLOSE);
		}

	}

}
