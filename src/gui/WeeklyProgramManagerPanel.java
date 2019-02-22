package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import classes.Film;
import classes.Hall;
import classes.Place;
import classes.Reservation;
import classes.Show;
import enums.State;
import enums.Type;
import exceptions.ReservationsExpiredException;
import interfaces.CheckShow;
import managers.GenericManager;
import managers.ReservationManager;
import managers.DiscountsManager;
import utils.MyCalendar;
import utils.MyDefaultTableModel;

/**
 * This class represent a panel to interact with the Weekly Program Manager
 * @see managers.GenericManager
 * @author Francesco Migliaro
 */
public class WeeklyProgramManagerPanel extends JPanel
{
	/**
	 * Instantiate the WeeklyProgramManagerPanel that use the value of the parameters
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @param aType The type of the User for define operations
	 * @param hallsManager The halls manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 * @param reservationManager The reservations manager of the Cinema
	 * @param discountsManager The discounts manager of the Cinema
	 */
	public WeeklyProgramManagerPanel(GenericManager<Show> weeklyProgramManager, Type aType, GenericManager<Hall> hallsManager, GenericManager<Film> filmsManager, ReservationManager reservationManager, DiscountsManager discountsManager)
	{
		super();
		setLayout(new BorderLayout());
		sales=discountsManager;
		
		String[] columns= {"Film", "Hall", "Date", "Price"};
		String[][] rows=new String[weeklyProgramManager.getNumberOfElement()][4];
		
		comparator=(firstShow, secondShow)->0;
		defaultTableModel=new MyDefaultTableModel(rows, columns);
		tableWeeklyProgram=new JTable(defaultTableModel);
		tableWeeklyProgram.getTableHeader().setReorderingAllowed(false);
		tableWeeklyProgram.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableWeeklyProgram.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent mouseEvent)
			{
				if(mouseEvent.getClickCount()==2)
				{
					/**
					 * This class represent a dialog to view the info of the Fillm
					 * @author Francesco Migliaro
					 */
					class ViewFilmInfoDialog extends JDialog
					{
						/**
						 * Instantiate newly create ViewFIlmInfoDialog that use the value of the parameter
						 * @param aFilm The film to see the info
						 */
						public ViewFilmInfoDialog(Film aFilm)
						{
							super();
							setTitle("Film info");
							mainPanel=new JPanel();
							filmMetaPanel=new JPanel();
							filmPlotPanel=new JPanel();
							filmTitle=new JLabel("Title: " + aFilm.getTitle());
							filmDirector=new JLabel("Director: " + aFilm.getDirector());
							filmYear=new JLabel("Year: " + aFilm.getYear());
							filmDuration=new JLabel("Duration: " + aFilm.getDuration());
							filmPlotLabel=new JLabel("Plot: ");
							filmPlot=new JTextArea(aFilm.getPlot());
							filmPlot.setEditable(false);
							scrollPane=new JScrollPane(filmPlot);
							filmMetaPanel.add(filmTitle);
							filmMetaPanel.add(filmDirector);
							filmMetaPanel.add(filmYear);
							filmMetaPanel.add(filmDuration);
							filmPlotPanel.add(filmPlotLabel);
							filmPlotPanel.add(scrollPane);
							mainPanel.add(filmMetaPanel);
							mainPanel.add(filmPlotPanel);
							add(mainPanel);
							pack();
							setModal(true);
							setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							Dimension screenSize=getToolkit().getScreenSize();
							setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
						}
						
						private JPanel mainPanel, filmMetaPanel, filmPlotPanel;
						private JLabel filmTitle, filmDirector, filmYear, filmDuration, filmPlotLabel;
						private JTextArea filmPlot;
						private JScrollPane scrollPane;
						
						private static final long serialVersionUID = 6750507796236591159L;
					}
					
					JTable tempTable=(JTable) mouseEvent.getSource();
					int showIndex=tempTable.getSelectedRow();
					ViewFilmInfoDialog viewFilmInfoDialog=new ViewFilmInfoDialog(selected.getElement(showIndex).getFilm());
					
					viewFilmInfoDialog.setVisible(true);
				}
				super.mouseClicked(mouseEvent);
			}
		});
		scrollPane=new JScrollPane(tableWeeklyProgram);
		add(scrollPane, BorderLayout.WEST);
		if(aType==Type.MANAGER)
			createManagerPanel(hallsManager, filmsManager, weeklyProgramManager);
		else
			createClientPanel(weeklyProgramManager, hallsManager, reservationManager, discountsManager);
	}
	
	/**
	 * Update the selected shows from weekly program
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 */
	public void updateSelected(GenericManager<Show> weeklyProgramManager)
	{
		viewShowsForCheck(checkShow, weeklyProgramManager);
	}
	
	/**
	 * Fill the panel with the components for the Manager
	 * @param hallsManager The halls manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 */
	private void createManagerPanel(GenericManager<Hall> hallsManager, GenericManager<Film> filmsManager, GenericManager<Show> weeklyProgramManager)
	{
		viewShowsForCheck((aShow)->true, weeklyProgramManager);
		panelAddRemove=new JPanel();
		checkBoxAvalaiblePlace=new JCheckBox("Sort for avalaible places");
		checkBoxAvalaiblePlace.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(checkBoxAvalaiblePlace.isSelected())
					sortShow(new Comparator<Show>()
					{
						public int compare(Show firstShow, Show secondShow)
						{
							int placeAvalaibleFirstHall=0, placeAvalaibleSecondHall=0;
							Hall firstHall=firstShow.getHall(), secondHall=secondShow.getHall();
							
							for(int i=0; i<firstHall.getNumberOfPlaces(); i++)
								if(firstHall.getPlaceAtIndex(i).getState()==State.AVALAIBLE)
									placeAvalaibleFirstHall++;
							for(int i=0; i<secondHall.getNumberOfPlaces(); i++)
								if(secondHall.getPlaceAtIndex(i).getState()==State.AVALAIBLE)
									placeAvalaibleSecondHall++;

							return placeAvalaibleSecondHall-placeAvalaibleFirstHall;
						}
					});
				else
					viewShowsForCheck((aShow)->true, weeklyProgramManager);
			}
		});
		addShowButton=new JButton("Add show");
		addShowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				AddShowDialog addShowDialog=new AddShowDialog(hallsManager, filmsManager);
				
				addShowDialog.setVisible(true); 
				Show tempShow=addShowDialog.getShow();
				if(tempShow!=null)
				{
					if(weeklyProgramManager.thereIsElement(tempShow))
						JOptionPane.showMessageDialog(getRootPane(), "Show already exists", "Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						weeklyProgramManager.addElement(tempShow);
						defaultTableModel.addRow(new String[] {tempShow.getFilm().getTitle(), tempShow.getHall().getName(), MyCalendar.dateFormatter(tempShow.getDate()), tempShow.getPrice()+""});
					}
				}
			}
		});
		deleteShowButton=new JButton("Delete show");
		deleteShowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int rowIndex=tableWeeklyProgram.getSelectedRow();
				
				if(rowIndex!=-1)
				{	
					weeklyProgramManager.removeElement(weeklyProgramManager.getElement(rowIndex));
					defaultTableModel.removeRow(rowIndex);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a show", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		panelAddRemove.add(checkBoxAvalaiblePlace);
		panelAddRemove.add(addShowButton);
		panelAddRemove.add(deleteShowButton);
		add(panelAddRemove);
	}
	
	/**
	 * Fill the panel with the components for the Client
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @param hallsManager The halls manager of the Cinema
	 * @param reservationManager The reservations manager of the Cinema
	 * @param discountsManager The discounts manager of the Cinema
	 */
	private void createClientPanel(GenericManager<Show> weeklyProgramManager, GenericManager<Hall> hallsManager, ReservationManager reservationManager, DiscountsManager discountsManager)
	{
		viewShowsForCheck(showIsOfThisWeek, weeklyProgramManager);
		panelReservationButton=new JPanel();
		reservePlace=new JButton("Reserve or buy place");
		reservePlace.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=tableWeeklyProgram.getSelectedRow();

				if(rowIndex!=-1)
				{	
					int contException=0;
					Show tempShow=weeklyProgramManager.getElement(weeklyProgramManager.getElementIndex(selected.getElement(rowIndex)));
					if(tempShow.isUsable())
					{
						ViewHallDialog viewHallDialog=new ViewHallDialog(tempShow.getHall(), Type.CLIENT);
						
						viewHallDialog.setVisible(true);
						Hall tempHall=viewHallDialog.getHall();
						tempShow.setHall(tempHall);
						weeklyProgramManager.setElement(weeklyProgramManager.getElementIndex(selected.getElement(rowIndex)), tempShow);
						selected.setElement(rowIndex, tempShow); 
						ArrayList<Place> places=viewHallDialog.getSelectedPlaces();
						for(int i=0; i<places.size(); i++)
						{
							Reservation tempReservation=new Reservation(places.get(i), selected.getElement(rowIndex));
							
							try
							{
								reservationManager.addReservation(tempReservation, selected.getElement(rowIndex));
								if(tempReservation.getPlace().getState()==State.SOLD)
									reservationManager.buyReservation(tempReservation, discountsManager);
							}
							catch (ReservationsExpiredException reservationsExpiredException)
							{
								if(contException==0)
									JOptionPane.showMessageDialog(getRootPane(), reservationsExpiredException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
								contException++;
								Place tempPlace=places.get(i);
								tempPlace.setState(State.AVALAIBLE);
								tempHall.setPlaceAtIndex(tempPlace, tempPlace.getIndex()-1);
								tempShow.setHall(tempHall);
								weeklyProgramManager.setElement(weeklyProgramManager.getElementIndex(selected.getElement(rowIndex)), tempShow);
								selected.setElement(rowIndex, tempShow); 
							}
						}
					}
					else
						JOptionPane.showMessageDialog(getRootPane(), "Show is expired, select an usable show", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a show", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		panelReservationButton.add(reservePlace);
		createSortPanel();
		add(panelSort, BorderLayout.NORTH);
		createCheckPanel(weeklyProgramManager, hallsManager);
		panelCheck.add(panelReservationButton, BorderLayout.SOUTH);
		add(panelCheck, BorderLayout.EAST);
	}
	
	/**
	 * Fill the panelCheck 
	 * @param weeklyProgramManager The weekly program manager of the Cinema
	 * @param hallsManager The halls manager of the Cinema
	 */
	private void createCheckPanel(GenericManager<Show> weeklyProgramManager, GenericManager<Hall> hallsManager)
	{
		panelCheck=new JPanel();
		checkBoxHall=new JCheckBox("View weekly program for hall");
		checkBoxHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{				
				if(checkBoxHall.isSelected())
				{
					comboBoxHall.setEnabled(true);
					if(comboBoxHall.getSelectedIndex()!=-1)
						comboBoxHall.getActionListeners()[0].actionPerformed(actionEvent);
				}
				else
				{
					comboBoxHall.setEnabled(false);
					viewShowsForCheck(checkBoxUsable.isSelected() ? showIsUsable : showIsOfThisWeek, weeklyProgramManager);
				}
			}
		});
		comboBoxHall=new JComboBox<String>();
		for(int i=0; i<hallsManager.getNumberOfElement(); i++)
			comboBoxHall.addItem(hallsManager.getElement(i).getName());
		comboBoxHall.setSelectedItem(null);
		comboBoxHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				viewShowsForCheck(new CheckShow()
				{
					public boolean isSelected(Show aShow)
					{
						if(aShow.getHall().getName().equals((String)comboBoxHall.getSelectedItem()))
							return true;
						
						return false;
					}
				}, checkBoxUsable.isSelected() ? selected : weeklyProgramManager);
			}
		});
		comboBoxHall.setEnabled(false);
		checkBoxUsable=new JCheckBox("View only usable shows");
		checkBoxUsable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				if(checkBoxUsable.isSelected())
					viewShowsForCheck(showIsUsable, checkBoxHall.isSelected() ? selected : weeklyProgramManager);
				else
					viewShowsForCheck(showIsOfThisWeek, weeklyProgramManager);
			}
		});
		panelCheck.add(checkBoxUsable);
		panelCheck.add(checkBoxHall);
		panelCheck.add(comboBoxHall);
	}
	
	/**
	 * Fill the panelSort
	 */
	private void createSortPanel()
	{
		panelSort=new JPanel();
		panelSort.setBorder(new TitledBorder(new EtchedBorder(), "Sort weekly program"));
		sortLabel=new JLabel("Sort for:");
		sortSchedule=new JRadioButton("Schedule");
		sortSchedule.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				sortShow(new Comparator<Show>()
				{
					public int compare(Show firstShow, Show secondShow)
					{
						return firstShow.getDate().compareTo(secondShow.getDate());
					}
				});
			}
		});
		sortHall=new JRadioButton("Hall");
		sortHall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				sortShow(new Comparator<Show>()
				{
					public int compare(Show firstShow, Show secondShow)
					{
						return firstShow.getHall().getName().compareTo(secondShow.getHall().getName());
					}
				});
			}
		});
		sortAlpha=new JRadioButton("Film title");
		sortAlpha.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				sortShow(new Comparator<Show>()
				{
					public int compare(Show firstShow, Show secondShow)
					{
						return firstShow.getFilm().getTitle().compareTo(secondShow.getFilm().getTitle());
					}
				});
			}
		});
		sortGroup=new ButtonGroup();
		sortGroup.add(sortSchedule);
		sortGroup.add(sortHall);
		sortGroup.add(sortAlpha);
		panelSort.add(sortLabel);
		panelSort.add(sortSchedule);
		panelSort.add(sortHall);
		panelSort.add(sortAlpha);
	}
	
	/**
	 * Select show for a criterion identified by CheckShow
	 * @param checkShow The criterion for select shows
	 * @param weeklyProgramManager The weekly program manager to apply the selection
	 */
	private void viewShowsForCheck(CheckShow checkShow, GenericManager<Show> weeklyProgramManager)
	{
		while(defaultTableModel.getRowCount()!=0)
			defaultTableModel.removeRow(0);
		this.checkShow=checkShow;
		selected=weeklyProgramManager.select(checkShow);
		sortShow(comparator);
	}

	/**
	 * Sort selected shows for a criterion based on the comparator
	 * @param comparator The criterion for sort the selected shows
	 */
	private void sortShow(Comparator<Show> comparator)
	{
		while(defaultTableModel.getRowCount()!=0)
			defaultTableModel.removeRow(0);
		this.comparator=comparator;
		selected.sort(comparator);
		updateTable(selected);
	}
	
	/**
	 * Update the table of the selected shows
	 * @param weeklyProgramManager The weekly program manager used to update the table
	 */
	private void updateTable(GenericManager<Show> weeklyProgramManager)
	{
		for(int i=0; i<weeklyProgramManager.getNumberOfElement(); i++)
		{
			Show tempShow=weeklyProgramManager.getElement(i);
			String price=tempShow.getPrice() + (sales.hasDiscount(tempShow) ? "-" + sales.getDiscountRate(tempShow) + "%" : "");
			
			defaultTableModel.addRow(new String[] {tempShow.getFilm().getTitle(), tempShow.getHall().getName(), 
					MyCalendar.dateFormatter(tempShow.getDate()), price});
		}
	}
	
	private GenericManager<Show> selected;
	private DiscountsManager sales;
	
	private CheckShow checkShow;
	private Comparator<Show> comparator;
	
	private JTable tableWeeklyProgram;
	private JScrollPane scrollPane;
	private MyDefaultTableModel defaultTableModel;
	private JButton addShowButton, deleteShowButton, reservePlace;
	private JCheckBox checkBoxHall, checkBoxUsable, checkBoxAvalaiblePlace;
	private JComboBox<String> comboBoxHall;
	private JPanel panelSort, panelCheck, panelReservationButton, panelAddRemove;
	private JLabel sortLabel;
	private JRadioButton sortSchedule, sortHall, sortAlpha;
	private ButtonGroup sortGroup;
	
	private static final CheckShow showIsOfThisWeek=(aShow)->aShow.isOfThisWeek(), showIsUsable=(aShow)->aShow.isUsable();
	private static final long serialVersionUID = 8460428941425341729L;
}
