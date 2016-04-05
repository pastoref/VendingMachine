package vendingMachine;

import vendingMachine.NotSoldException.ErrorCodes;

/**
 * A vending manager for the vending machine class. 
 * Keeps the prices of the items to be sold, 
 * and manages the selling by calling both the Dispenser and the CoinBox
 *  
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class VendingManager {

	private CoinBox coinBox;
	private Dispenser dispenser;
	private int[][] priceMatrix;
	private int columns;
	private int rows;
	
	
	/**
	 * Builds a vending machine with the given coinbox and dispenser.
	 * @param coinBox
	 * @param dispenser
	 * @param rows
	 * @param columns
	 */
	public VendingManager( CoinBox coinBox, Dispenser dispenser ){
		this.coinBox = coinBox;
		this.dispenser = dispenser;
		this.rows = dispenser.getRows();
		this.columns = dispenser.getColumns();
		
		if ( rows == 0 || columns == 0 ){
			throw new IllegalArgumentException("Dispenser has zero items");
		}
		
		this.priceMatrix = new int[rows][columns];
		
		resetPriceMatrix();
	}
	
	/**
	 * Resets the price matrix by setting all prices to -1
	 */
	private void resetPriceMatrix() {
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				priceMatrix[i][j]=-1;
			}	
		}
	}

	/**
	 * Set the price for the given item (identified by its position in the dispenser).
	 * 
	 * @param row
	 * @param column
	 * @param priceInCents
	 */
	public void setPrice( int row, int column, int priceInCents ){
		if( !  validPosition(row, column) ){
			throw new IllegalArgumentException( "Invalid position" );
		}
		
		priceMatrix[row][column] = priceInCents;
		
		//dispenser.setPrice(row, column, priceInCents); //FAULT, this call is missing
	}
	
	/**
	 * Buy the item at the given position.
	 * Throws not sold exception if: 
	 * (1) the given position is out of the dispenser size,
	 * (2) the requested item is out of stock
	 * (3) not enough cash has been put in the dispenser
	 * (4) the item price was not configured by the operator
	 * 
	 * @param row
	 * @param column
	 * @throws NotSoldException
	 */
	public void buy( int row, int column ) throws NotSoldException{
		if( ! validProductCode( row, column ) ){ 
			throw new NotSoldException( ErrorCodes.InvalidItem );
		}
		
		if ( dispenser.isEmpty(row, column) ){
			throw new NotSoldException( ErrorCodes.ItemOutOfStock );
		}
		
		int price = priceMatrix[row][column];
		
		
		
		boolean bought = coinBox.consume( price );
		
		if ( ! bought ){
			throw new NotSoldException( ErrorCodes.MissingCash );
		}
		
		dispenser.dispense(row, column); //bug, false return not handled
		
	}

	/**
	 * Returns true if the given code correspond to a valid product, i.e. an item in the dispenser for which a price has been set.
	 * 
	 * @param row	the corresponding row in the dispenser (starts from zero)
	 * @param column the corresponding column in the dispenser (starts from zero)
	 * @return true if the given product code is valid
	 */
	private boolean validProductCode(int row, int column) {
		if ( ! validPosition(row, column) ){
			return false;
		}
		
		if ( priceMatrix[row][column] == -1 ){
			return false;
		}
		
		return true;
	}

	/**
	 * Returns true if the given position is within the boundaries of
	 * the Dispenser.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean validPosition(int row, int column) {
		if ( row >= rows || column >= columns ){
			return false;
		}
		return true;
	}
}
