package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import classes.Hall;
import classes.Show;
import managers.GenericManager;
import enums.State;
import enums.Type;
import utils.MyDefaultTableModel;

/**
 * This class represent a panel to interact with the Halls Manager
 * @see managers.GenericManager
 * @author Francesco Migliaro
 */
public class HallsManagerPanel extends JPanel
{
	/**
	 * Initialize newly created HallsManagerPanel that use the values of the parameters
	 * @param hallsManager The halls manager of the Cinema
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @see classes.Cinema
	 */
	public HallsManagerPanel(GenericManager<Hall> hallsManager, GenericManager<Show> weeklyProgramManager)	
	{
		super();
		String[] columns= {"Name", "Places"};
		String[][] rows=new String[hallsManager.getNumberOfElement()][2];
		
		for(int i=0; i<hallsManager.getNumberOfElement(); i++)
		{
			Hall tempHall=hallsManager.getElement(i);
			
			rows[i][0]=tempHall.getName();
			rows[i][1]=tempHall.getNumberOfPlaces()+"";
		}
		defaultTableModel=new MyDefaultTableModel(rows, columns);
		hallsTable=new JTable(defaultTableModel);
		hallsTable.getTableHeader().setReorderingAllowed(false);
		scrollPane=new JScrollPane(hallsTable);
		addHall=new JButton("Add hall");
		addHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				AddHallDialog addHallDialog=new AddHallDialog();
				
				addHallDialog.setVisible(true);
				Hall newHall=addHallDialog.getHall();
				if(newHall!=null)
				{
					hallsManager.addElement(newHall);
					defaultTableModel.addRow(new String[] {newHall.getName(), newHall.getNumberOfPlaces()+""});
					if(!manageHall.isEnabled())
						manageHall.setEnabled(true);
				}
			}
		});
		deleteHall=new JButton("Delete hall");
		deleteHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=hallsTable.getSelectedRow();
				
				if(rowIndex!=-1)
				{					
					hallsManager.removeElement(hallsManager.getElement(rowIndex));
					defaultTableModel.removeRow(rowIndex);
					if(hallsManager.thereIsNoElement())
						manageHall.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select an hall", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		manageHall=new JButton("Manage Hall");
		manageHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=hallsTable.getSelectedRow();

				if(rowIndex!=-1)
				{
					ViewHallDialog viewHallDialog=new ViewHallDialog(hallsManager.getElement(rowIndex), Type.MANAGER);
					
					viewHallDialog.setVisible(true);
					Hall hallUpdated=viewHallDialog.getHall();
					hallsManager.setElement(rowIndex, hallUpdated);
					for(int i=0; i<weeklyProgramManager.getNumberOfElement(); i++)
					{
						Show tempShow=weeklyProgramManager.getElement(i);
						
						if(tempShow.getHall().isSameHall(hallUpdated))
						{
							for(int k=0; k<hallUpdated.getNumberOfPlaces(); k++)
								if(tempShow.getHall().getPlaceAtIndex(k).getState()==State.RESERVED || tempShow.getHall().getPlaceAtIndex(k).getState()==State.SOLD)
									hallUpdated.setPlaceAtIndex(tempShow.getHall().getPlaceAtIndex(k), k);
							tempShow.setHall(hallUpdated);
							weeklyProgramManager.setElement(i, tempShow);
						}
					}
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select an hall", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		if(hallsManager.thereIsNoElement())
			manageHall.setEnabled(false);
		add(scrollPane);
		add(addHall);
		add(deleteHall);
		add(manageHall);
	}
	
	private JButton addHall, deleteHall, manageHall;
	private JTable hallsTable;
	private JScrollPane scrollPane;
	private MyDefaultTableModel defaultTableModel;
	
	private static final long serialVersionUID = -8802234527089840434L;
}
