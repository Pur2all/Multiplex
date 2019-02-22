package classes;

import java.io.Serializable;

import interfaces.MyCloneable;

/**
 * This class represent an Hall
 * @author Francesco Migliaro
 */
public class Hall implements MyCloneable<Hall>, Serializable
{
	/**
	 * Initialize a newly created Hall with the value of the parameters
	 * @param numberOfPlaces Number of places of the hall
	 * @param aName Name of the hall
	 */
	public Hall(int numberOfPlaces, String aName)
	{
		places=new Place[numberOfPlaces];
		for(int i=0; i<numberOfPlaces; i++)
		{
			places[i]=new Place();
			places[i].setIndex(i+1);
		}
		name=aName;
	}
	
	/**
	 * Return the name of this Hall
	 * @return The name of this Hall
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Replace the name of this Hall with the parameter aName
	 * @param aName The name to be stored
	 */
	public void setName(String aName)
	{
		name=aName;
	}
	
	/**
	 * Return the number of places of this Hall
	 * @return The number of places of this Hall
	 */
	public int getNumberOfPlaces()
	{
		return places.length;
	}
	
	/**
	 * Return the Place specified by index
	 * @param index The index of the Place
	 * @return The Place specified by index
	 */
	public Place getPlaceAtIndex(int index)
	{
		return places[index].clone();
	}
	
	/**
	 * Replace the Place specified by index with the parameter aPlace
	 * @param aPlace The place to be stored
	 * @param index The index of the place to replace
	 */
	public void setPlaceAtIndex(Place aPlace, int index)
	{
		places[index]=aPlace.clone();
	}
	
	/**
	 * Perform a comparison of this Hall with the parameter anHall without taking any importance to places
	 * @param anHall The Hall to compare with this Hall
	 * @return true if this Hall is the same Hall of anHall, false otherwise
	 */
	public boolean isSameHall(Hall anHall)
	{
		return name.equals(anHall.name) && places.length==anHall.places.length;
	}
	
	/**
	 * Returns a String representing this Hall and its values
	 * @return A String representing this Hall and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[name= " + name + ", places= " + places.length + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Hall and the param obj
	 * @param obj Object to compare with
	 * @return true if this Hall is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		Hall otherHall=(Hall) obj;
		int i;
		
		if(places.length!=otherHall.places.length)
			return false;
		
		for(i=0; i<places.length; i++)
			if(!places[i].equals(otherHall.places[i]))
				break;
		
		return name.equals(otherHall.name) && i==places.length;
	}
	
	/**
	 * This method perform a deep copy of this Hall
	 * @return Cloned Hall
	 */
	public Hall clone()
	{
		try
		{
			Hall clone=(Hall) super.clone();
			
			clone.places=new Place[places.length];
			for(int i=0; i<places.length; i++)
				clone.places[i]=places[i].clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private Place[] places;
	private String name;
	
	private static final long serialVersionUID = -6679121940606548739L;
}
