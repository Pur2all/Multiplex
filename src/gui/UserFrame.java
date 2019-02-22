package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import classes.Cinema;
import classes.Client;
import classes.User;

/**
 * This class represent the main frame of the User to interact with the abstraction of the Cinema
 * @see classes.User
 * @see classes.Cinema
 * @author Francesco Migliaro
 */
public class UserFrame extends JFrame
{
	/**
	 * Initialize newly created User Frame
	 */
	public UserFrame()
	{
		super("UserFrame");
		cinema=new Cinema();
		loginDialog=new LoginDialog(cinema.getLoginManager());
		loginDialog.setVisible(true);
		if(!loginDialog.userIsAuthenticated())
			System.exit(0);
		user=loginDialog.getUser();
		tabbedPane=new JTabbedPane();
		menuBar=new JMenuBar();
		menuAccount=new JMenu("Account");	
		showInfo=new JMenuItem("Account info");
		createStandardFrame();
		pack();
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Fill the frame with the standard components
	 */
	private void createStandardFrame()
	{
		showInfo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				class AccountInfoDialog extends JDialog
				{
					public AccountInfoDialog(User anUser)
					{
						super();
						setTitle("Account info");
						mainPanel=new JPanel();
						name=new JLabel("Name: " + anUser.getName());
						surname=new JLabel("Surname: " + anUser.getSurname());
						age=new JLabel("Age: " + anUser.getAge());
						email=new JLabel("Email: " + anUser.getEmail());
						password=new JLabel("Password: " + anUser.getPassword());
						mainPanel.add(name);
						mainPanel.add(surname);
						mainPanel.add(age);
						mainPanel.add(email);
						mainPanel.add(password);
						add(mainPanel);
						pack();
						setModal(true);
						setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						Dimension screenSize=getToolkit().getScreenSize();
						setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
					}
					
					private JPanel mainPanel;
					private JLabel name, surname, age, email, password;
					
					private static final long serialVersionUID = 1738767593022899805L;
				}
				
				AccountInfoDialog accountInfoDialog=new AccountInfoDialog(user);
				
				accountInfoDialog.setVisible(true);
			}
		});
		menuAccount.add(showInfo);
		menuBar.add(menuAccount);
		if(user instanceof Client)
			createClientFrame();
		else
			createManagerFrame();
		add(menuBar, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				cinema.saveData(cinema.getDiscountsManager().isActive());
				super.windowClosing(windowEvent);
			}
		});
	}
	
	/**
	 * Fill the frame with the components for the Manager
	 */
	private void createManagerFrame()
	{
		labelName=new JLabel("Welcome " + user.getName() +"!");
		add(labelName);
		menuTaking=new JMenu("Taking");
		takingOfWeek=new JMenuItem("View taking for this week");
		takingOfWeek.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				ViewTakingDialog viewTakingDialog=new ViewTakingDialog(null, cinema.getWeeklyProgramManager());
				
				viewTakingDialog.setVisible(true);
			}
		});
		takingForFilm=new JMenuItem("View taking for a specific film");
		takingForFilm.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				ViewTakingDialog viewTakingDialog=new ViewTakingDialog(cinema.getFilmsManager(), cinema.getWeeklyProgramManager());
				
				viewTakingDialog.setVisible(true);
			}
		});
		menuTaking.add(takingForFilm);
		menuTaking.add(takingOfWeek);
		menuBar.add(menuTaking);
		filmsManagerPanel=new FilmsManagerPanel(cinema.getFilmsManager());
		tabbedPane.addTab("Films Manager", filmsManagerPanel);
		hallsManagerPanel=new HallsManagerPanel(cinema.getHallsManager(), cinema.getWeeklyProgramManager());
		tabbedPane.addTab("Halls Manager", hallsManagerPanel);
		createWeeklyProgramManagerPanel();
		discountsManagerPanel=new DiscountsManagerPanel(cinema.getDiscountsManager(), cinema.getFilmsManager());
		tabbedPane.addTab("Sales Manager", discountsManagerPanel);
	}
	
	/**
	 * Fill the frame with the components for the Client
	 */
	private void createClientFrame()
	{
		Client client=(Client) user;
		
		labelName=new JLabel("Welcome " + client.getName() +"!");
		add(labelName);
		if(cinema.getDiscountsManager().isActive() && cinema.getDiscountsManager().hasDiscount(client))
			labelName.setText(labelName.getText() + " You are entitled to " + cinema.getDiscountsManager().getDiscountRate(client) + "% sale!");
		cinema.setReservationsManager(client);
		reservationManagerPanel=new ReservationManagerPanel(cinema.getReservationsManager(), cinema.getDiscountsManager(), cinema.getHallsManager(), cinema.getWeeklyProgramManager());
		createWeeklyProgramManagerPanel();
		tabbedPane.addTab("Reservation manager", reservationManagerPanel);
		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				reservationManagerPanel.repaintTable(cinema.getReservationsManager());
				weeklyProgramManagerPanel.updateSelected(cinema.getWeeklyProgramManager());
			}
		});
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				client.deleteReservations();
				for(int i=0; i<cinema.getReservationsManager().getNumberOfReservation(); i++)
					client.setReservation(i, cinema.getReservationsManager().getReservation(i));
				cinema.saveClientReservation(client);
				cinema.saveData(cinema.getDiscountsManager().isActive());
				super.windowClosing(windowEvent);
			}
		});
	}
	
	/**
	 * Create the weekly program manager panel
	 */
	private void createWeeklyProgramManagerPanel()
	{
		weeklyProgramManagerPanel=new WeeklyProgramManagerPanel(cinema.getWeeklyProgramManager(), user.getType(), cinema.getHallsManager(), 
				cinema.getFilmsManager(), cinema.getReservationsManager(), cinema.getDiscountsManager());
		tabbedPane.addTab("Weekly Program Manager", weeklyProgramManagerPanel);
	}
	
	private Cinema cinema;
	
	private User user;
	
	private DiscountsManagerPanel discountsManagerPanel;
	private FilmsManagerPanel filmsManagerPanel;
	private HallsManagerPanel hallsManagerPanel;
	private WeeklyProgramManagerPanel weeklyProgramManagerPanel;
	private ReservationManagerPanel reservationManagerPanel;
	private LoginDialog loginDialog;
	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JMenu menuAccount, menuTaking;
	private JMenuItem showInfo, takingOfWeek, takingForFilm;
	private JLabel labelName;
	
	private static final long serialVersionUID = 7791374891522389808L;
}
