package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import classes.Film;
import classes.Hall;
import classes.Show;
import managers.GenericManager;
import utils.MyCalendar;

/**
 * This class represent a dialog to add new Show
 * @see classes.Show
 * @author Francesco Migliaro
 */
public class AddShowDialog extends JDialog
{
	/**
	 * Initialize a newly created AddShowDialog with the value of the parameters
	 * @param hallsManager The halls manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 * @see classes.Cinema
	 */
	public AddShowDialog(GenericManager<Hall> hallsManager, GenericManager<Film> filmsManager)
	{
		super();
		setTitle("Add show");
		show=null;
		mainPanel=new JPanel();
		add=new JButton("Add");
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				MyCalendar tempDate=new MyCalendar(MyCalendar.getInstance().get(MyCalendar.YEAR), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()),
						Integer.parseInt(hh.getText()), Integer.parseInt(mm.getText()));
				
				show=new Show(hallsManager.getElement(hall.getSelectedIndex()), filmsManager.getElement(film.getSelectedIndex()), tempDate, Float.parseFloat(price.getText()));
				dispose();
			}
		});
		createFilmPanel(filmsManager);
		createHallPanel(hallsManager);
		createDatePanel();
		createPricePanel();
		mainPanel.add(filmPanel);
		mainPanel.add(hallPanel);
		mainPanel.add(datePanel);
		mainPanel.add(pricePanel);
		mainPanel.add(add);
		add(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setModal(true);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the Show created
	 * <br><strong> NOTE: </strong> If the Show isn't be created this method will return null
	 * @return The Show created
	 */
	public Show getShow()
	{
		if(show==null)
			return null;
		
		return show.clone();
	}
	
	/**
	 * Create option panel for Film
	 * @param filmsManager The films manager
	 */
	private void createFilmPanel(GenericManager<Film> filmsManager)
	{
		JLabel labelFilm=new JLabel("Film:");
		String[] films=new String[filmsManager.getNumberOfElement()];
		
		for(int i=0; i<filmsManager.getNumberOfElement(); i++)
			films[i]=filmsManager.getElement(i).getTitle();
		filmPanel=new JPanel();
		filmPanel.setBorder(new TitledBorder(new EtchedBorder(), "Film"));
		filmPanel.add(labelFilm);
		film=new JComboBox<String>(films);
		filmPanel.add(film);
	}
	
	/**
	 * Create option panel for Hall
	 * @param hallsManager The halls manager
	 */
	private void createHallPanel(GenericManager<Hall> hallsManager)
	{
		JLabel labelHall=new JLabel("Hall:");
		String[] halls=new String[hallsManager.getNumberOfElement()];
		
		for(int i=0; i<hallsManager.getNumberOfElement(); i++)
			halls[i]=hallsManager.getElement(i).getName();
		hallPanel=new JPanel();
		hallPanel.setBorder(new TitledBorder(new EtchedBorder(), "Hall"));
		hallPanel.add(labelHall);
		hall=new JComboBox<String>(halls);
		hallPanel.add(hall);
	}
	
	/**
	 * Create option panel for Date
	 */
	private void createDatePanel()
	{
		JLabel labelDay=new JLabel("Day:"), labelMonth=new JLabel("Month:"), labelHour=new JLabel("Hour:"), labelMinutes=new JLabel("Minutes:");
		
		datePanel=new JPanel();
		datePanel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
		Dimension preferredSize=new Dimension(width, height);
		day=new JTextField();
		day.setPreferredSize(preferredSize);
		month=new JTextField();
		month.setPreferredSize(preferredSize);
		hh=new JTextField();
		hh.setPreferredSize(preferredSize);
		mm=new JTextField();
		mm.setPreferredSize(preferredSize);
		datePanel.add(labelDay);
		datePanel.add(day);
		datePanel.add(labelMonth);
		datePanel.add(month);
		datePanel.add(labelHour);
		datePanel.add(hh);
		datePanel.add(labelMinutes);
		datePanel.add(mm);
		
	}
	
	/**
	 * Create option panel for price
	 */
	private void createPricePanel()
	{
		JLabel labelPrice=new JLabel("Price:");
		
		pricePanel=new JPanel();
		pricePanel.setBorder(new TitledBorder(new EtchedBorder(), "Price"));
		price=new JTextField();
		Dimension preferredSize=new Dimension(width, height);
		price.setPreferredSize(preferredSize);
		pricePanel.add(labelPrice);
		pricePanel.add(price);
	}
	
	private Show show;
	
	private JPanel mainPanel, filmPanel, hallPanel, datePanel, pricePanel;
	private JComboBox<String> film, hall;
	private JTextField price, month, day, hh, mm;
	private JButton add;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = -671877164539910650L;
}