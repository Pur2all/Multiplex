package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Film;
import classes.Discount;
import exceptions.DiscountRateGreaterThanOnehundredException;
import interfaces.Discountable;
import managers.GenericManager;
import managers.DiscountsManager;

/**
 * This class represent a dialog to add a new Discount
 * @see Discount
 * @author Francesco Migliaro
 */
public class AddDiscountDialog extends JDialog
{
	/**
	 * Initialize a newly created AddDiscountDialog with the value of the parameters
	 * @param discountsManager The discounts manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 * @see classes.Cinema
	 */
	public AddDiscountDialog(DiscountsManager discountsManager, GenericManager<Film> filmsManager)
	{
		super();
		setTitle("Add Discount");
		discount=null;
		Dimension preferredSize=new Dimension(width, height);
		mainPanel=new JPanel();
		labelRate=new JLabel("Rate:");
		labelType=new JLabel("What to apply for the discount:");
		type=new JComboBox<String>();
		type.addItem("Client age range");
		type.addItem("Film");
		type.addItem("Days of week");
		type.setSelectedItem(null);
		panel=new JPanel();
		type.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent itemEvent)
			{
				String typeStr=(String)type.getSelectedItem();
				
				panel.removeAll();
				
				if(typeStr.equals("Client age range"))
				{
					labelStartRange=new JLabel("Starting range:");
					labelEndRange=new JLabel("End range:");
					textStartRange=new JTextField();
					textEndRange=new JTextField();
					
					textStartRange.setPreferredSize(preferredSize);
					textEndRange.setPreferredSize(preferredSize);
					panel.add(labelStartRange);
					panel.add(textStartRange);
					panel.add(labelEndRange);
					panel.add(textEndRange);
				}
				else
					if(typeStr.equals("Film"))
					{
						labelFilms=new JLabel("Films:");
						films=new JComboBox<String>();
						
						for(int i=0; i<filmsManager.getNumberOfElement(); i++)
							films.addItem(filmsManager.getElement(i).getTitle());
						panel.add(labelFilms);
						panel.add(films);
					}
					else
					{
						String[] strDays= {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
						labelDay=new JLabel("Days:");
						days=new JComboBox<String>(strDays);
						
						panel.add(labelDay);
						panel.add(days);
					}
				pack();
			}
		});
		rate=new JTextField();
		rate.setPreferredSize(preferredSize);
		add=new JButton("Add");
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Discountable discountable=null;
				String description=null;
				int saleRate;
				switch(type.getSelectedIndex())
				{
					case 0:
						int start=Integer.parseInt(textStartRange.getText()), end=Integer.parseInt(textEndRange.getText());
						
						if(start>end)
						{
							JOptionPane.showMessageDialog(rootPane, "Age range is invalid, please insert start age range less equal than end age range", "Error", JOptionPane.ERROR_MESSAGE);
							break;
						}
						discountable=discountsManager.createDiscountPolicy(start, end);
						description="Sale is applicable to clients between the ages of " + start + " and " + end;
					break;
					case 1:
						discountable=discountsManager.createDiscountPolicy(filmsManager.getElement(films.getSelectedIndex()));
						description="Sale is applicable to the shows that project \"" + filmsManager.getElement(films.getSelectedIndex()).getTitle() + "\"";
					break;
					case 2:
						discountable=discountsManager.createDiscountPolicy(days.getSelectedIndex());
						description="Sale is applicable to the shows scheduled on " + days.getSelectedItem();
					break;
				}
				saleRate=Integer.parseInt(rate.getText());
				try
				{
					if(discountable!=null && description!=null)
						discount=new Discount(discountable, saleRate, description);
				} 
				catch (DiscountRateGreaterThanOnehundredException discountRateGreaterThanOnehundredException)
				{
					JOptionPane.showMessageDialog(getRootPane(), discountRateGreaterThanOnehundredException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
		});
		mainPanel.add(labelType);
		mainPanel.add(type);
		mainPanel.add(labelRate);
		mainPanel.add(rate);
		mainPanel.add(panel);
		mainPanel.add(add);
		add(mainPanel);
		pack();
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the Discount created
	 * <br><strong> NOTE: </strong> If the Discount isn't be created this method will return null
	 * @return The Discount created
	 */
	public Discount getDiscount()
	{
		if(discount==null)
			return null;
		
		return discount.clone();
	}
	
	private Discount discount;
	
	private JPanel mainPanel, panel;
	private JLabel labelType, labelRate, labelStartRange, labelEndRange, labelFilms, labelDay;
	private JComboBox<String> type, films, days;
	private JTextField rate, textStartRange, textEndRange;
	private JButton add;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = 7842728421248063810L;
}
