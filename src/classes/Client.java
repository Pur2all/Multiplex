package classes;

import java.util.ArrayList;

import enums.Type;
import interfaces.HasDiscount;
import utils.MyCalendar;

/**
 * This class represent a Client of the Cinema
 * @see classes.Cinema
 * @author Francesco Migliaro
 */
public class Client extends User implements HasDiscount
{
	/**
	 * Initialize a newly created Client with default values
	 */
	public Client()
	{
		super(0, null, null, null, null, Type.CLIENT);
		reservations=new ArrayList<Reservation>();
	}
	
	/**
	 * Initialize a newly create Client with the values of the parameters
	 * @param anAge Age of the client
	 * @param aName Name of the client
	 * @param aSurname Surname of the client
	 * @param anEmail Email of the client
	 * @param aPassword Password of the client
	 */
	public Client(int anAge, String aName, String aSurname, String anEmail, String aPassword)
	{
		super(anAge, aName, aSurname, anEmail, aPassword, Type.CLIENT);
		reservations=new ArrayList<Reservation>();
	}
	
	/**
	 * Return the Reservation of the client at the specified position indicated by index
	 * @param index Index of the reservation
	 * @return The Reservation at the specified position
	 */
	public Reservation getReservation(int index)
	{
		return reservations.get(index).clone();
	}
	
	/**
	 * Replaces the Reservation indicated by index with the paramter aReservation
	 * @param index Index of the Reservation to be replaced
	 * @param aReservation Reservation to be stored at the specified position
	 */
	public void setReservation(int index, Reservation aReservation)
	{
		if(index>=reservations.size())
			reservations.add(aReservation);
		else
			reservations.set(index, aReservation);
	}
	
	/**
	 * Delete all reservations
	 */
	public void deleteReservations()
	{
		reservations.clear();
	}
	
	/**
	 * Return the number of reservation
	 * @return Number of reservation
	 */
	public int getNumberOfReservation()
	{
		return reservations.size();
	}
	
	/**
	 * Returns a String representing this Client and its values
	 * @see classes.User#toString()
	 * @return A String representing this Client and its values
	 */
	public String toString()
	{
		return super.toString() + "[number of reservation= " + reservations.size() + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Client and the param obj
	 * @param obj Object to compare with
	 * @see classes.User#equals(java.lang.Object)
	 * @return true if this Client is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(!super.equals(obj))
			return false;
		
		Client otherClient=(Client) obj;
		
		if(reservations.size()!=otherClient.reservations.size())
			return false;
		for(int i=0; i<reservations.size(); i++)
			if(reservations.get(i)!=otherClient.reservations.get(i))
				return false;
		
		return true;
	}
	
	/**
	 * This method perform a deep copy of this Client
	 * @see classes.User#clone()
	 * @return Cloned Client
	 */
	public Client clone()
	{
		Client clone=(Client) super.clone();
		
		clone.reservations=new ArrayList<Reservation>();
		for(int i=0; i<reservations.size(); i++)
			clone.reservations.add(reservations.get(i).clone());
		
		return clone;
	}
	
	private ArrayList<Reservation> reservations;

	private static final long serialVersionUID = -1912360346617993075L;
	
	/**
	 * @deprecated
	 */
	public Film getFilm()
	{
		return null;
	}

	/**
	 * @deprecated
	 */
	public MyCalendar getDate()
	{
		return null;
	}
}
