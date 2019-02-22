package managers;

import java.util.ArrayList;

import classes.Client;
import classes.Place;
import classes.Reservation;
import classes.Show;
import classes.Ticket;
import enums.State;
import exceptions.ReservationsExpiredException;
import utils.MyCalendar;

/**
 * This class represent a reservation manager, an abstraction that manage reservations of a specified Client
 * @see classes.Client
 * @author Francesco Migliaro
 */
public class ReservationManager
{
	/**
	 * Initialize newly created ReservationManager with the value of the parameter
	 * @param aClient The client whose reservations are to be managed
	 */
	public ReservationManager(Client aClient)
	{
		client=aClient.clone();
		clientReservations=new ArrayList<Reservation>();
		for(int i=0; i<aClient.getNumberOfReservation(); i++)
			clientReservations.add(aClient.getReservation(i).clone());
	}
	
	/**
	 * Return the reservation specified by index
	 * @param index The index of reservation
	 * @return The reservation specified by index
	 */
	public Reservation getReservation(int index)
	{
		return clientReservations.get(index).clone();
	}
	
	/**
	 * Add a reservation to the list of the reservation of the client
	 * @param aReservation The reservation to add
	 * @param selectedShow The show of the reservation to add
	 * @return true if the reservation was added, false otherwise
	 * @throws ReservationsExpiredException Thrown when the time for reserve a place is expired (12 hours before the show)
	 */
	public boolean addReservation(Reservation aReservation, Show selectedShow) throws ReservationsExpiredException
	{
		MyCalendar currentDate=new MyCalendar(), showDateMinusTwelweHours=selectedShow.getDate();
		
		showDateMinusTwelweHours.set(MyCalendar.HOUR, selectedShow.getDate().get(MyCalendar.HOUR)-12);
		if(currentDate.before(showDateMinusTwelweHours) || aReservation.getPlace().getState()==State.SOLD)
			return clientReservations.add(aReservation.clone());
		
		throw new ReservationsExpiredException();
	}
	
	/**
	 * Replace the reservation specified by index
	 * @param index The index of the reservation that has to be replace
	 * @param aReservation The reservation to be stored
	 */
	public void setReservation(int index, Reservation aReservation)
	{
		clientReservations.set(index, aReservation.clone());
	}
	
	/**
	 * Remove a reservation from the list of the reservation of the client
	 * @param aReservation The reservation to remove
	 * @return true if the reservation was removed, false otherwise
	 */
	public boolean removeReservation(Reservation aReservation)
	{
		return clientReservations.remove(aReservation);
	}
	
	/**
	 * Buy a place that was only reserved
	 * @param aReservation The reservation to purchase
	 * @param discountsManager The discount manager of the Cinema
	 */
	public void buyReservation(Reservation aReservation, DiscountsManager discountsManager)
	{
		float price=aReservation.getShow().getPrice();
		int saleRateForClient=discountsManager.getDiscountRate(client), saleRateForShow=discountsManager.getDiscountRate(aReservation.getShow());
		
		if(saleRateForClient>=0 || saleRateForShow>=0)
			price-=saleRateForClient>saleRateForShow ? price*saleRateForClient/100 : price*saleRateForShow/100;
			
		Ticket newTicket=new Ticket(price);
		Place newPlace=aReservation.getPlace();
		
		int index=this.clientReservations.indexOf(aReservation);
		aReservation.setTicket(newTicket);
		newPlace.setState(State.SOLD);
		aReservation.setPlace(newPlace);
		if(index==-1)
			this.clientReservations.add(aReservation);
		else
			this.clientReservations.set(index, aReservation);
	}
	
	/**
	 * Return the number of the reservation of the client
	 * @return The number of the reservation of the client
	 */
	public int getNumberOfReservation()
	{
		return clientReservations.size();
	}
	
	private Client client;
	private ArrayList<Reservation> clientReservations;
}
