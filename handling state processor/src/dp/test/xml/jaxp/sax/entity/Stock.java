package dp.test.xml.jaxp.sax.entity;

import java.math.BigDecimal;

/**
 * Example entity.
 * 
 * @author DPavlov
 */
public class Stock
{

	private String symbol;
	private BigDecimal quantity;
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String toString() {
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Stock ( "
	        + super.toString() + TAB
	        + "symbol = " + this.symbol + TAB
	        + "quantity = " + this.quantity + TAB
	        + " )";
	
	    return retValue;
	}
	
}
