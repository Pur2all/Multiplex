package exceptions;

/**
 * Thrown to indicate that a Place is unavalaible
 * @see classes.Place
 * @see enums.State
 * @author Francesco Migliaro
 */
public class PlaceUnavalaibleException extends Exception
{
	public PlaceUnavalaibleException()
	{
		super("Place is unavalaible, choose another place");
	}
	
	private static final long serialVersionUID = -1296267964903895398L;
}
