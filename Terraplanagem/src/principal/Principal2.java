package principal;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import view.Login;

public class Principal2 {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame dialog;
		try {
			dialog = new Login();
			dialog.show(true);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// JDialog dialog = new RelatorioDialog();
		// dialog.setModal(true);
		// dialog.setSize(800, 600);
		// dialog.pack();
	}
}
