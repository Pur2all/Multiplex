package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Hall;

/**
 * This class represent a dialog to add new Hall
 * @see classes.Hall
 * @author Francesco Migliaro
 */
public class AddHallDialog extends JDialog
{
	/**
	 * Initialize a newly created AddHallDialog 
	 */
	public AddHallDialog()
	{
		super();
		setTitle("Add Hall");
		hall=null;
		mainPanel=new JPanel();
		labelName=new JLabel("Name:");
		labelNumPlaces=new JLabel("Number of places:");
		Dimension preferredSize=new Dimension(width, height);
		name=new JTextField();
		name.setPreferredSize(preferredSize);
		numPlaces=new JTextField();
		numPlaces.setPreferredSize(preferredSize);
		add=new JButton("Add");
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(name.getText().isEmpty() || numPlaces.getText().isEmpty())
					JOptionPane.showMessageDialog(rootPane, "Insert valid input", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					hall=new Hall(Integer.parseInt(numPlaces.getText()), name.getText());
					dispose();
				}
			}
		});
		cancel=new JButton("Cancel");
		cancel.addActionListener((event)->dispose());
		mainPanel.add(labelName);
		mainPanel.add(name);
		mainPanel.add(labelNumPlaces);
		mainPanel.add(numPlaces);
		mainPanel.add(add);
		mainPanel.add(cancel);
		add(mainPanel);
		pack();
		setModal(true);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the Hall created
	 * <br><strong> NOTE: </strong> If the Hall isn't be created this method will return null
	 * @return The Hall created
	 */
	public Hall getHall()
	{
		if(hall==null)
			return null;
		
		return hall.clone();
	}
	
	private Hall hall;
	
	private JPanel mainPanel;
	private JLabel labelName, labelNumPlaces;
	private JTextField name, numPlaces;
	private JButton add, cancel;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = 7184147005212222630L;
}
