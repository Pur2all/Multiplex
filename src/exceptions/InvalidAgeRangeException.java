package exceptions;

/**
 * Thrown to indicate that an age range for a discount policy is invalid
 * @see managers.DiscountsManager
 * @author Francesco Migliaro
 */
public class InvalidAgeRangeException extends RuntimeException
{
	public InvalidAgeRangeException()
	{
		super("The age range is invalid, age range is valid when start<=end");
	}
	
	private static final long serialVersionUID = -1688047373717706744L;
}
