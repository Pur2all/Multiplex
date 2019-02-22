package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import classes.Film;

/**
 * This class represent a dialog to add new Film
 * @see classes.Film
 * @author Francesco Migliaro
 */
public class AddFilmDialog extends JDialog
{
	/**
	 * Initialize a newly created AddFilmDialog
	 */
	public AddFilmDialog()
	{
		setTitle("Add Film");
		film=null;
		mainPanel=new JPanel();
		labelDirector=new JLabel("Director:");
		labelTitle=new JLabel("Title:");
		labelPlot=new JLabel("Plot:");
		labelYear=new JLabel("Year:");
		labelDuration=new JLabel("Duration:");
		Dimension preferredSize=new Dimension(width, height);
		director=new JTextField();
		director.setPreferredSize(preferredSize);
		title=new JTextField();
		title.setPreferredSize(preferredSize);
		year=new JTextField();
		year.setPreferredSize(preferredSize);
		duration=new JTextField();
		plot=new JTextArea();
		plot.setLineWrap(true);
		plot.setWrapStyleWord(true);
		plot.setPreferredSize(new Dimension(width, height+50));
		scrollPane=new JScrollPane(plot);
		duration.setPreferredSize(preferredSize);
		add=new JButton("Add");
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(director.getText().isEmpty() || title.getText().isEmpty() || duration.getText().isEmpty() || year.getText().isEmpty() || plot.getText().isEmpty())
					JOptionPane.showMessageDialog(rootPane, "Insert valid input", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					film=new Film(director.getText(), title.getText(), plot.getText(), Integer.parseInt(duration.getText()), Integer.parseInt(year.getText()));
					dispose();
				}
			}
		});
		cancel=new JButton("Cancel");
		cancel.addActionListener((event)->dispose());
		mainPanel.add(labelDirector);
		mainPanel.add(director);
		mainPanel.add(labelTitle);
		mainPanel.add(title);
		mainPanel.add(labelYear);
		mainPanel.add(year);
		mainPanel.add(labelDuration);
		mainPanel.add(duration);
		mainPanel.add(labelPlot);
		mainPanel.add(scrollPane);
		mainPanel.add(add);
		mainPanel.add(cancel);
		add(mainPanel);
		pack();
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the Film created
	 * <br><strong> NOTE: </strong> If the Film isn't be created this method will return null
	 * @return The Film created
	 */
	public Film getFilm()
	{
		if(film==null)
			return null;
		
		return film.clone();
	}
	
	private Film film;
	
	private JPanel mainPanel;
	private JLabel labelDirector, labelTitle, labelPlot, labelYear, labelDuration;
	private JTextField director, title, year, duration;
	private JTextArea plot;
	private JScrollPane scrollPane;
	private JButton add, cancel;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = -7111004924917967411L;
}
