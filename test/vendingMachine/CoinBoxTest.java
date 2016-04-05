package vendingMachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for CoinBox.
 * 
 * Tests have been derived by using category partion.
 * Category partition parameters and test specifications follows
 * 
 * 
 * 
 * 
 ****** Test design specifications or method "consume(int)" *****
 *Test cases have been derived by applying the category partition method. 
 *The TSL tool have been used to automatically identify test cases (see folder support/categoryPartitionTool-TSL).
 *
 *Categories and values (according to tsl format)
 
Object state:
        Cents inserted:
        Zero.   [property noCash]
        Many.

Invocations:
        Number of calls to method consume:
        One.    [property consuming]
        Many.   [property consuming]

        Cents to consume:
        Zero.   [single]        [if !noCash && consuming]
        Same as inserted.       [if consuming]
        More than inserted.     [if consuming]
        Less than inserted.     [if !noCash && consuming]
        Zero.   [error]        [if consuming]


  
 * 
 ****** Test case specifications for method "consume(int)" *****
Test Case 1             <single>
   Cents to consume :  Zero
   
Test Case 2             <error>
   Cents to consume :  Negative   


Test Case 3             (Key = 1.1.2.)
   Cents inserted          :  Zero
   Number of consume calls :  One
   Cents to consume        :  Same as inserted


Test Case 4             (Key = 1.1.3.)
   Cents inserted          :  Zero
   Number of consume calls :  One
   Cents to consume        :  More than inserted


Test Case 5             (Key = 1.2.2.)
   Cents inserted          :  Zero
   Number of consume calls :  Many
   Cents to consume        :  Same as inserted


Test Case 6             (Key = 1.2.3.)
   Cents inserted          :  Zero
   Number of consume calls :  Many
   Cents to consume        :  More than inserted


Test Case 7             (Key = 2.1.2.)
   Cents inserted          :  Many
   Number of consume calls :  One
   Cents to consume        :  Same as inserted


Test Case 8             (Key = 2.1.3.)
   Cents inserted          :  Many
   Number of consume calls :  One
   Cents to consume        :  More than inserted



Test Case 9             (Key = 2.1.4.)
   Cents inserted          :  Many
   Number of consume calls :  One
   Cents to consume        :  Less than inserted


Test Case 10             (Key = 2.2.2.)
   Cents inserted          :  Many
   Number of consume calls :  Many
   Cents to consume        :  Same as inserted


Test Case 11            (Key = 2.2.3.)
   Cents inserted          :  Many
   Number of consume calls :  Many
   Cents to consume        :  More than inserted


Test Case 12            (Key = 2.2.4.)
   Cents inserted          :  Many
   Number of consume calls :  Many
   Cents to consume        :  Less than inserted

****** Test design specifications for method returnChange()******
Category partition has been used.

Object state:
        Cents inserted:
        Zero.   [property noCash]
        Many.

Invocations:
        Number of calls to returnChange:
        One.
        Many.

 ****** Test case specifications for method "returnChange()" *****

Test Case 1             (Key = 1.1.)
   Cents inserted                  :  Zero
   Number of calls to returnChange :  One


Test Case 2             (Key = 1.2.)
   Cents inserted                  :  Zero
   Number of calls to returnChange :  Many


Test Case 3             (Key = 2.1.)
   Cents inserted                  :  Many
   Number of calls to returnChange :  One


Test Case 4             (Key = 2.2.)
   Cents inserted                  :  Many
   Number of calls to returnChange :  Many


****** Test design specifications for method getValue()******

Method getValue should be tested both when the CoinBox is empty, and when it has some coins inside.

