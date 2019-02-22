package utils;

import java.util.GregorianCalendar;

/**
 * This class represent a mine simply version of GregorianCalendar
 * @see GregorianCalendar
 * @author Francesco Migliaro
 */
public class MyCalendar extends GregorianCalendar
{
	/**
	 * Initialize newly create MyCalendar with the current instance of date
	 * @see GregorianCalendar#GregorianCalendar()
	 */
	public MyCalendar()
	{
		super();
	}
	
	/**
	 * Initialize newly create MyCalendar with the values of the parameters
	 * @param year The year of the date
	 * @param month The month of the date
	 * @param dayOfMonth The day of the date
	 * @param hourOfDay The hour of the date
	 * @param minute The minutes of the hour
	 */
	public MyCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute)
	{
		super(year, month, dayOfMonth, hourOfDay, minute);
	}
	
	/**
	 * Return a String representing the day of week of this MyCalendar
	 * @return A String representing the day of week of this MyCalendar
	 */
	public String getDayOfWeek()
	{
		String[] days= {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		return days[get(DAY_OF_WEEK)-1];
	}
	
	/**
	 * Return a String representing the month of year of this MyCalendar
	 * @return A String representing the month of year of this MyCalendar
	 */
	public String getMonth()
	{
		String[] months= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		return months[get(MyCalendar.MONTH)];
	}
	
	/**
	 * Formats a date
	 * @param aDate The date to formats
	 * @return A string representing the date formatted
	 */
	public static String dateFormatter(MyCalendar aDate)
	{
		return aDate.getDayOfWeek() + " " + aDate.get(MyCalendar.DAY_OF_MONTH) + " " + aDate.getMonth() + " " + aDate.get(HOUR_OF_DAY) + ":" + aDate.get(MINUTE);
	}
	
	/**
	 * Parse a date in the format of {@link MyCalendar#dateFormatter(MyCalendar)} 
	 * @param string The string to parse
	 * @return The date parsed from the string
	 */
	public static MyCalendar parseDate(String string)
	{
		if(string==null || string.isEmpty())
			return null;
		
		String[] splitDate=string.split(" ");
		String[] splitSchedule=splitDate[3].split(":");
		String[] months= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int i;

		for(i=0; i<months.length; i++)
			if(months[i].equals(splitDate[2]))
				break;
		
		return new MyCalendar(getInstance().get(YEAR), i, Integer.parseInt(splitDate[1]), Integer.parseInt(splitSchedule[0]), Integer.parseInt(splitSchedule[1]));
	}
	
	/**
	 * Returns a String representing this MyCalendar and its values
	 * @see GregorianCalendar#toString()
	 * @return A String representing this MyCalendar and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[date=" + " "+getDayOfWeek() + " "+getMonth() + " "+get(MyCalendar.DAY_OF_MONTH) + " "+get(MyCalendar.HOUR_OF_DAY) + ":"+get(MyCalendar.MINUTE) + "]";
	}
	
	/**
	 * This method perform a deep copy of this MyCalendar
	 * @see GregorianCalendar#clone()
	 * @return Cloned MyCalendar
	 */
	public MyCalendar clone()
	{
		MyCalendar clone=(MyCalendar) super.clone();
		
		return clone;
	}
	
	private static final long serialVersionUID = -7734064772007270334L;
}
