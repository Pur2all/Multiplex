package testers;

import classes.Client;

public class TesterClient
{
	public static void main(String[] args)
	{
		Client client=new Client(20, "Francesco", "Migliaro", "prova@gmail.com", "123456");
		Client sameClient=client.clone();
		
		System.out.println("Testo il metodo toString su client: " + client);
		System.out.println("Testo il metodo toString su sameClient: " + sameClient);
		System.out.println("\nTesto il metodo equals, valore aspettato true\nValore: " + client.equals(sameClient));
		System.out.println("\nTesto il metodo clone, valore aspettato false\nValore: " + (client==sameClient));
		
		String name;
		System.out.println("\nTesto il metodo getName, stringa aspettata \"Francesco\"\nStringa: ");
		name=client.getName();
		System.out.println(name);
		
		name="Paolo";
		System.out.println("\nTesto il metodo setName");
		client.setName(name);
		System.out.println(client);
		System.out.println("Valore aspettato false\nValore: " + client.equals(sameClient));
	}
}
