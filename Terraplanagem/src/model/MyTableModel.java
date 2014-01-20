package model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Vector<Vector<Object>> pDados;
	private Vector<String> pColunas;
	private boolean DEBUG = false;

	public MyTableModel(Vector<Vector<Object>> dados, Vector<String> colunas) {
		pDados = dados;
		pColunas = colunas;
	}

	@Override
	public int getColumnCount() {
		return pColunas.size();
	}

	@Override
	public int getRowCount() {
		return pDados.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return pDados.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int col) {
		return pColunas.get(col);
	}

	@Override  
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
			return false;
	}

	public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col
					+ " to " + value
					+ " (an instance of "
					+ value.getClass() + ")");
		}

		pDados.get(row).setElementAt(value, col);
		fireTableCellUpdated(row, col);

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}

	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + pDados.get(i).get(j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}
}
