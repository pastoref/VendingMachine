package vendingMachine;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Stub that simulates the dispenser by means of a GUI component
 * that shows the number of items still in the dispenser.
 * 
 * @author Fabrizio Pastore - pastore@disco.unimib.it
 *
 */
public class DispenserGUIStub implements Dispenser {

	private static final String EURO = "\u20ac";
	
	private JTextField[] area;
	private JTextField[] prices;
	int rows;
	int columns;

	public DispenserGUIStub(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		JFrame frame = new JFrame(){

			{
				setLayout(new GridLayout(rows*2,columns));
				area = new JTextField[rows*columns];
				prices = new JTextField[rows*columns];
				
				for ( int i = 0; i < rows; i++){
					for ( int j = 0; j < columns; j++){
						area[columns*i+j]= new JTextField(String.valueOf(columns*i+j));
						area[columns*i+j].setSize(30,30);
						area[columns*i+j].setHorizontalAlignment(JTextField.RIGHT);
						add(area[columns*i+j]);
					}
					for ( int j = 0; j < columns; j++){
						prices[columns*i+j]= new JTextField(EURO+":");
						prices[columns*i+j].setBackground(Color.BLACK);
						prices[columns*i+j].setForeground(Color.WHITE);
						prices[columns*i+j].setSize(20,30);
						prices[columns*i+j].setHorizontalAlignment(JTextField.LEFT);
						add(prices[columns*i+j]);
					}
				}
				setSize(200, 300);
				
				
			}
		};
		frame.setTitle("DispenserStub");      
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setLocation(600, 0);
	}
	
	@Override
	public boolean isEmpty(int row, int column) {
		if( getValue(row,column) == 0 ){
			return true;
		}
		return false;
	}

	private int getValue(int row, int column) {
		return Integer.valueOf(area[row*columns+column].getText());
	}

	@Override
	public boolean dispense(int row, int column) {
		if( isEmpty(row, column) ){
			return false;
		}
		
		int value = getValue(row, column);
		
		setValue( row, column, value -1 );
		
		return true;
	}

	private void setValue(int row, int column, int i) {
		area[row*columns+column].setText(String.valueOf(i));
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public void setPrice(int row, int column, double price) {
		String text = String.format("%1$,.2f", price);
		prices[columns*row+column].setText(text);
	}

}
