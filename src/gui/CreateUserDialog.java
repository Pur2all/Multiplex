package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import classes.Client;
import classes.User;

/**
 * This class represent a dialog to create new User
 * @see classes.User
 * @see classes.Client
 * @author Francesco Migliaro
 */
public class CreateUserDialog extends JDialog
{
	/**
	 * Initialize new CreateUserDialog
	 */
	public CreateUserDialog()
	{
		super();
		setTitle("Create User");
		user=null;
		panel=new JPanel();
		labelName=new JLabel("Name:");
		labelSurname=new JLabel("Surname:");
		labelAge=new JLabel("Age:");
		labelEmail=new JLabel("Email:");
		labelPassword=new JLabel("Password:");
		name=new JTextField();
		Dimension preferredSize=new Dimension(width, height);
		name.setPreferredSize(preferredSize);
		surname=new JTextField();
		surname.setPreferredSize(preferredSize);
		age=new JTextField();
		age.setPreferredSize(preferredSize);
		email=new JTextField();
		email.setPreferredSize(preferredSize);
		password=new JTextField();
		password.setPreferredSize(preferredSize);
		managerRadioButton=new JRadioButton("Manager");
		clientRadioButton=new JRadioButton("Client");
		buttonGroup=new ButtonGroup();
		buttonGroup.add(clientRadioButton);
		buttonGroup.add(managerRadioButton);
		clientRadioButton.setSelected(true);
		create=new JButton("Register");
		create.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(name.getText().isEmpty() || surname.getText().isEmpty() || age.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty())
					JOptionPane.showMessageDialog(rootPane, "Insert valid input", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					if(clientRadioButton.isSelected())
						user=new Client(Integer.parseInt(age.getText()), name.getText(), surname.getText(), email.getText(), password.getText());
					else
						user=new User(Integer.parseInt(age.getText()), name.getText(), surname.getText(), email.getText(), password.getText(), enums.Type.MANAGER);
					dispose();
				}
			}
		});
		cancel=new JButton("Cancel");
		cancel.addActionListener((event)->dispose());
		panel.add(labelName);
		panel.add(name);
		panel.add(labelSurname);
		panel.add(surname);
		panel.add(labelAge);
		panel.add(age);
		panel.add(labelEmail);
		panel.add(email);
		panel.add(labelPassword);
		panel.add(password);
		panel.add(clientRadioButton);
		panel.add(managerRadioButton);
		panel.add(create);
		panel.add(cancel);
		add(panel);
		pack();
		setModal(true);
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return the User created
	 * <br><strong> NOTE: </strong> If the User isn't be created this method will return null
	 * @return The User created
	 */
	public User getClient()
	{
		if(user==null)
			return null;
		
		return user.clone();
	}
	
	private User user;
	
	private JPanel panel;
	private JLabel labelName, labelSurname, labelAge, labelEmail, labelPassword;
	private JTextField name, surname, age, email, password;
	private JButton create, cancel;
	private JRadioButton managerRadioButton, clientRadioButton;
	private ButtonGroup buttonGroup;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = 1714325669339595639L;
}
