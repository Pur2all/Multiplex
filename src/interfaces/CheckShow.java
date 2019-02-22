package interfaces;

import classes.Show;

/**
 * This interface represent that a Show can be selected based on a criterion
 * @see classes.Show
 * @see CheckElement
 * @author Francesco Migliaro
 */
public interface CheckShow extends CheckElement<Show>
{
	/**
	 * Check if the show satisfy the criterion
	 * @param aShow The show to be examine
	 * @return true if the show satisfy the criterion, false otherwise
	 */
	boolean isSelected(Show aShow);
}
