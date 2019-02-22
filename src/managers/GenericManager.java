package managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import classes.Hall;
import interfaces.CheckElement;
import interfaces.MyCloneable;

/**
 * This class represent a generic manager, an abstraction that manage a list of every type of object
 * @author Francesco Migliaro
 * @param <T> The type of objects that may be managed
 */
public class GenericManager<T extends MyCloneable<T>>
{
	/**
	 * Initialize newly created GenericManager with the value of the parameter
	 * @param aList The list of the objects that has to be managed
	 */
	@SuppressWarnings("unchecked")
	public GenericManager(Serializable aList)
	{
		list=(ArrayList<T>) aList;
	}
	
	/**
	 * Initialize newly created GenericManager with the default value
	 */
	public GenericManager()
	{
		list=new ArrayList<T>();
	}
	
	/**
	 * Add element to the elements list
	 * @param element The element to add
	 * @return true if the element was added, false otherwise
	 */
	public boolean addElement(T element)
	{
		return list.add((T)element.clone());
	}
	
	/**
	 * Remove element to the elements list
	 * @param element The element to remove
	 * @return true if the element was removed, false otherwise
	 */
	public boolean removeElement(T element)
	{
		return list.remove(element);
	}
	
	/**
	 * Return the state of the list (empty or not)
	 * @return true if the list is empty, false otherwise
	 */
	public boolean thereIsNoElement()
	{
		return list.isEmpty();
	}
	
	/**
	 * Return the element specified by index
	 * @param index The index of the element
	 * @return The element specified by index
	 */
	public T getElement(int index)
	{
		return list.get(index).clone();
	}
	
	/**
	 * Replace the element in the specified position indicated by index
	 * @param index The position of the element
	 * @param element The element to be stored
	 */
	public void setElement(int index, T element)
	{
		list.set(index, element.clone());
	}
	
	/**
	 * Return the number of the elements in the list
	 * @return The number of the elements in the list
	 */
	public int getNumberOfElement()
	{
		return list.size();
	}
	
	/**
	 * Check if the element is present in the list
	 * @param element The element to check
	 * @return true if the element is present in the list, false otherwise
	 */
	public boolean thereIsElement(T element)
	{
		return list.contains(element);
	}
	
	/**
	 * Return the index of the element
	 * @param element the element whose index is to be known
	 * @return The index of the element if it is present in the list, -1 otherwise
	 */
	public int getElementIndex(T element)
	{
		if(element.getClass()==Hall.class)
		{
			for(int index=0; index<list.size(); index++)
			{
				Hall tempHall=(Hall) list.get(index);
				if(tempHall.isSameHall((Hall)element))
					return index;
			}
			
			return -1;
		}
		
		return list.indexOf(element);
	}
	
	/**
	 * Sort the elements based on a criterion specified by comparator
	 * @param comparator The criterion for sort the list
	 */
	public void sort(Comparator<T> comparator)
	{
		list.sort(comparator);
	}
	
	/**
	 * Return a sublist of the elements that satisfy the specified condition
	 * @param checkElement The condition that the elements had to satisfy
	 * @return A sublist of the elements that satisfy the specified condition
	 */
	public GenericManager<T> select(CheckElement<T> checkElement)
	{
		GenericManager<T> selected=new GenericManager<T>();
		
		for(T element : list)
			if(checkElement.isSelected(element))
				selected.addElement(element.clone());
		
		return selected;
	}
	
	private ArrayList<T> list;
}
