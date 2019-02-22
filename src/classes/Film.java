package classes;

import java.io.Serializable;

import interfaces.MyCloneable;

/**
 * This class represent a Film
 * @author Francesco Migliaro
 */
public class Film implements MyCloneable<Film>, Serializable
{
	/**
	 * Initialize a newly created Film with the value of the parameters
	 * @param aDirector The director of the film
	 * @param aTitle The title of the film
	 * @param aPlot The plot of the film
	 * @param aDuration The duration of the film
	 * @param aYear The year of the release of the film
	 */
	public Film(String aDirector, String aTitle, String aPlot, int aDuration, int aYear)
	{
		director=aDirector;
		title=aTitle;
		plot=aPlot;
		duration=aDuration;
		year=aYear;
	}
	
	/**
	 * Return the director of this Film
	 * @return The director of this Film
	 */
	public String getDirector()
	{
		return director;
	}

	/**
	 * Return the title of this Film
	 * @return The title of this Film
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Return the plot of this Film
	 * @return The plot of this Film
	 */
	public String getPlot()
	{
		return plot;
	}
	
	/**
	 * Return the duration of this Film
	 * @return The duration of this Film
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * Return the year of the release of the film
	 * @return The year of the release of the film
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * Replace the director with the parameter aDirector
	 * @param aDirector The director to be stored
	 */
	public void setDirector(String aDirector)
	{
		director=aDirector;
	}
	
	/**
	 * Replace the title with the parameter aTitle
	 * @param aTitle The title to be stored
	 */
	public void setTitle(String aTitle)
	{
		title=aTitle;
	}
	
	/**
	 * Replace the plot with the parameter aPlot
	 * @param aPlot The plot to be stored
	 */
	public void setPlot(String aPlot)
	{
		plot=aPlot;
	}
	
	/**
	 * Replace the duration with the parameter aDuration
	 * @param aDuration The duration to be stored
	 */
	public void setDuration(int aDuration)
	{
		duration=aDuration;
	}
	
	/**
	 * Replace the year with the paramter aYear
	 * @param aYear The year to be stored
	 */
	public void setYear(int aYear)
	{
		year=aYear;
	}
	
	/**
	 * Returns a String representing this Film and its values
	 * @return A String representing this Film and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[director= " + director + ", title= " + title + ", plot= " + plot + ", duration= " + duration + 
				", year= " + year + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Film and the param obj
	 * @param obj Object to compare with
	 * @return true if this Film is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		Film otherFilm=(Film) obj;
		
		return director.equals(otherFilm.director) && title.equals(otherFilm.title) &&
				plot.equals(otherFilm.plot) && duration==otherFilm.duration && year==otherFilm.year;
	}
	
	/**
	 * This method perform a deep copy of this Film
	 * @return Cloned Film
	 */
	public Film clone()
	{
		try
		{
			Film clone=(Film)super.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private String director, title, plot;
	private int duration, year;
	
	private static final long serialVersionUID = -8782253309140048794L;
}
