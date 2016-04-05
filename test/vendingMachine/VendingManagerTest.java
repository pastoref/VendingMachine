package vendingMachine;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Auto;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class tests the Vending Machine class
 * 

**** Test design specifications ****
*
Test cases have been derived using the category partition method.
TSL tool have been used to identify test cases.
Categories/values follows:

Object state:
        Line items:
        Zero.   [error]
        One.    [single]
        Many.

Invocations:
        Requested product:
        Available.
        Out of stock.
        Wrong position.
        Price not configured.
        Not enough coins.


***** Test input specifications *****
*
Test Case 1             <error>
   Line items :  Zero


Test Case 2             <single>
   Line items :  One


Test Case 3             (Key = 3.1.)
   Line items        :  Many
   Requested product :  Available


Test Case 4             (Key = 3.2.)
   Line items        :  Many
   Requested product :  Out of stock


Test Case 5             (Key = 3.3.)
   Line items        :  Many
   Requested product :  Wrong position


Test Case 6             (Key = 3.4.)
   Line items        :  Many
   Requested product :  Price not configured

Test Case 7             (Key = 3.5.)
   Line items        :  Many
   Requested product :  Not enough coins

 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VendingManagerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery() {{
	      setImposteriser(ClassImposteriser.INSTANCE);
	   }};
	   
	   @Mock Dispenser dispenser;
	   @Mock CoinBox coinBox;
	   
	   
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = IllegalArgumentException.class)
	public void test01_NoItems() {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(0));
			oneOf(dispenser).getColumns(); will(returnValue(0));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		
	}
	
	
	@Test
	public void test02_OneItem_Available() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(1));
			oneOf(dispenser).getColumns(); will(returnValue(1));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70);
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(0, 0); will(returnValue(false));
				oneOf(coinBox).consume(70); will(returnValue(true));
				oneOf(dispenser).dispense(0, 0); will(returnValue(true));
			}
		});
		
		vm.buy(0, 0);
		
	}
	
	
	@Test
	public void test02_OneItem_Available_Other() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(1));
			oneOf(dispenser).getColumns(); will(returnValue(1));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70);
		
		context.checking(new Expectations(){
			{
				//The order does not count
				//oneOf(dispenser).isEmpty(0, 0); will(returnValue(false));
				oneOf(coinBox).consume(70); will(returnValue(true));
				oneOf(dispenser).dispense(0, 0); will(returnValue(true));
				oneOf(dispenser).isEmpty(0, 0); will(returnValue(false));
			}
		});
		
		vm.buy(0, 0);
		
	}
	
	
	@Auto Sequence vmCalls;
	@Test
	public void test02_OneItem_Available_Better() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(1)); inSequence(vmCalls);
			oneOf(dispenser).getColumns(); will(returnValue(1)); inSequence(vmCalls);
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70);
		
		context.checking(new Expectations(){
			{
				//The order does not count
				oneOf(dispenser).isEmpty(0, 0); will(returnValue(false)); inSequence(vmCalls);
				oneOf(coinBox).consume(70); will(returnValue(true)); inSequence(vmCalls);
				oneOf(dispenser).dispense(0, 0); will(returnValue(true)); inSequence(vmCalls);
			}
		});
		
		vm.buy(0, 0);
		
	}
	
	
	
	@Test
	public void test03_ManyItems_Available() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70);
		vm.setPrice(1, 0, 70); vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 70); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(2, 3); will(returnValue(false));
				oneOf(coinBox).consume(110); will(returnValue(true)); 
				oneOf(dispenser).dispense(2, 3); will(returnValue(true));
				
			}
		});
		
		vm.buy(2, 3);
		
	}


	@Test //comment out @Test if you want to see this test in action
	public void test03_ManyItems_Available_Relaxed() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70); 
		vm.setPrice(1, 0, 70); vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 70); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(with(any(Integer.class)), with(any(Integer.class))); will(returnValue(false));
				oneOf(coinBox).consume(with(any(Integer.class))); will(returnValue(true)); 
				oneOf(dispenser).dispense(with(any(Integer.class)), with(any(Integer.class))); will(returnValue(true));
			}
		});
		
		vm.buy(2, 3);
	}
	
	@Auto Sequence s3;
	//@Test //comment out @Test if you want to see this test in action
	public void test03_ManyItems_Available_Sequence() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70); 
		vm.setPrice(1, 0, 70); vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 70); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(2, 3); will(returnValue(false)); inSequence(s3);
				oneOf(coinBox).consume(110); will(returnValue(true)); inSequence(s3);
				oneOf(dispenser).dispense(2, 3); will(returnValue(true)); inSequence(s3);
			}
		});
		
		vm.buy(2, 3);
	}
	
	
	@Test
	public void test04_ManyItems_OutOfStock() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70);
		vm.setPrice(1, 0, 70); vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 175); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(1, 2); will(returnValue(true));
				
			}
		});
		
		
		try {
			vm.buy(1, 2);
		} catch ( NotSoldException e ){
			//We check that the error code is the expected one...
			assertEquals(NotSoldException.ErrorCodes.ItemOutOfStock, e.getErrorCode());
		}
		
	}
	
	
	@Test
	public void test05_ManyItems_WrongPosition() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70);
		vm.setPrice(1, 0, 70); vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 175); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		
		
		context.checking(new Expectations(){
			{
				//we do not expect any call
			}
		});
		
		try {
			vm.buy(3, 4);
		} catch ( NotSoldException e ){
			//We check that the error code is the expected one...
			assertEquals(NotSoldException.ErrorCodes.InvalidItem, e.getErrorCode());
		}
		
	}
	
	@Test
	public void test06_ManyItems_PriceNotConfigured() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70);
								vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 175); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		
		
		context.checking(new Expectations(){
			{
				//we do not expect any call
			}
		});
		
		try {
			vm.buy(1, 0);
		} catch ( NotSoldException e ){
			//We check that the error code is the expected one...
			assertEquals(NotSoldException.ErrorCodes.InvalidItem, e.getErrorCode());
		}
		
	}
	
	
	@Test
	public void test07_ManyItems_NotEnoughCoins() throws NotSoldException {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(3));
			oneOf(dispenser).getColumns(); will(returnValue(4));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 0, 70); vm.setPrice(0, 1, 70); vm.setPrice(0, 2, 70); vm.setPrice(0, 3, 70);
								vm.setPrice(1, 1, 50); vm.setPrice(1, 2, 175); vm.setPrice(1, 3, 70);
		vm.setPrice(2, 0, 70); vm.setPrice(2, 1, 70); vm.setPrice(2, 2, 90); vm.setPrice(2, 3, 110);
		
		
		
		context.checking(new Expectations(){
			{
				oneOf(dispenser).isEmpty(2, 1); will(returnValue(false));
				oneOf(coinBox).consume(70); will(returnValue(false));
				
			}
		});
		
		try {
			vm.buy(2, 1);
		} catch ( NotSoldException e ){
			//We check that the error code is the expected one...
			assertEquals(NotSoldException.ErrorCodes.MissingCash, e.getErrorCode());
		}
		
	}

}
