package classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import enums.State;

/**
 * This class represent a Place
 * @author Francesco Migliaro
 */
public class Place extends JLabel implements Cloneable
{
	/**
	 * Initialize a newly created Place with the default values
	 */
	public Place()
	{
		super(new ImageIcon(imagePath));
		setOpaque(true);
		state=State.AVALAIBLE;
		index=0;
		setEnabled(false);
		setBackground(Color.GREEN);
	}
	
	/**
	 * Initialize a newly created Place with the state specified by param aState
	 * @param aState The state of the Place
	 */
	public Place(State aState)
	{
		super(new ImageIcon(imagePath));
		setOpaque(true);
		setEnabled(false);
		index=0;
		state=aState;
		setBackgroundColor();
	}
	
	/**
	 * Return the state of this Place
	 * @return The state of this Place
	 */
	public State getState()
	{
		return state;
	}
	
	/**
	 * Return the index of this Place
	 * @return The index of this Place
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * Replace the state of this Place with the parameter aState
	 * @param aState The state to be stored
	 */
	public void setState(State aState)
	{
		state=aState;
		setBackgroundColor();
	}
	
	/**
	 * Replace the index of this Place with the paramter anIndex
	 * @param anIndex The index to be stored
	 */
	public void setIndex(int anIndex)
	{
		index=anIndex;
	}
	
	/**
	 * Returns a String representing this Place and its values
	 * @return A String representing this Place and its values
	 */
	public String toString()
	{
		return getClass().getSimpleName() + "[state= " + state + ", index= " + index + "]";
	}
	
	/**
	 * This method perform a deep comparison between this Place and the param obj
	 * @param obj Object to compare with
	 * @return true if this Place is equal to obj, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		
		Place otherPlace=(Place) obj;
		
		return state==otherPlace.state && index==otherPlace.index;
	}
	
	/**
	 * This method perform a deep copy of this Place
	 * @return Cloned Place
	 */
	public Place clone()
	{
		try
		{
			Place clone=(Place) super.clone();
			
			return clone;
		} 
		catch (CloneNotSupportedException exception)
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @see JLabel#paintComponents(Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D) g;
		Rectangle place=new Rectangle(graphicSize, graphicSize);
		
		g2.draw(place);
		g2.setColor(getBackground());
		g2.fill(place);
		getIcon().paintIcon(this, g, 0, 0);
	}
	
	/**
	 * Set the background color of this Place
	 */
	private void setBackgroundColor()
	{
		switch(state)
		{
			case UNAVALAIBLE:
				setBackground(Color.GRAY);
			break;
			case AVALAIBLE:
				setBackground(Color.GREEN);
			break;
			case RESERVED:
				setBackground(Color.YELLOW);
			break;
			case SOLD:
				setBackground(Color.RED);
			break;
		}
	}
	
	private State state;
	private int index;

	private static final int graphicSize=15;
	private static final String imagePath="./armchair.png";
	private static final long serialVersionUID = -4978573104116103701L;
}
