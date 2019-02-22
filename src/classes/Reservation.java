package classes;

import java.io.Serializable;

/**
 * This class represent a Reservation
 * @author Francesco Migliaro
 */
public class Reservation implements Cloneable, Serializable
{
	/**
	 * Initialize a newly created Reservation with the values of the paramters
	 * @param aPlace The Place of the Reservation
	 * @param aShow The Show of the Reservation
	 */
	public Reservation(Place aPlace, Show aShow)
	{
		place=aPlace.clone();
		show=aShow.clone();
		ticket=null;
	}
	
	/**
	 * Return the Ticket of this Reservation
	 * @return The Ticket of this Reservation
	 */
	public Ticket getTicket()
	{
		return ticket.clone();
	}
	
	/**
	 * Return the Show of this Reservation
	 * @return The Show of this Reservation
	 */
	public Show getShow()
	{
		return show.clone();
	}
	
	/**
	 * Return the Place of this Reservation
	 * @return The Place of this Reservation
	 */
	public Place getPlace()
	{
		return place.clone();
	}
	
	/**
	 * Replace the Ticket of this Reservation with the parameter aTicket
	 * @param aTicket The Ticket to be stored
	 */
	public void setTicket(Ticket aTicket)
	{
		ticket=aTicket.clone();
	}
	
	/**
	 * Replace the Show of this Reservation with the parameter aShow
	 * @param aShow The Show to be stored
	 */
	public void setShow(Show aShow)
	{
		show=aShow.clone();
	}
	
	/**
	 * Replace the Place of this Reservation with the parameter aPlace
	 * @param aPlace The Place to be stored
	 */
	public void setPlace(Place aPlace)
	{
		place=aPlace.clone();
	}
	
	/**
	 * Returns a String representing this Reservation and its values
	 * @return A String representing this Reservation and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[ticket= " + ticket + ", show=" + show + ", place= " + place + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Reservation and the param obj
	 * @param obj Object to compare with
	 * @return true if this Reservation is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(obj.getClass()!=getClass())
			return false;
		
		Reservation otherPrenotation=(Reservation) obj;
		
		if(ticket==null || otherPrenotation.ticket==null)
			return show.equals(otherPrenotation.show) && place.equals(otherPrenotation.place);
		
		return ticket.equals(otherPrenotation.ticket) && show.equals(otherPrenotation.show) && place.equals(otherPrenotation.place);
	}
	
	/**
	 * This method perform a deep copy of this Reservation
	 * @return Cloned Reservation
	 */
	public Reservation clone()
	{
		try
		{
			Reservation clone=(Reservation) super.clone();
			
			if(ticket!=null)
				clone.ticket=ticket.clone();
			clone.show=show.clone();
			clone.place=place.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private Ticket ticket;
	private Show show;
	private Place place;
	
	static final long serialVersionUID = -8489327728953043596L;
}
