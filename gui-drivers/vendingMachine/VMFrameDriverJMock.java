package vendingMachine;

import java.awt.Color;

import javax.swing.JFrame;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;

import vendingMachine.NotSoldException.ErrorCodes;
import vendingMachine.gui.VMFrame;

/**
 * This class is used to manually test the GUI in isolation, ie.
 * without caring about the implementation of the VendingManager.
 *  
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VMFrameDriverJMock {
	
	

	public static void main(String[] args) throws NotSoldException{
		Synchroniser synchroniser = new Synchroniser();


		Mockery context = new Mockery() {{
	        setImposteriser(ClassImposteriser.INSTANCE);
	        
	        setThreadingPolicy(synchroniser);
	    }};
		
		
		VendingManager vm = context.mock(VendingManager.class);

		context.checking(new Expectations(){
			{
				allowing(vm).buy(0, 0);
				allowing(vm).buy(0, 1); will(throwException(new NotSoldException(ErrorCodes.InvalidItem)));
				allowing(vm).buy(1, 0); will(throwException(new NotSoldException(ErrorCodes.ItemOutOfStock)));
				allowing(vm).buy(1, 1); will(throwException(new NotSoldException(ErrorCodes.MissingCash)));
			}
		});
		
		
		

		JFrame frame = new VMFrame(vm);
		frame.setTitle("Vending Machine");      
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(0, 0);
		frame.setBackground(new Color(114,159,207));
		
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { context.assertIsSatisfied(); }
		});
		
	}


}
