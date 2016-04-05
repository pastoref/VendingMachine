package vendingMachine.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import vendingMachine.NotSoldException;
import vendingMachine.NotSoldException.ErrorCodes;
import vendingMachine.VendingManager;
import vendingMachine.gui.KeyPad.KeyPadOkButtonListener;

/**
 * Vending Mahcine Frame, acts as a controller for the KeyPad and the 
 * VendingManager.
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VMFrame extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private KeyPad pad;
	private JTextArea display;

	private static final int FRAME_WIDTH = 250;
	private static final int FRAME_HEIGHT = 400;

	private VendingManager vm;
	private OkButtonListener okButtonListener;
	/**
    Constructs the user interface of the ATM frame.
	 */
	public VMFrame(VendingManager vm) {  
		this.vm = vm;
		okButtonListener = new OkButtonListener();
		// Construct components
		pad = new KeyPad(2,okButtonListener);

		

		setLayout(new FlowLayout());
		add(pad);
		

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		
	}

	/** 
    Updates display message.
	 */
	public void updateState(String msg)
	{  
		pad.setText(msg);
	}

	private class OkButtonListener implements KeyPadOkButtonListener {  
		@Override
		public void insertedValue(int padValue) {


			try {


				int row = padValue / 10;
				int column = padValue % 10;

				vm.buy(row, column);
			} catch (NotSoldException e) {
				if ( e.getErrorCode() == ErrorCodes.MissingCash ){
					updateState("More coins");
				} else {
					updateState("Not valid");
				}
			}
		}


	}


}

