package classes;

import java.io.Serializable;

/**
 * This class represent a Ticket
 * @author Francesco Migliaro
 */
public class Ticket implements Cloneable, Serializable
{
	/**
	 * Initialize a newly created Ticket with the value of the parameter
	 * @param aPrice The price of the Ticket
	 */
	public Ticket(float aPrice)
	{
		price=aPrice;
	}
	
	/**
	 * Return the price of this Ticket
	 * @return The price of this Ticket
	 */
	public float getPrice()
	{
		return price;
	}
	
	/**
	 * Returns a String representing this Ticket and its values
	 * @return A String representing this Ticket and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[price= " + price + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Ticket and the param obj
	 * @param obj Object to compare with
	 * @return true if this Ticket is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(obj.getClass()!=getClass())
			return false;
		
		Ticket otherTicket=(Ticket) obj;
		
		return this.price==otherTicket.price;
	}
	
	/**
	 * This method perform a deep copy of this Ticket
	 * @return Cloned Ticket
	 */
	public Ticket clone()
	{
		try
		{
			Ticket clone=(Ticket)super.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private float price;

	private static final long serialVersionUID = -2408269722420349969L;
}
