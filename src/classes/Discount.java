package classes;

import java.io.Serializable;

import exceptions.DiscountRateGreaterThanOnehundredException;
import interfaces.Discountable;

/**
 * This class represent a Discount with his policy, discount rate and description
 * @author Francesco Migliaro
 */
public class Discount implements Serializable, Cloneable
{
	/**
	 * Initialize a newly create discount with the values of the parameters
	 * @param aDiscountable The discount policy
	 * @param aRate The discount rate
	 * @param aDescription The discount description
	 * @throws DiscountRateGreaterThanOnehundredException It's throws when the discount rate is greather than one hundred
	 * @see exceptions.DiscountRateGreaterThanOnehundredException
	 */
	public Discount(Discountable aDiscountable, Integer aRate, String aDescription) throws DiscountRateGreaterThanOnehundredException
	{
		if(aRate>100)
			throw new DiscountRateGreaterThanOnehundredException();
		discountPolicy=aDiscountable;
		rate=aRate;
		description=aDescription;
	}

	/**
	 * Return the policy of this Discount
	 * @return The policy of this Discount
	 */
	public Discountable getDiscountPolicy()
	{
		return discountPolicy;
	}
	
	/**
	 * Return the discount rate of this Discount
	 * @return The discount rate of this Discount
	 */
	public int getDiscountRate()
	{
		return rate;
	}

	/**
	 * Return a String descripting the discount policy of this Discount
	 * @return A String descripting the discount policy of this Discount
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Replace the discount policy of this Discount with the parameter aDiscountable
	 * @param aDiscountable The discount policy to be stored
	 */
	public void setDiscountPolicy(Discountable aDiscountable)
	{
		discountPolicy=aDiscountable;
	}
	
	/**
	 * Replace the discount rate of this Discount with the parameter aRate
	 * @param aRate The discount rate to be stored
	 */
	public void setDiscountRate(int aRate)
	{
		rate=aRate;
	}
	
	/**
	 * Replace the discount description of this Discount with the parameter aDescription
	 * @param aDescription The description to be stored
	 */
	public void setDescription(String aDescription)
	{
		description=aDescription;
	}
	
	/**
	 * Returns a String representing this Discount and its values
	 * @return A String representing this Discount and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[description= " + description + ", rate= " + rate + "]";
	}
	
	/**
	 * This method perform a comparison between this Discount and the param obj
	 * @param obj Object to compare with
	 * @return true if this Discount is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(obj.getClass()!=getClass())
			return false;
		
		Discount otherSale=(Discount) obj;
		
		return discountPolicy==otherSale.discountPolicy && rate==otherSale.rate && description.equals(otherSale.description);
	}
	
	/**
	 * This method perform a copy of this Discount
	 * @return Cloned Discount
	 */
	public Discount clone()
	{
		try
		{
			Discount clone=(Discount) super.clone();
			
			return clone;
		}
		catch(CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	private Discountable discountPolicy;
	private Integer rate;
	private String description;

	private static final long serialVersionUID = 931633833363899318L;
}
