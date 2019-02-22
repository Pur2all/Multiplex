package managers;

import java.io.Serializable;
import java.util.ArrayList;

import classes.Client;
import classes.User;
import enums.Type;

/**
 * This class represent an account manager, an abstraction that can create, log and set info about an User
 * @see classes.User
 * @author Francesco Migliaro
 */
public class AccountsManager
{
	/**
	 * Initialize newly created AccountManager with the value of the parameter
	 * @param someUsers The list of the users of the Cinema
	 */
	@SuppressWarnings("unchecked")
	public AccountsManager(Serializable someUsers)
	{
		users=(ArrayList<User>) someUsers;
		index=0;
	}
	
	/**
	 * Log a user
	 * @param email The email of the user
	 * @param password The password of the user
	 * @return The user logged if it has a match in the list of the users, null otherwise
	 */
	public final User login(String email, String password)
	{
		index=0;
		for(User u : users)
		{
			if(u.getEmail().equals(email) && u.getPassword().equals(password))
				return u.clone();
			index++;
		}
		
		return null;
	}
	
	/**
	 * Create and add to the list of the user a new user 
	 * @param anAge The age of the User
	 * @param aName The name of the User
	 * @param aSurname The surname of the User
	 * @param anEmail The email of the User
	 * @param aPassword The password of the User
	 * @param aType The type of the User
	 * @return true if the user was added to the list, false otherwise
	 */
	public boolean createUser(int anAge, String aName, String aSurname, String anEmail, String aPassword, Type aType)
	{
		User newUser;
		
		if(aType==Type.CLIENT)
			newUser=new Client(anAge, aName, aSurname, anEmail, aPassword);
		else
			newUser=new User(anAge, aName, aSurname, anEmail, aPassword, aType);
			
		
		return users.add(newUser);
	}
	
	/**
	 * Replace the user logged, it is used to save information about user 
	 * @param anUser The user to be stored
	 */
	public void setUserLogged(User anUser)
	{
		users.set(index, anUser.clone());
	}
	
	private ArrayList<User> users;
	private int index;
}
