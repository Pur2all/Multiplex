package interfaces;

import classes.Film;
import utils.MyCalendar;

/**
 * This interface represent that an object can have a discount
 * @author Francesco Migliaro
 */
public interface HasDiscount 
{
	/**
	 * Return the film of the object that implements this interface
	 * @return The film of the object that implements this interface
	 */
	Film getFilm();
	
	/**
	 * Return the age of the object that implements this interface
	 * @return The age of the object that implements this interface
	 */
	int getAge();
	
	/**
	 * Return the date of the object that implements this interface
	 * @return The date of the object that implements this interface
	 */
	MyCalendar getDate();
}
