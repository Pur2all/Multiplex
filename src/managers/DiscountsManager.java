package managers;

import java.io.Serializable;
import java.util.ArrayList;

import classes.Client;
import classes.Film;
import classes.Discount;
import classes.Show;
import exceptions.InvalidAgeRangeException;
import interfaces.Discountable;
import interfaces.HasDiscount;
import utils.MyCalendar;

/**
 * This class represent a discount manager, an abstraction that manage discounts
 * @see classes.Discount
 * @author Francesco Migliaro
 */
public class DiscountsManager implements Serializable
{
	/**
	 * Initialize newly created DiscountsManager with the value of the parameters
	 * @param someDiscounts The list of the discounts of the Cinema
	 * @param salesIsActive The state of the activation of the discount policy of the Cinema
	 */
	@SuppressWarnings("unchecked")
	public DiscountsManager(Serializable someDiscounts, Boolean salesIsActive)
	{
		discounts=(ArrayList<Discount>) someDiscounts;
		isActive=salesIsActive;
	}

	/**
	 * Create a new discount policy based on an age range
	 * @param initAgeRange The start of the range
	 * @param finalAgeRange The end of the range
	 * @return The created discount policy
	 */
	public Discountable createDiscountPolicy(int initAgeRange, int finalAgeRange)
	{
		if(initAgeRange>finalAgeRange)
			throw new InvalidAgeRangeException();
		Discountable newDiscountPolicy=new Discountable()
		{
			public <T extends HasDiscount> boolean checkDiscount(T element)
			{
				if(element.getClass()==Client.class)
					if(element.getAge()>=initAgeRange && element.getAge()<=finalAgeRange)
						return true;

				return false;
			}

			private static final long serialVersionUID = -8958828716009215270L;
		};

		return newDiscountPolicy;
	}

	/**
	 * Create a new discount policy based on a Film
	 * @see classes.Film
	 * @param film The film to apply the discount
	 * @return The created discount policy
	 */
	public Discountable createDiscountPolicy(Film film)
	{
		Discountable newDiscountPolicy=new Discountable()
		{
			public <T extends HasDiscount> boolean checkDiscount(T element)
			{
				if(element.getClass()==Show.class)
					if(element.getFilm().equals(film))
						return true;

				return false;
			}

			private static final long serialVersionUID = -7336898035456716131L;
		};

		return newDiscountPolicy;
	}

	/**
	 * Create a new discount policy based on a day of the week
	 * @param dayOfWeek The day of the week to apply the discount
	 * @return The created discount policy
	 */
	public Discountable createDiscountPolicy(int dayOfWeek)
	{
		Discountable newDiscountPolicy=new Discountable()
		{
			public <T extends HasDiscount> boolean checkDiscount(T element)
			{
				if(element.getClass()==Show.class)
					if(element.getDate().get(MyCalendar.DAY_OF_WEEK)==dayOfWeek+1)
						return true;

				return false;
			}

			private static final long serialVersionUID = -6750845554755066009L;
		};

		return newDiscountPolicy;
	}

	/**
	 * Add discount to the discounts list of the Cinema
	 * @param aDiscount The discount to add
	 * @return true if the discount was added, false otherwise
	 */
	public boolean addDiscount(Discount aDiscount)
	{
		return discounts.add(aDiscount.clone());
	}

	/**
	 * Remove discount to the discounts list of the Cinema
	 * @param aDiscount The discount to delete
	 * @return true if the discount was removed, false otherwise
	 */
	public boolean removeDiscount(Discount aDiscount)
	{
		return discounts.remove(aDiscount);
	}

	/**
	 * Return the discount specified by index
	 * @param index The index of the discount
	 * @return The discount specified by index
	 */
	public Discount getDiscount(int index)
	{
		return discounts.get(index).clone();
	}

	/**
	 * Return the number of the discount of the Cinema
	 * @return The number of the discount of the Cinema
	 */
	public int getNumberOfDiscounts()
	{
		return discounts.size();
	}

	/**
	 * Check if the element is entitled to a discount
	 * @param element The element to be examined
	 * @param <T> The type of the element to be examined
	 * @return true if the element has a discount, false otherwise
	 */
	public <T extends HasDiscount> boolean hasDiscount(T element)
	{
		for(Discount s : discounts)
			if(s.getDiscountPolicy().checkDiscount(element))
				return true;

		return false;
	}

	/**
	 * Return the discount rate of the element
	 * @param element The element of which the discount rate is desired
	 * @param <T> The type of the element of which the discount rate is desired
	 * @return The discount rate of the element if it is entitled to a discount, -1 otherwise
	 */
	public <T extends HasDiscount> int getDiscountRate(T element)
	{
		for(Discount s : discounts)
		{
			if(s.getDiscountPolicy().checkDiscount(element))
				return s.getDiscountRate();
		}

		return -1;
	}

	/**
	 * Return the state of activation of the discount policies
	 * @return The state of activation of the discount policies
	 */
	public boolean isActive()
	{
		return isActive;
	}

	/**
	 * Set the state of the activation of the discount policies
	 * @param flag The state of the activation of the discount policies
	 */
	public void setActive(boolean flag)
	{
		isActive=flag;
	}

	private boolean isActive;
	private ArrayList<Discount> discounts;

	private static final long serialVersionUID = -3920663544088874975L;
}
