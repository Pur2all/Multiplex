package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import classes.Film;
import classes.Hall;
import classes.Show;
import enums.State;
import managers.GenericManager;

/**
 * This class represent a dialog to view the taking of the Cinema
 * @author Francesco Migliaro
 */
public class ViewTakingDialog extends JDialog
{
	/**
	 * Instantiate newly created ViewTakingDialog that use the value of the parameters
	 * <br><strong> NOTE: </strong> If the parameter filmsManager is null this dialog will show the total taking for the current week
	 * @param filmsManager The films manager of the Cinema, can be null
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 */
	public ViewTakingDialog(GenericManager<Film> filmsManager, GenericManager<Show> weeklyProgramManager)
	{
		super();
		setTitle("View taking");
		mainPanel=new JPanel();
		if(filmsManager!=null)
		{
			if(filmsManager.thereIsNoElement())
			{
				JOptionPane.showMessageDialog(getRootPane(), "There aren't film stored, first add a film", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			filmComboBox=new JComboBox<String>();
			for(int i=0; i<filmsManager.getNumberOfElement(); i++)
				filmComboBox.addItem(filmsManager.getElement(i).getTitle());
			filmComboBox.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent actionEvent)
				{
					updateLabel(weeklyProgramManager, filmsManager);
				}
			});
			mainPanel.add(filmComboBox);
			labelTaking=new JLabel();
			updateLabel(weeklyProgramManager, filmsManager);
		}
		else
		{
			labelTaking=new JLabel("The takingg for this week is: ");
			labelTaking.setText(labelTaking.getText() + totalTaking(weeklyProgramManager, null) + "€");
		}
		mainPanel.add(labelTaking);
		add(mainPanel);
		pack();
		setModal(true);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Calculate and return the taking
	 * <br><strong> NOTE: </strong> If the parameter aFilm is null this dialog will show the total taking for the current week
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @param aFilm The film to view the taking, can be null
	 * @return The total taking for the current week or for a selected film
	 */
	private int totalTaking(GenericManager<Show> weeklyProgramManager, Film aFilm)
	{
		int total=0;
		
		for(int i=0; i<weeklyProgramManager.getNumberOfElement(); i++)
		{
			if(aFilm!=null && !weeklyProgramManager.getElement(i).getFilm().equals(aFilm))
				continue;
			
			Hall tempHall=weeklyProgramManager.getElement(i).getHall();
			int placeSold=0;
			
			for(int k=0; k<tempHall.getNumberOfPlaces(); k++)
				if(tempHall.getPlaceAtIndex(k).getState()==State.SOLD)
					placeSold++;
			total+=weeklyProgramManager.getElement(i).getPrice()*placeSold;
		}
		
		return total;		
	}
	
	/**
	 * Update the labelTaking
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 */
	private void updateLabel(GenericManager<Show> weeklyProgramManager, GenericManager<Film> filmsManager)
	{
		labelTaking.setText("The taking for " + filmComboBox.getSelectedItem() + " is: " + totalTaking(weeklyProgramManager, filmsManager.getElement(filmComboBox.getSelectedIndex())) + "€");
		pack();
	}
	
	private JPanel mainPanel;
	private JComboBox<String> filmComboBox;
	private JLabel labelTaking;
	
	
	private static final long serialVersionUID = -5063057698895185373L;
}
