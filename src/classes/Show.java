package classes;

import java.io.Serializable;

import interfaces.HasDiscount;
import interfaces.MyCloneable;
import utils.MyCalendar;

/**
 * This class represent a Show
 * @author Francesco Migliaro
 */
public class Show implements MyCloneable<Show>, Serializable, HasDiscount
{
	/**
	 * Initialize a newly created Show with the values of the parameters
	 * @param anHall The Hall of the Show
	 * @param aFilm The Film of the Show
	 * @param aDate The Date of the Show
	 * @param aPrice The price of the Show
	 */
	public Show(Hall anHall, Film aFilm, MyCalendar aDate, float aPrice)
	{
		hall=anHall.clone();
		film=aFilm.clone();
		date=aDate.clone();
		price=aPrice;
	}
	
	/**
	 * Return the Hall of this Show
	 * @return The Hall of this Show
	 */
	public Hall getHall()
	{
		return hall.clone();
	}
	
	/**
	 * Return the Film of this Show
	 * @return The Film of this Show
	 */
	public Film getFilm()
	{
		return film.clone();
	}
	
	/**
	 * Return the Date of this Show
	 * @return The Date of this Show
	 */
	public MyCalendar getDate()
	{
		return date.clone();
	}
	
	/**
	 * Return the price of this Show
	 * @return The price of this Show
	 */
	public float getPrice()
	{
		return price;
	}
	
	/**
	 * Replace the Hall of this Show with the parameter anHall
	 * @param anHall The Hall to be stored
	 */
	public void setHall(Hall anHall)
	{
		hall=anHall.clone();
	}
	
	/**
	 * Replace the Film of this Show with the parameter aFilm
	 * @param aFilm The Film to be stored
	 */
	public void setFilm(Film aFilm)
	{
		film=aFilm.clone();
	}
	
	/**
	 * Replace the Date of this Show with the parameter aDate
	 * @param aDate The Date to be stored
	 */
	public void setDate(MyCalendar aDate)
	{
		date=aDate.clone();
	}
	
	/**
	 * Replace the price of this Show with the parameter aPrice
	 * @param aPrice The price to be stored
	 */
	public void setPrice(float aPrice)
	{
		price=aPrice;
	}
	
	/**
	 * Check if the Show is of this week
	 * @return true if this Show is of this week, false otherwise
	 */
	public boolean isOfThisWeek()
	{
		MyCalendar currentDate=new MyCalendar(), firstDayOfThisWeek=new MyCalendar(), lastDayOfThisWeek=new MyCalendar();
		
		firstDayOfThisWeek.set(MyCalendar.DATE, currentDate.get(MyCalendar.DAY_OF_MONTH)-currentDate.get(MyCalendar.DAY_OF_WEEK));
		lastDayOfThisWeek.set(MyCalendar.DATE, currentDate.get(MyCalendar.DAY_OF_MONTH)+7-currentDate.get(MyCalendar.DAY_OF_WEEK));
		
		return date.before(lastDayOfThisWeek) && date.after(firstDayOfThisWeek);
	}
	
	/**
	 * Check if the Show is usable, an usable Show is a Show that it's scheduled after current date
	 * @return true if this Show is usable, false otherwise
	 */
	public boolean isUsable()
	{
		MyCalendar currentDate=new MyCalendar(), lastDayOfThisWeek=new MyCalendar();
		
		lastDayOfThisWeek.set(MyCalendar.DATE, currentDate.get(MyCalendar.DAY_OF_MONTH)+7-currentDate.get(MyCalendar.DAY_OF_WEEK));
		
		return date.after(currentDate) && date.before(lastDayOfThisWeek);
	}
	
	/**
	 * Returns a String representing this Show and its values
	 * @return A String representing this Show and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[hall= " + hall + ", film= " + film + ", date= " + date + ", price= " + price +"]";
	}
	
	/**
	 * This method perform a deep comparison between this Show and the param obj
	 * @param obj Object to compare with
	 * @return true if this Show is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		Show otherShow=(Show) obj;
		
		return hall.equals(otherShow.hall) && film.equals(otherShow.film) && date.equals(otherShow.date) && price==otherShow.price;
	}
	
	/**
	 * This method perform a deep copy of this Show
	 * @return Cloned Show
	 */
	public Show clone()
	{
		try
		{
			Show clone=(Show) super.clone();
			
			clone.film=film.clone();
			clone.hall=hall.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private Hall hall;
	private Film film;
	private MyCalendar date;
	private float price;
	
	private static final long serialVersionUID = -4208450515550970938L;

	/**
	 * @deprecated
	 */
	public int getAge()
	{
		return 0;
	}
}
