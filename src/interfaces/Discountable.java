package interfaces;

import java.io.Serializable;

/**
 * This interface represent that an object that implements HasDiscount can have a discount
 * @see HasDiscount
 * @author Francesco Migliaro
 */
public interface Discountable extends Serializable
{
	/**
	 * Check if the element has a discount
	 * @param element The element to be examine
	 * @param <T> The type of the element to be examine
	 * @return true if the element has a discount, false otherwise
	 */
	public <T extends HasDiscount> boolean checkDiscount(T element);
}