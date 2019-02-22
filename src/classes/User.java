package classes;

import java.io.Serializable;

import enums.Type;

/**
 * This class represent an User of the Cinema, User is a manager and he's able to manage the Cinema
 * @see classes.Cinema
 * @author Francesco Migliaro
 */
public class User implements Serializable, Cloneable
{
	/**
	 * Initialize a new User with the values of the parameters
	 * @param anAge The age of the User
	 * @param aName The name of the User
	 * @param aSurname The surname of the User
	 * @param anEmail The email of the User
	 * @param aPassword The password of the User
	 * @param aType The type of the User
	 */
	public User(int anAge, String aName, String aSurname, String anEmail, String aPassword, Type aType)
	{
		age=anAge;
		name=aName;
		surname=aSurname;
		email=anEmail;
		password=aPassword;
		type=aType;
	}
	
	/**
	 * Return the age of this User
	 * @return The age of this User
	 */
	public int getAge()
	{
		return age;
	}
	
	/**
	 * Return the name of this User
	 * @return The name of this User
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Return the surname of this User
	 * @return The surname of this User
	 */
	public String getSurname()
	{
		return surname;
	}
	
	/**
	 * Return the email of this User
	 * @return The email of this User
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * Return the password of this User
	 * @return The password of this User
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Return the type of this User
	 * @return The type of this User
	 */
	public Type getType()
	{
		return type;
	}
	
	/**
	 * Replace the age of this User with the parameter anAge
	 * @param anAge The age to be stored
	 */
	public void setAge(int anAge)
	{
		age=anAge;
	}
	
	/**
	 * Replace the name of this User with the parameter aName
	 * @param aName The name to be stored
	 */
	public void setName(String aName)
	{
		name=aName;
	}
	
	/**
	 * Replace the surname of this User with the parameter aSurname
	 * @param aSurname The surname to be stored
	 */
	public void setSurname(String aSurname)
	{
		surname=aSurname;
	}
	
	/**
	 * Replace the email of this User with the parameter anEmail
	 * @param anEmail The email to be stored
	 */
	public void setEmail(String anEmail)
	{
		email=anEmail;
	}
	
	/**
	 * Replace the password of this User with the paramter aPassword
	 * @param aPassword The password to be stored
	 */
	public void setPassword(String aPassword)
	{
		password=aPassword;
	}
	
	/**
	 * Returns a String representing this User and its values
	 * @return A String representing this User and its values
	 */
 	public String toString()
	{
		return getClass().getSimpleName() + "[name= " + name + ", surname=" + surname + ", age= " + age + ", email= " + email + ", type= " + type + "]";
	}
	
 	/**
	 * This method perform a deep comparison between this User and the param obj
	 * @param obj Object to compare with
	 * @return true if this User is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		User otherUser=(User) obj;
		
		return age==otherUser.age && name.equals(otherUser.name) && 
				surname.equals(otherUser.surname) && email.equals(otherUser.email) && 
				password.equals(otherUser.password) && type==otherUser.type;
	}
	
	/**
	 * This method perform a deep copy of this User
	 * @return Cloned User
	 */
	public User clone()
	{
		try
		{
			User clone=(User) super.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}

	private int age;
	private String name, surname, email, password;
	private Type type;
	
	private static final long serialVersionUID = 6329884188865631186L;
}
