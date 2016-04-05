package vendingMachine;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A coinbox simulator, with buttons to simulate the insertion of coins.
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class HardwareCoinBoxSimulator extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel buttonPanel;
	private JButton clearButton;
	private JTextField display;
	
	public class SimulatedCoinBox extends CoinBox {

		@Override
		public boolean consume(int cents) {
			boolean value = super.consume(cents);
			if ( value == true ){
				display.setText(String.valueOf(super.getValueIn()));
			}
			return value;
		}
		
		
	}
	
	private CoinBox coinBox = new SimulatedCoinBox();

	/**
      Constructs the keypad panel.
	 */
	public HardwareCoinBoxSimulator() {  
		this.coinBox = coinBox;
		setLayout(new BorderLayout());

		// Add display field

		display = new JTextField();
		add(display, "North");

		// Make button panel

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 1));

		// Add digit buttons

		JButton button = new JButton("0.05");
		buttonPanel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coinBox.addFiveCents();
				display.setText(String.valueOf(coinBox.getValueIn()));
			}
		});
		
		button = new JButton("0.10");
		buttonPanel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coinBox.addTenCents();
				display.setText(String.valueOf(coinBox.getValueIn()));
			}
		});
		
		button = new JButton("0.20");
		buttonPanel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coinBox.addTwentyCents();
				display.setText(String.valueOf(coinBox.getValueIn()));
			}
		});
		
		button = new JButton("0.50");
		buttonPanel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coinBox.addFiftyCents();
				display.setText(String.valueOf(coinBox.getValueIn()));
			}
		});
		



		// Add clear entry button

		clearButton = new JButton("Change");
		buttonPanel.add(clearButton);

		class ClearButtonListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  
				display.setText("");
				coinBox.returnChange();
			}
		}

		clearButton.addActionListener(new ClearButtonListener());      

		add(buttonPanel, "Center");
		
		
	}

	/**
      Adds a button to the button panel 
      @param label the button label
	 */
	private void addButton(final String label)
	{  
		class DigitButtonListener implements ActionListener
		{  
			public void actionPerformed(ActionEvent event)
			{  

				// Don't add two decimal points
				if (label.equals(".") 
						&& display.getText().indexOf(".") != -1) 
					return;

				// Append label text to button
				display.setText(display.getText() + label);
			}
		}

		JButton button = new JButton(label);
		buttonPanel.add(button);
		ActionListener listener = new DigitButtonListener();
		button.addActionListener(listener);
	}

	/** 
      Gets the value that the user entered. 
      @return the value in the text field of the keypad
	 */
	public double getValue()
	{  
		return Double.parseDouble(display.getText());
	}

	/** 
      Clears the display. 
	 */
	public void clear()
	{  
		display.setText("");
	}


	/**
	 * Returns the underlying CoinBox structure used to keep trace of the coins
	 * inserted.
	 * 
	 * @return
	 */
	public CoinBox getCoinBox() {
		return coinBox;
	}
}


