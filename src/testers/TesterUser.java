package testers;

import classes.User;
import enums.Type;

public class TesterUser
{
	public static void main(String[] args)
	{
		User user=new User(20, "Francesco", "Migliaro", "prova@gmail.com", "123456", Type.MANAGER);
		User sameUser=user.clone();
		
		System.out.println("Testo il metodo toString su user: " + user);
		System.out.println("Testo il metodo toString su sameUser: " + sameUser);
		System.out.println("\nTesto il metodo equals, valore aspettato true\nValore: " + user.equals(sameUser));
		System.out.println("\nTesto il metodo clone, valore aspettato false\nValore: " + (user==sameUser));
		
		String name;
		System.out.println("\nTesto il metodo getName, stringa aspettata \"Francesco\"\nStringa: ");
		name=user.getName();
		System.out.println(name);
		
		name="Paolo";
		System.out.println("\nTesto il metodo setName");
		user.setName(name);
		System.out.println(user);
		System.out.println("Valore aspettato false\nValore: " + user.equals(sameUser));
	}
}
