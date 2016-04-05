package vendingMachine;

/**
 * A class that manages a CoinBox. 
 * It is expected to be used by: 
 * (1) the hardware coinbox driver to indicate that a coin has bee introduced;
 * (2) the VendingMachine to check if enough money has been introduced
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class CoinBox {
	private int centsInserted;
	
	public void addTenCents(){
		centsInserted += 10;
	}

	public void addFiveCents(){
		centsInserted += 5;
	}

	public void addTwentyCents(){
		centsInserted += 20;
	}

	public void addFiftyCents(){
		centsInserted += 50;
	}
	
	public void addOneEuro(){
		centsInserted += 100;
	}
	
	public void addTwoEuros(){
		centsInserted += 200;
	}
	
	public void add(int cents){
		centsInserted += cents;
	}
	
	/**
	 * Consume the given amount of cents. Returns false if the amount of cents already inserted is lower than the given amount.
	 * The amount of cents still in the coinbox is updated accordingly.
	 * 
	 * @param cents	amount of cents to consume, expected to be positive or zero.
	 * @return
	 */
	public boolean consume( int cents ){
		if ( cents < 0 ){  //defensive programming, check precondition
			throw new IllegalArgumentException("Negative amount");
		}
		
		if ( centsInserted < cents ){
			return false;
		}
		
		centsInserted -= cents;
		
		assert( centsInserted >= 0 );
		return true;
	}
	
	/**
	 * Returns the amount of change still in the box.
	 * 
	 * @return
	 */
	public int returnChange(){
		int change = centsInserted;
		centsInserted = 0;
		return change;
	}

	/**
	 * Returns the amount of change in the box
	 * @return
	 */
	public int getValueIn() {
		return centsInserted;
	}
}
