package interfaces;

/**
 * This interface is a mine version of Cloneable that works even on parametric types
 * @see Cloneable
 * @author Francesco Migliaro
 * @param <T> The type of the object to be cloned
 */
public interface MyCloneable<T> extends Cloneable
{
	/**
	 * Clone an object
	 * @return Cloned object
	 */
	T clone();
}
