package utils;

import javax.swing.table.DefaultTableModel;

/**
 * This class represent a mine simply version of DefaultTableModel
 * @see DefaultTableModel
 * @author Francesco Migliaro
 */
public class MyDefaultTableModel extends DefaultTableModel
{
	/**
	 * Initialize newly created MyDefaultTableModel with the values of the parameters
	 * @param rows The rows to add to the table model
	 * @param columns The columns to add to the table model
	 * @see DefaultTableModel#DefaultTableModel(Object[][], Object[])
	 */
	public MyDefaultTableModel(String[][] rows, String[] columns)
	{
		super(rows, columns);
	}
	
	/**
	 * Set the cells to not editable
	 */
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	private static final long serialVersionUID = -1962000240768717172L;
}
