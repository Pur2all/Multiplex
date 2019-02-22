package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import classes.Film;
import managers.GenericManager;
import managers.DiscountsManager;
import utils.MyDefaultTableModel;

/**
 * This class represent a panel to interact with the Discounts Manager
 * @see managers.DiscountsManager
 * @author Francesco Migliaro
 */
public class DiscountsManagerPanel extends JPanel
{
	/**
	 * Initialize a newly created DiscountsManagerPanel that use the values of the parameters
	 * @param discountsManager The discounts manager of the Cinema
	 * @param filmsManager The films manager of the Cinema
	 * @see classes.Cinema
	 */
	public DiscountsManagerPanel(DiscountsManager discountsManager, GenericManager<Film> filmsManager)
	{
		super();
		String[] columns= {"Description", "Sale rate"};
		String[][] rows=new String[discountsManager.getNumberOfDiscounts()][2];
		
		for(int i=0; i<discountsManager.getNumberOfDiscounts(); i++)
		{
			rows[i][0]=discountsManager.getDiscount(i).getDescription();
			rows[i][1]=discountsManager.getDiscount(i).getDiscountRate()+"";
		}
		defaultTableModel=new MyDefaultTableModel(rows, columns);
		salesTable=new JTable(defaultTableModel);
		salesTable.getTableHeader().setReorderingAllowed(false);
		salesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane=new JScrollPane(salesTable);
		policyActiveLabel=new JLabel();
		changeStatePolicyButton=new JButton();
		deleteSalePolicyButton=new JButton("Delete");
		deleteSalePolicyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int rowIndex=salesTable.getSelectedRow();

				if(rowIndex!=-1)
				{
					discountsManager.removeDiscount(discountsManager.getDiscount(rowIndex));
					defaultTableModel.removeRow(rowIndex);
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "First select a Sale", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		addSalePolicyButton=new JButton("Add Sale");
		addSalePolicyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				AddDiscountDialog addDiscountDialog=new AddDiscountDialog(discountsManager, filmsManager);
				
				addDiscountDialog.setVisible(true);
				if(addDiscountDialog.getDiscount()!=null)
					if(addDiscountDialog.getDiscount().getDiscountRate()!=0)
					{
						discountsManager.addDiscount(addDiscountDialog.getDiscount());
						defaultTableModel.addRow(new String[] {addDiscountDialog.getDiscount().getDescription(), addDiscountDialog.getDiscount().getDiscountRate()+""});
					}
					else
						JOptionPane.showMessageDialog(getRootPane(), "Sale not created, sale rate equal to zero isn't permitted", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		if(discountsManager.isActive())
		{
			policyActiveLabel.setText("Policy are actived");
			changeStatePolicyButton.setText("Disactive");
		}
		else
		{
			policyActiveLabel.setText("Policy are disactived");
			changeStatePolicyButton.setText("Active");
			addSalePolicyButton.setVisible(false);
			deleteSalePolicyButton.setVisible(false);
		}
		changeStatePolicyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(changeStatePolicyButton.getText().equals("Disactive"))
				{
					discountsManager.setActive(false);
					salesTable.setEnabled(false);
					policyActiveLabel.setText("Policy are disactived");
					changeStatePolicyButton.setText("Active");
					addSalePolicyButton.setVisible(false);
					deleteSalePolicyButton.setVisible(false);
				}
				else
				{
					discountsManager.setActive(true);
					salesTable.setEnabled(true);
					policyActiveLabel.setText("Policy are actived");
					changeStatePolicyButton.setText("Disactive");
					addSalePolicyButton.setVisible(true);
					deleteSalePolicyButton.setVisible(true);
				}
			}
		});
		add(scrollPane);
		add(policyActiveLabel);
		add(changeStatePolicyButton);
		add(addSalePolicyButton);
		add(deleteSalePolicyButton);
	}
	
	private JLabel policyActiveLabel;
	private JButton changeStatePolicyButton, addSalePolicyButton, deleteSalePolicyButton;
	private JTable salesTable;
	private JScrollPane scrollPane;
	private MyDefaultTableModel defaultTableModel;
	
	private static final long serialVersionUID = 6259368791344149103L;
}