Tests test01_ManyCoins_NoVend and test03_NoCoins_EmptyVend() can be used for this purpose.

 * 
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class CoinBoxTest {
	CoinBox cb;
	
	@Before
	public void setUp() throws Exception {
		cb = new CoinBox();
	}

	@After
	public void tearDown() throws Exception {
	}


	
	@Test
	public void test_consume_01_ManyCoins_NoVend() {
		cb.addFiftyCents();
		cb.addFiveCents();

		boolean bought = cb.consume(0);
		assertTrue(bought);

		//A call to getValueIn is added to inspect the internal state of the 
		//coinbox, i.e. the amount of values still inside
		assertEquals( 55, cb.getValueIn() );

	}
	

	@Test( expected = IllegalArgumentException.class)
	public void test_consume_02_Negative() {
		cb.addFiftyCents();
		cb.addFiveCents();

		boolean bought = cb.consume(-5);
		
	}
	
	@Test
	public void test_consume_03_NoCoins_EmptyVend() {
		
		boolean bought = cb.consume(0);
		assertTrue(bought);
		
		assertEquals( 0, cb.getValueIn() );
	}
	

	@Test
	public void test_consume_04_NoCoins_FailedVend() {
		
		boolean bought = cb.consume(55);
		assertFalse(bought);
		
		assertEquals(0, cb.getValueIn() );
	}
	
	
	@Test
	public void test_consume_05_NoCoins_ManyEmptyVends() {
		
		boolean bought = cb.consume(0);
		assertTrue(bought);
		
		bought = cb.consume(0);
		assertTrue(bought);
		
		assertEquals(0, cb.getValueIn() );
	}
	
	
	@Test
	public void test_consume_06_NoCoins_ManyFailedVends() {
		
		boolean bought = cb.consume(30);
		assertFalse(bought);
		
		bought = cb.consume(50);
		assertFalse(bought);
		
		assertEquals(0, cb.getValueIn() );
	}

	
	@Test
	public void test_consume_07_ManyCoins_SingleVend_NoRest() {
		cb.addFiftyCents();
		cb.addTwentyCents();
		
		
		boolean bought = cb.consume(70);
		assertTrue(bought);
		
		assertEquals(0, cb.getValueIn() );
	}
	
	

	
	
	@Test
	public void test_consume_08_ManyCoins_SingleFailedVend_WithRest() {
		cb.addFiftyCents();
		cb.addTwentyCents();
		
		
		boolean bought = cb.consume(105);
		assertFalse(bought);
		
		assertEquals(70, cb.getValueIn() );
	}
	
	
	
	@Test
	public void test_consume_09_ManyCoins_SingleVend_WithRest() {
		cb.addFiftyCents();
		cb.addTwentyCents();
		
		
		boolean bought = cb.consume(55);
		assertTrue(bought);
		
		assertEquals(15, cb.getValueIn() );
	}
	
	
	@Test
	public void test_consume_10_ManyCoins_ManyVends_NoRest() {
		cb.addFiveCents();
		cb.addTenCents();
		cb.addTwentyCents();
		cb.addFiftyCents();
		cb.addFiftyCents();
		cb.addFiftyCents();
		cb.addOneEuro();
		
		boolean bought = cb.consume(85);
		assertTrue(bought);
		
		bought = cb.consume(50);
		assertTrue(bought);
		
		bought = cb.consume(150);
		assertTrue(bought);
		
		assertEquals(0, cb.getValueIn() );
	}
	
	@Test
	public void test_consume_11_ManyCoins_ManyFailedVends() {
		cb.addFiveCents();
		cb.addTenCents();
		cb.addTwentyCents();
		cb.addFiftyCents();
		cb.addFiftyCents();
		cb.addFiftyCents();

		
		boolean bought = cb.consume(35);
		assertTrue(bought);
		
		bought = cb.consume(155);
		assertFalse(bought);
		
		bought = cb.consume(250);
		assertFalse(bought);
		
		assertEquals(150, cb.getValueIn() );
	}
	
	@Test
	public void test_consume_12_ManyCoins_ManyVends_WithRest() {
		cb.addFiveCents();
		cb.addTenCents();
		cb.addTwentyCents();
		cb.addFiftyCents();
		cb.addTwoEuros();
		
		boolean bought = cb.consume(30);
		assertTrue(bought);
		
		bought = cb.consume(50);
		assertTrue(bought);
		
		bought = cb.consume(150);
		assertTrue(bought);
		
		assertEquals(55, cb.getValueIn() );
	}
	

	@Test
	public void test_returnChange_01_NoCoins_OneReturn() {
		
		int change = cb.returnChange();
		assertEquals(0, change);
	}
	
	
	@Test
	public void test_returnChange_02_NoCoins_manyReturn() {
		
		int change = cb.returnChange();
		assertEquals(0, change);
		
		change = cb.returnChange();
		assertEquals(0, change);
		
		//a call to getValueIn is added to check the internal state of the object
		assertEquals(0, cb.getValueIn());
	}
	
	@Test
	public void test_returnChange_03_ManyCoins_OneReturn() {
		cb.addFiftyCents();
		cb.addFiveCents();
		
		int change = cb.returnChange();
		assertEquals(55, change);
		
		assertEquals(0, cb.getValueIn());
	}
	
	@Test
	public void test_returnChange_04_ManyCoins_manyReturn() {
		cb.addFiftyCents();
		cb.addFiveCents();
		
		int change = cb.returnChange();
		assertEquals(55, change);
		
		
		change = cb.returnChange();
		assertEquals(0, change);
		
		assertEquals(0, cb.getValueIn());
	}
	

	
}
