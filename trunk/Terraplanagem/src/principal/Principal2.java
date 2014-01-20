package principal;
import javax.swing.JFrame;

import view.FrameLogin;



public class Principal2 {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame dialog = new FrameLogin();
//		JDialog dialog = new RelatorioDialog();
//		dialog.setModal(true);
//		dialog.setSize(800, 600);
		dialog.pack();
		dialog.show(true);
	}
}
