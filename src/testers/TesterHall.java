package testers;

import classes.Hall;

public class TesterHall
{
	public static void main(String[] args)
	{
		Hall hall=new Hall(150, "Sala 1");
		Hall sameHall=hall.clone();
		
		System.out.println("Testo il metodo toString su hall: " + hall);
		System.out.println("Testo il metodo toString su sameHall: " + sameHall);
		System.out.println("\nTesto il metodo equals, valore aspettato true\nValore: " + hall.equals(sameHall));
		System.out.println("\nTesto il metodo clone, valore aspettato false\nValore: " + (hall==sameHall));
		
		String name;
		System.out.println("\nTesto il metodo getName, stringa aspettata \"Sala 1\"\nStringa: ");
		name=hall.getName();
		System.out.println(name);
		
		name="Paolo";
		System.out.println("\nTesto il metodo setName");
		hall.setName(name);
		System.out.println(hall);
		System.out.println("Valore aspettato false\nValore: " + hall.equals(sameHall));
	}
}
