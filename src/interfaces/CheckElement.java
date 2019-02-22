package interfaces;

/**
 * This interface represent that an object can be selected based on a criterion
 * @author Francesco Migliaro
 * @param <T> The type of objects that may be selected
 */
public interface CheckElement<T>
{
	/**
	 * Check if the element satisfy the criterion
	 * @param element The element to be examine
	 * @return true if the element satisfy the criterion, false otherwise
	 */
	boolean isSelected(T element);
}
