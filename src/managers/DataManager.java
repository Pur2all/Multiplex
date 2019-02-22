package managers;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import classes.Film;
import classes.Hall;
import classes.Discount;
import classes.Show;
import classes.User;

/**
 * This class represent a data manager, an abstraction that save and load the data for the Cinema
 * @see classes.Cinema
 * @author Francesco Migliaro
 */
public class DataManager
{
	/**
	 * Initialize newly created DataManager with the stored info if they exists or with new if they doesn't exist
	 */
	public DataManager()
	{
		if(checkDataFile())
			loadData();
		else
		{
			users=new ArrayList<User>();
			films=new ArrayList<Film>();
			halls=new ArrayList<Hall>();
			discountPoliciesAreActive=false;
			discounts=new ArrayList<Discount>();
			weeeklyProgram=new ArrayList<Show>();
		}
	}
	
	/**
	 * Return the list of the users of the Cinema
	 * @return The list of the users of the Cinema
	 */
	public Serializable getUsers()
	{
		return users;
	}
	
	/**
	 * Return the list of the films of the Cinema
	 * @return The list of the films of the Cinema
	 */
	public Serializable getFilms()
	{
		return films;
	}
	
	/**
	 * Return the list of the halls of the Cinema
	 * @return The list of the halls of the Cinema
	 */
	public Serializable getHalls()
	{
		return halls;
	}
	
	/**
	 * Return true if the discount policy are active, false otherwise
	 * @return true if the discount policy are active, false otherwise
	 */
	public Boolean getDiscountPoliciesAreActive()
	{
		return (Boolean) discountPoliciesAreActive;
	}
	
	/**
	 * Return the list of the discounts of the Cinema
	 * @return The list of the discounts of the Cinema
	 */
	public Serializable getDiscounts()
	{
		return discounts;
	}
	
	/**
	 * Return the list of the shows of the Cinema
	 * @return The list of the shows of the Cinema
	 */
	public Serializable getWeeklyProgram()
	{
		return weeeklyProgram;
	}
	
	/**
	 * Set the value of the discount policy activation
	 * @param flag The value of the discount policy activation
	 */
	public void setDiscountPoliciesAreActive(Boolean flag)
	{
		discountPoliciesAreActive=flag;
	}
	
	/**
	 * Return true if the data file exists
	 * @return true if the data file exists, false otherwise
	 */
	public boolean checkDataFile()
	{
		File dataFile=new File(dataFilePath);
		
		return dataFile.exists();
	}
	
	/**
	 * Load the data from the file
	 */
	@SuppressWarnings("unchecked")
	public void loadData()
	{
		try
		{
			ObjectInputStream inputFile=new ObjectInputStream(new FileInputStream(dataFilePath));
			
			users=(ArrayList<User>) inputFile.readObject();
			films=(ArrayList<Film>) inputFile.readObject();
			halls=(ArrayList<Hall>) inputFile.readObject();
			discountPoliciesAreActive=(Boolean) inputFile.readObject();
			discounts=(ArrayList<Discount>) inputFile.readObject();
			weeeklyProgram=(ArrayList<Show>) inputFile.readObject();
			
			inputFile.close();
		}
		catch(EOFException eofException)
		{
			System.out.println("Empty file");
		}
		catch(ClassNotFoundException classNotFoundException)
		{
			classNotFoundException.printStackTrace();
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	/**
	 * Save the data into the file
	 */
	public void saveData()
	{
		try
		{
			ObjectOutputStream outputFile=new ObjectOutputStream(new FileOutputStream(dataFilePath));
			
			outputFile.writeObject(users);
			outputFile.writeObject(films);
			outputFile.writeObject(halls);
			outputFile.writeObject(discountPoliciesAreActive);
			outputFile.writeObject(discounts);
			outputFile.writeObject(weeeklyProgram);
			
			outputFile.close();
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	private Serializable users;
	private Serializable films;
	private Serializable halls;
	private Serializable discounts;
	private Serializable weeeklyProgram;
	private Serializable discountPoliciesAreActive;
	
	private static final String dataFilePath="./dataFile.txt";
}
