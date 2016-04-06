package vendingMachine;

/**
 * A dispenser for the items sold by a VendingMachine
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public interface Dispenser {
	
	/**
	 * Returns true if the given line is empty. Lines are identified by their row/column (position starts from zero).
	 *  
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isEmpty( int row, int column );
	
	/**
	 * Dispense the item at the given position.
	 * 
	 * @param row
	 * @param column
	 * @return true if the item has been dispensed. False if the line is empty.
	 */
	public boolean dispense( int row, int column );

	/**
	 * Returns the number of rows of the dispenser.
	 * 
	 * @return
	 */
	public int getRows();
	
	/**
	 * Returns the number of columns of the dispenser.
	 * 
	 * @return
	 */
	public int getColumns();

	/**
	 * Show the price on the dispenser. 
	 * Not implemented by all the dispenser hardware.
	 * 
	 * @return
	 */
	public void setPrice(int row, int column, double price);
}
