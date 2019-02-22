package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import classes.Hall;
import classes.Place;
import classes.Reservation;
import classes.Show;
import enums.State;
import managers.GenericManager;
import managers.ReservationManager;
import managers.DiscountsManager;
import utils.MyCalendar;
import utils.MyDefaultTableModel;

/**
 * This class represent a panel to interact with the Reservations Manager
 * @see managers.ReservationManager
 * @author Francesco Migliaro
 */
public class ReservationManagerPanel extends JPanel
{
	/**
	 * Initialize a newly created ReservationManagerPanel that use the values of the parameters
	 * @param reservationManager The reservations manager of the Cinema
	 * @param discountsManager The discounts manager of the Cinema
	 * @param hallsManager The halls manager of the Cinema
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 */
	public ReservationManagerPanel(ReservationManager reservationManager, DiscountsManager discountsManager, GenericManager<Hall> hallsManager, GenericManager<Show> weeklyProgramManager)
	{
		super();
		String[] columns= {"Film", "Schedule", "Place", "State"};
		String[][] rows=new String[reservationManager.getNumberOfReservation()][4];
		
		for(int i=0; i<reservationManager.getNumberOfReservation(); i++)
		{
			rows[i][0]=reservationManager.getReservation(i).getShow().getFilm().getTitle();
			rows[i][1]=MyCalendar.dateFormatter(reservationManager.getReservation(i).getShow().getDate());
			rows[i][2]=reservationManager.getReservation(i).getShow().getHall().getName() + ": " + reservationManager.getReservation(i).getPlace().getIndex();
			rows[i][3]=reservationManager.getReservation(i).getPlace().getState()+"";
		}
		defaultTableModel=new MyDefaultTableModel(rows, columns);
		reservationTable=new JTable(defaultTableModel);
		reservationTable.getTableHeader().setReorderingAllowed(false);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane=new JScrollPane(reservationTable);
		deleteReservation=new JButton("Delete reservation");
		deleteReservation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=reservationTable.getSelectedRow();

				if(rowIndex!=-1)
				{
					updateShowsHall(State.AVALAIBLE, rowIndex, reservationManager, weeklyProgramManager);
					if(reservationManager.removeReservation(reservationManager.getReservation(rowIndex)))
						defaultTableModel.removeRow(rowIndex);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a reservation", "Error", JOptionPane.ERROR_MESSAGE);			
			}
		});
		purchaseTicket=new JButton("Purchase ticket");
		purchaseTicket.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=reservationTable.getSelectedRow();
				
				if(rowIndex!=-1)
				{
					if(defaultTableModel.getValueAt(rowIndex, 3).equals("SOLD"))
						JOptionPane.showMessageDialog(getRootPane(), "Reservation already purchased", "Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						updateShowsHall(State.SOLD, rowIndex, reservationManager, weeklyProgramManager);
						reservationManager.buyReservation(reservationManager.getReservation(rowIndex), discountsManager);
						defaultTableModel.setValueAt(State.SOLD, rowIndex, 3);
					}
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a reservation", "Error", JOptionPane.ERROR_MESSAGE);			
			}
		});
		add(scrollPane);
		add(deleteReservation);
		add(purchaseTicket);
	}
	
	/**
	 * Repaint the table of reservations
	 * @param reservationManager The reservation manager of the Cinema
	 */
	public void repaintTable(ReservationManager reservationManager)
	{
		for(int i=reservationTable.getRowCount(); i<reservationManager.getNumberOfReservation(); i++)
			addReservationToTable(reservationManager.getReservation(i));
	}
	
	/**
	 * Add a Reservation to the table of reservations
	 * @param aReservation The Reservation to add to the table
	 */
	private void addReservationToTable(Reservation aReservation)
	{
		defaultTableModel.addRow(new String[] {aReservation.getShow().getFilm().getTitle(), MyCalendar.dateFormatter(aReservation.getShow().getDate()), 
				aReservation.getShow().getHall().getName() + ": " + aReservation.getPlace().getIndex()+"", aReservation.getPlace().getState()+""});
	}
	
	/**
	 * Update the hall in other shows of the same Reservation
	 * @param aState The state of the place
	 * @param rowIndex The index of the Show
	 * @param reservationManager The reservation manager of the Cinema
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 */
	private void updateShowsHall(State aState, int rowIndex, ReservationManager reservationManager, GenericManager<Show> weeklyProgramManager)
	{
		Place tempPlace=reservationManager.getReservation(rowIndex).getPlace();
		int showIndex;
		Show tempShow;
		
		tempPlace.setState(aState);
		showIndex=weeklyProgramManager.getElementIndex(reservationManager.getReservation(rowIndex).getShow());
		Hall tempHall=weeklyProgramManager.getElement(showIndex).getHall();
		tempHall.setPlaceAtIndex(tempPlace, tempPlace.getIndex()-1);
		for(int i=0; i<reservationManager.getNumberOfReservation(); i++)
			if(reservationManager.getReservation(i).getShow().getHall().equals(weeklyProgramManager.getElement(showIndex).getHall()))
			{
				Reservation tempReservation=reservationManager.getReservation(i);
				tempShow=tempReservation.getShow();
				tempShow.setHall(tempHall);
				tempReservation.setShow(tempShow);
				reservationManager.setReservation(i, tempReservation);
			}
		tempShow=weeklyProgramManager.getElement(showIndex);
		tempShow.setHall(tempHall);
		weeklyProgramManager.setElement(showIndex, tempShow);
	}
	
	private JButton deleteReservation, purchaseTicket;
	private JTable reservationTable;
	private JScrollPane scrollPane;
	private MyDefaultTableModel defaultTableModel;
	
	private static final long serialVersionUID = -3944698861294291836L;
}
