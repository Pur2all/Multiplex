package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import classes.Film;
import managers.GenericManager;
import utils.MyDefaultTableModel;

/**
 * This class represent a panel to interact with the Films Manager
 * @see managers.GenericManager
 * @author Francesco Migliaro
 */
public class FilmsManagerPanel extends JPanel
{
	/**
	 * Initialize newly created FilmsManagerPanel that use the values of the parameter
	 * @param filmsManager The films manager of the Cinema
	 * @see classes.Cinema
	 */
	public FilmsManagerPanel(GenericManager<Film> filmsManager)
	{
		super();
		String[] columns= {"Director", "Title", "Duration", "Year"};
		String[][] rows=new String[filmsManager.getNumberOfElement()][4];
	
		for(int i=0; i<filmsManager.getNumberOfElement(); i++)
		{
			Film tempFilm=filmsManager.getElement(i);
			
			rows[i][0]=tempFilm.getDirector();
			rows[i][1]=tempFilm.getTitle();
			rows[i][2]=tempFilm.getDuration() + "";
			rows[i][3]=tempFilm.getYear() + "";
		}
		defaultTableModel=new MyDefaultTableModel(rows, columns);
		filmsTable=new JTable(defaultTableModel);
		filmsTable.getTableHeader().setReorderingAllowed(false);
		scrollPane=new JScrollPane(filmsTable);
		addFilm=new JButton("Add film");
		addFilm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				AddFilmDialog addFilmDialog=new AddFilmDialog();
				
				addFilmDialog.setVisible(true);
				Film newFilm=addFilmDialog.getFilm();
				if(newFilm!=null)
				{
					filmsManager.addElement(newFilm);
					defaultTableModel.addRow(new String[]{newFilm.getDirector(), newFilm.getTitle(), newFilm.getYear()+"", newFilm.getDuration()+""});
				}
			}
		});
		deleteFilm=new JButton("Delete film");
		deleteFilm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=filmsTable.getSelectedRow();
				
				if(rowIndex!=-1)
				{
					filmsManager.removeElement(filmsManager.getElement(rowIndex));
					defaultTableModel.removeRow(rowIndex);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a film", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(scrollPane);
		add(addFilm);
		add(deleteFilm);
	}
	
	private JButton addFilm, deleteFilm;
	private JTable filmsTable;
	private JScrollPane scrollPane;
	private MyDefaultTableModel defaultTableModel;
	
	private static final long serialVersionUID = -8654286858938112651L;
}
