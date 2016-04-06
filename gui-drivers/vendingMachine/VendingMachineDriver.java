package vendingMachine;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import vendingMachine.gui.VMFrame;

/**
 * Class used to test the VendingMachine by simulating the
 * real hardware using GUI components.
 * 
 * This example shows that the GUI test allows to detect that prices are not
 * as doubles, so they are shown as 10.00 20.00 ... 120.00 instead of 0.10 0.20 ...
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VendingMachineDriver {
	
	static {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
	}

	public static void main(String[] args){  
		
		
		//Create the dispenser GUI stub
		DispenserGUIStub dispenser = new DispenserGUIStub(3,2);
		
		//Craete the coinbox
		HardwareCoinBoxSimulator coinSimulator = new HardwareCoinBoxSimulator();
		CoinBox cb = coinSimulator.getCoinBox();
		
		//Creates the vending manager
		VendingManager vm = new VendingManager(cb, dispenser);

		//populate with prices
		vm.setPrice(0, 0, 10);
		vm.setPrice(0, 1, 20);
		vm.setPrice(1, 0, 30);
		vm.setPrice(1, 1, 40);
		vm.setPrice(2, 0, 60);
		vm.setPrice(2, 1, 120);
		
		
		
		//Create the VMFrame
		JFrame frame = new VMFrame(vm);
		frame.setTitle("Vending Machine");      
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(0, 0);
		frame.setBackground(new Color(114,159,207));
		
		JFrame coinFrame = new JFrame(){
			{
				setLayout(new FlowLayout());
				add( coinSimulator );
				
				setSize(100, 300);
				
				
			}
		};
		coinFrame.setTitle("HardwareCoinBoxSimulator");      
		coinFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		coinFrame.setVisible(true);
		coinFrame.setLocation(400, 0);
		
	}


}
