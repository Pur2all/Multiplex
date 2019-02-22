package testers;

import classes.Film;

public class TesterFilm
{
	public static void main(String[] args)
	{
		Film film=new Film("Tizio", "Titolo film", "Succedono cose", 120, 2019);
		Film sameFilm=film.clone();
		
		System.out.println("Testo il metodo toString su film: " + film);
		System.out.println("Testo il metodo toString su samefilm: " + sameFilm);
		System.out.println("\nTesto il metodo equals, valore aspettato true\nValore: " + film.equals(sameFilm));
		System.out.println("\nTesto il metodo clone, valore aspettato false\nValore: " + (film==sameFilm));
		
		String title;
		System.out.println("\nTesto il metodo getName, stringa aspettata \"Titolo film\"\nStringa: ");
		title=film.getTitle();
		System.out.println(title);
		
		title="Titolo film 2";
		System.out.println("\nTesto il metodo setName");
		film.setTitle(title);
		System.out.println(film);
		System.out.println("Valore aspettato false\nValore: " + film.equals(sameFilm));
	}
}
