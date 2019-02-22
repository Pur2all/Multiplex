package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import managers.AccountsManager;
import classes.User;

/**
 * This class represent a dialog to interact with the Login Manager
 * @see managers.AccountsManager
 * @author Francesco Migliaro
 */
public final class LoginDialog extends JDialog
{
	/**
	 * Initialize newly created LoginDialog
	 * @param accountsManager The login manager of the Cinema
	 * @see classes.Cinema
	 */
	public LoginDialog(AccountsManager accountsManager)
	{
		super();
		setTitle("Login");
		auth=false;
		panelLogin=new JPanel();
		labelEmail=new JLabel("Email:");
		labelPassword=new JLabel("Password");
		buttonLogin=new JButton("Login");
		buttonLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String username=textFieldEmail.getText(), password=textFieldPassword.getText();
				
				if(username.isEmpty() || password.isEmpty())
					JOptionPane.showMessageDialog(rootPane, "Please insert valid user and password", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					User tempUser=accountsManager.login(username, password);
					
					if(tempUser!=null)
					{
						user=tempUser.clone();
						auth=true;
						dispose();
					}
					else
						JOptionPane.showMessageDialog(rootPane, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonCancel=new JButton("Cancel");
		buttonCancel.addActionListener((event)->dispose());
		buttonCreateUser=new JButton("Sign in");
		buttonCreateUser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				CreateUserDialog createUserDialog=new CreateUserDialog();
				
				createUserDialog.setVisible(true);
				User client=createUserDialog.getClient();
				if(client!=null)
					accountsManager.createUser(client.getAge(), client.getName(), client.getSurname(), client.getEmail(), client.getPassword(), client.getType());
			}
		});
		textFieldEmail=new JTextField();
		textFieldEmail.setPreferredSize(new Dimension(width, height));
		textFieldPassword=new JTextField();
		textFieldPassword.setPreferredSize(new Dimension(width, height));
		panelLogin.add(labelEmail);
		panelLogin.add(textFieldEmail);
		panelLogin.add(labelPassword);
		panelLogin.add(textFieldPassword);
		panelLogin.add(buttonLogin);
		panelLogin.add(buttonCreateUser);
		panelLogin.add(buttonCancel);
		add(panelLogin);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		Dimension screenSize=getToolkit().getScreenSize();
		setLocation((int)(screenSize.width-getWidth())/2, (int)(screenSize.height-getHeight())/2);
	}
	
	/**
	 * Return if the User is authenticated
	 * @return true if the User is authenticated, false otherwise
	 */
	public boolean userIsAuthenticated()
	{
		return auth;
	}
	
	/**
	 * Return the User authenticated
	 * @return The User authenticated
	 */
	public User getUser()
	{
		return user.clone();
	}
	
	private boolean auth;	
	private User user;
	
	private JPanel panelLogin;
	private JLabel labelEmail, labelPassword;
	private JTextField textFieldEmail, textFieldPassword;
	private JButton buttonLogin, buttonCancel, buttonCreateUser;
	
	private static final int width=150, height=25;
	private static final long serialVersionUID = -5100212021194753173L;
}
