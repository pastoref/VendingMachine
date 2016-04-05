package vendingMachine.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
   A component that lets the user enter a number, using 
   a button pad labeled with digits.
 */
public class KeyPad extends JPanel {

	public interface KeyPadOkButtonListener {

		void insertedValue(int value);
		
	}
	
	class ClearButtonListener implements ActionListener {  
		public void actionPerformed(ActionEvent event){  
			clear();
		}
	}
	
	class OkButtonListener implements ActionListener {  

		public void actionPerformed(ActionEvent event) {  
			if ( digits != maxDigits ){
				return;
			}
			digits=0;
			Integer value = Integer.valueOf(display.getText());
			display.setText("");
			if ( externalOkButtonListener != null ){
				externalOkButtonListener.insertedValue(value);
			}
		}
	}

	private KeyPadOkButtonListener externalOkButtonListener;
	
	private static final long serialVersionUID = 1L;
	private JButton buyButton;

	private OkButtonListener okButtonListener;

	private int maxDigits;
	private int digits;
	
	private JPanel buttonPanel;
	private JButton clearButton;
	private JTextField display;

	private Color colorTangoBlue = new Color(114,159,207);

	/**
      Constructs the keypad panel.
	 */
	public KeyPad(int maxDigits, KeyPadOkButtonListener externalOkButtonListener){  
		this.maxDigits = maxDigits;
		this.externalOkButtonListener = externalOkButtonListener;
		
		setLayout(new BorderLayout());

		// Add display field

//		display = new JTextField();
		Font font1 = new Font("Hiragino Maru Gothic Pro", Font.BOLD, 38);
	    
	    display = new JTextField();
	    //display.setLocation(5, 5);
	    display.setSize(150,40);
	    
	    display.setFont(font1);
	    display.setHorizontalAlignment(JTextField.RIGHT);
	    display.setBackground(colorTangoBlue);
		display.setForeground(Color.WHITE);
		display.setEditable(false);
		display.setBorder(null);
		add(display, "North");

		// Make button panel

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 3));

		// Add digit buttons



		addButton("1");
		addButton("2");
		addButton("3");
		addButton("4");
		addButton("5");
		addButton("6");
		addButton("7");
		addButton("8");
		addButton("9");

      


		// Add clear entry button

		
		Icon ce = new ImageIcon( "img/ce.gif" );
		clearButton = new JButton(ce);
		clearButton.setSize(50, 50);
		clearButton.setBorderPainted(false);
		buttonPanel.add(clearButton);

   
		clearButton.addActionListener(new ClearButtonListener());      

		addButton("0");
		
		Icon ok = new ImageIcon( "img/ok.gif" );
		buyButton = new JButton(ok);
		buyButton.setSize(50, 50);
		buyButton.setBorderPainted(false);
		
		
		buttonPanel.add(buyButton);
		okButtonListener = new OkButtonListener();
		buyButton.addActionListener(okButtonListener);
		
		buttonPanel.setBackground(colorTangoBlue);
		add(buttonPanel, "Center");
		
		setBackground(new Color(114,159,207));
	}

	/**
      Adds a button to the button panel 
      @param label the button label
	 */
	private void addButton(final String name)
	{  
		class DigitButtonListener implements ActionListener{  
			public void actionPerformed(ActionEvent event){  
				if ( digits >= maxDigits ){
					return;
				}
				if ( digits == 0 ){
					display.setText("");
				}
				digits++;
				
				// Append label text to button
				display.setText(display.getText() + name);
			}
		}
		
		
		Icon two = new ImageIcon( "img/"+name+".gif" );
		JButton button = new JButton(two);
		button.setSize(50, 50);
		button.setBorderPainted(false);
		
		buttonPanel.add(button);
		ActionListener listener = new DigitButtonListener();
		button.addActionListener(listener);
	}



	/** 
      Clears the display. 
	 */
	public void clear(){  
		digits=0;
		display.setText("");
	}



	public void setText(String msg) {
		digits=0;
		display.setText(msg);
	}
}


