package vendingMachine;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class contains test cases for the Vending Machine class
 * implemented to satisfy the bytecode branch condition of EclEmma
 * which subsumes basic condition coverage.
 
 * 
 * EclEmma implements branch condition coverage at the bytecode level,
 * this subsumes basic condition coverage (in Java bytecode each compound condition
 * is transformed into a set of goto statements that link basic conditions).
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VendingManagerTest_BasicConditionEvaluatedAdequacy {

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

	/**
	 * This test covers
	 * 
	 * line 63:   if( !validPosition(row, column) ){	"!validPosition(row, column)" is evaluated true
	 * line 135:   if ( row >= rows || column >= columns ){			"column >= columns" is evaluated true
	 * 		pay ATTENTION that to "evaluate" this condition, the condition "row >= rows" must be false
	 * 
	 *  EclEmma does not implement basic-condition coverage but compound condition coverage 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test_invalid_column_to_setPrice() {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(2));
			oneOf(dispenser).getColumns(); will(returnValue(1));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		vm.setPrice(0, 2, 70); 
		
	}
	
	
	/**
	 * This test covers
	 * 
	 * line 35:   if ( rows == 0 || columns == 0 ){	"columns == 0" is evaluated true
	 * 		pay ATTENTION that to "evaluate" this condition, the condition "row == 0" must be false, 
	 * 		this is why the condition "columns == 0" was not covered by 
	 * 
	 *  EclEmma does not implement basic-condition coverage but compound condition coverage 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test_constructor_withNoColumns() {
		context.checking(new Expectations(){
			{
			oneOf(dispenser).getRows(); will(returnValue(2));
			oneOf(dispenser).getColumns(); will(returnValue(0));
			}
		});
		
		VendingManager vm = new VendingManager(coinBox, dispenser);
		 
		
	}
	
	

}
