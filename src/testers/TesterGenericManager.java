package testers;

import classes.Film;
import managers.GenericManager;

public class TesterGenericManager
{
	public static void main(String[] args)
	{
		GenericManager<Film> films=new GenericManager<Film>();
		Film aFilm=new Film("Tizio", "Titolo film", "Succedono cose", 120, 2019);
		
		System.out.println("Testo il metodo add, valore aspettato true\nValore: " + films.addElement(aFilm));
		System.out.println("Testo il metodo get con incapsulamento, valore aspettato false\nValore: " + (aFilm==films.getElement(0)));
		System.out.println("Valore aspettato true\nValore: " + aFilm.equals(films.getElement(0)));
		aFilm.setDirector("Tizio a caso");
		films.setElement(0, aFilm);
		aFilm.setDirector("Tizio");
		System.out.println("Testo il metodo set con incapsulamento, stringa aspettata \"Tizio a caso\"\nStringa: " + films.getElement(0).getDirector());
	}
}
