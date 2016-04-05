package vendingMachine;

/**
 * Exception thrown by the VendingManager to indicate that an item has 
 * not been sold for some reason.
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class NotSoldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum ErrorCodes { MissingCash, InvalidItem, ItemOutOfStock }

	private ErrorCodes errorCode; 
	
	public NotSoldException(ErrorCodes errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}
	
	

}
