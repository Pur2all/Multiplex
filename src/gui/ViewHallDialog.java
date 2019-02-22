package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import classes.Hall;
import classes.Place;
import enums.State;
import exceptions.PlaceUnavalaibleException;

/**
 * This class represent a dialog to intercat with the Hall's places
 * @see classes.Hall
 * @see classes.Place
 * @author Francesco Migliaro
 */
public class ViewHallDialog extends JDialog
{
	/**
	 * Instantiate newly created ViewHallDialog using the values of the parameters
	 * @param anHall The hall to view
	 * @param aType The type of the User for define operations
	 */
	public ViewHallDialog(Hall anHall, enums.Type aType)
	{
		super();
		setTitle("View Hall: " + anHall.getName());
		hall=anHall.clone();
		selectedPlaces=new ArrayList<Place>();
		mainPanel=new JPanel();
		mainPanel.setLayout(new FlowLayout());
		
		for(int i=0; i<hall.getNumberOfPlaces(); i++)
		{
			JPanel currentPlacePanel=new JPanel();
			Place currentPlace=hall.getPlaceAtIndex(i);
			final int index=i;
			
			MouseAdapter mouseAdapterClient=new MouseAdapter()
			{
				public void mouseClicked(MouseEvent mouseEvent)
				{
					Color backgroundColor=currentPlace.getBackground();

					if(backgroundColor.equals(Color.GRAY))
						try
					{
							throw new PlaceUnavalaibleException();
					} 
					catch (PlaceUnavalaibleException placeUnavalaibleException)
					{
						JOptionPane.showMessageDialog(getRootPane(), placeUnavalaibleException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					if(backgroundColor.equals(Color.GREEN))
					{
						String choose;

						Object[] chooses= {"Purchase", "Reserve"};
						choose=(String)JOptionPane.showInputDialog(rootPane, (Object)"Place avalaible, reserve or purchase?", "Choose", 
								JOptionPane.OK_OPTION, new ImageIcon(), chooses, chooses[1]);
						if(choose==null)
							;//Do nothing
						else
						{
							if(choose.equals(chooses[1]))
								currentPlace.setState(State.RESERVED);
							else
								currentPlace.setState(State.SOLD);
							currentPlace.repaint();
							hall.setPlaceAtIndex(currentPlace, index);
							selectedPlaces.add(currentPlace);
						}
					}
					if(backgroundColor.equals(Color.YELLOW))
						JOptionPane.showMessageDialog(rootPane, "Place already reserved", "Error" , JOptionPane.ERROR_MESSAGE);
					if(backgroundColor.equals(Color.RED))
						JOptionPane.showMessageDialog(rootPane, "Place already sold", "Error" , JOptionPane.ERROR_MESSAGE);
				}
			};
			
			MouseAdapter mouseAdapterManager=new MouseAdapter()
			{
				public void mouseClicked(MouseEvent event)
				{
					Color backgroundColor=currentPlace.getBackground();

					if(backgroundColor.equals(Color.GRAY))
					{
						int choose;
						
						choose=JOptionPane.showConfirmDialog(rootPane, "Place already unavalaible, do you want to restore it?", "Able/Disable place", 
								JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_CANCEL_OPTION);
						if(choose==JOptionPane.YES_OPTION)
						{
							currentPlace.setState(State.AVALAIBLE);
							currentPlace.repaint();
							hall.setPlaceAtIndex(currentPlace, index);
						}
					}	
					if(backgroundColor.equals(Color.GREEN))
					{
						int choose;

						choose=JOptionPane.showConfirmDialog(rootPane, "Place avalaible, do you want to make unavalaible it?", "Able/Disable place", 
								JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_CANCEL_OPTION);
						if(choose==JOptionPane.YES_OPTION)
						{
							currentPlace.setState(State.UNAVALAIBLE);
							currentPlace.repaint();
							hall.setPlaceAtIndex(currentPlace, index);
						}
					}
				}
			};
			if(aType==enums.Type.CLIENT)
			{
				currentPlace.removeMouseListener(currentPlace.getMouseListeners().length==0 ? null : currentPlace.getMouseListeners()[0]);
				currentPlace.addMouseListener(mouseAdapterClient);
			}
			else
				currentPlace.addMouseListener(mouseAdapterManager);
			
			currentPlacePanel.add(currentPlace);
			mainPanel.add(currentPlacePanel);
		}
		add(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(new Dimension(width, height));
		setModal(true);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the modified Hall
	 * <br><strong> NOTE: </strong> If the Hall isn't be modified this method will return null
	 * @return The modified Hall
	 */
	public Hall getHall()
	{
		if(hall==null)
			return null;
		
		return hall.clone();
	}
	
	/**
	 * Return the selected places
	 * <br><strong> NOTE: </strong> If no places aren't selected this method will return null
	 * @return The selected places
	 */
	public ArrayList<Place> getSelectedPlaces()
	{
		if(selectedPlaces==null)
			return null;
		
		return selectedPlaces;
	}
	
	private Hall hall;
	private ArrayList<Place> selectedPlaces;
	private JPanel mainPanel;
	
	private static final int width=500, height=500;
	private static final long serialVersionUID = -3420460756739390532L;
}
