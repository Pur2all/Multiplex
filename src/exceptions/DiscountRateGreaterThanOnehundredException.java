package exceptions;

/**
 * Thrown to indicate that a discount rate is greater than one hundred
 * @see classes.Discount
 * @author Francesco Migliaro
 */
public class DiscountRateGreaterThanOnehundredException extends Exception
{
	public DiscountRateGreaterThanOnehundredException()
	{
		super("Sale rate is greater than 100");
	}

	private static final long serialVersionUID = -1751755911967043936L;
}
