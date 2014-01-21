package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import control.AcoesUsuario;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8473289070103167272L;
	private JPanel contentPane;
	private JPasswordField pfSenha;
	private JTextField tfUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 */
	public Login() throws UnsupportedLookAndFeelException {
		super("Terraplanagem São Marcos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 180);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		UIManager.setLookAndFeel(new MetalLookAndFeel());

		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setBounds(44, 25, 80, 20);
		contentPane.add(lblUsuario);

		tfUsuario = new JTextField();
		tfUsuario.setFont(new Font("Arial", Font.BOLD, 13));
		tfUsuario.setBounds(138, 25, 150, 20);
		contentPane.add(tfUsuario);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Arial", Font.BOLD, 13));
		lblSenha.setBounds(44, 60, 80, 20);
		contentPane.add(lblSenha);

		pfSenha = new JPasswordField();
		pfSenha.setFont(new Font("Arial", Font.BOLD, 13));
		pfSenha.setBounds(138, 60, 150, 20);
		contentPane.add(pfSenha);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = tfUsuario.getText().toString();
				String senha = pfSenha.getText().toString();
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
						dispose();
					}
				}
			}
		});
		btnLogin.setBounds(52, 112, 89, 25);
		contentPane.add(btnLogin);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		btnSair.setFont(new Font("Arial", Font.BOLD, 13));
		btnSair.setBounds(193, 112, 89, 25);
		contentPane.add(btnSair);
	}

	protected String criptografarSenha(String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		String s = hash.toString(16);
		if (s.length() % 2 != 0)
			s = "0" + s;
		return s;
	}
}
