package testers;

import classes.Place;
import enums.State;

public class TesterPlace
{
	public static void main(String[] args)
	{
		Place place=new Place();
		Place samePlace=place.clone();
		
		System.out.println("Testo il metodo toString su place: " + place);
		System.out.println("Testo il metodo toString su samePlace: " + samePlace);
		System.out.println("\nTesto il metodo equals, valore aspettato true\nValore: " + place.equals(samePlace));
		System.out.println("\nTesto il metodo clone, valore aspettato false\nValore: " + (place==samePlace));
		
		State state;
		System.out.println("\nTesto il metodo getName, stringa aspettata \"AVALAIBLE\"\nStringa: ");
		state=place.getState();
		System.out.println(state);
		
		state=State.RESERVED;
		System.out.println("\nTesto il metodo setName");
		place.setState(state);
		System.out.println(place);
		System.out.println("Valore aspettato false\nValore: " + place.equals(samePlace));
	}
}
