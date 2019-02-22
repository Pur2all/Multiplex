package exceptions;

/**
 * Thrown to indicate that the Reservations are expired
 * @see managers.ReservationManager
 * @author Francesco Migliaro
 */
public class ReservationsExpiredException extends Exception
{
	public ReservationsExpiredException()
	{
		super("The time to make reservations is expired");
	}

	private static final long serialVersionUID = -6769142832088585339L;
}
