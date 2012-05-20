package dp.test.xml.jaxp.sax;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dp.test.xml.jaxp.sax.entity.Stock;

/**
 * Extending default handler allows to override the necessary methods that
 * are invoked during parsing.
 * 
 * @author DPavlov
 */
public class SAXHandlerNoNamespace extends DefaultHandler
{

	private final List<Stock> stocks;

	private Stock currentStock;
	
	private boolean handleSymbol = false;
	private boolean handleQuantity = false;
	
	private boolean hasData = false;
	
	public SAXHandlerNoNamespace(List<Stock> stocks) {
		super();
		this.stocks = stocks;
	}

	/**
	 * @param uri namespace full URI
	 * @param localName simple name without namespace
	 * @param name full name including namespace
	 */
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		if ("stock".equals(name)) {
			if (this.hasData) {
				this.stocks.add(this.currentStock);
			}
			this.currentStock = null;
			this.hasData = false;
		} else if ("symbol".equals(name)) {
			this.handleSymbol = false;
		} else if ("quantity".equals(name)) {
			this.handleQuantity = false;
		}
	}

	/**
	 * @param uri namespace full URI
	 * @param localName simple name without namespace
	 * @param name full name including namespace
	 * @param attributes attributes in the node if any
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if ("stock".equals(name)) {
			this.currentStock = new Stock(); 
		} else if ("symbol".equals(name)) {
			this.handleSymbol = true;
		} else if ("quantity".equals(name)) {
			this.handleQuantity = true;
		}
	}

	/**
	 * Current text inside the node.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (handleSymbol) {
			this.currentStock.setSymbol(String.valueOf(Arrays.copyOfRange(ch, start, start + length)));
			this.hasData = true;
		} else if (handleQuantity) {
			this.currentStock.setQuantity(new BigDecimal(String.valueOf(Arrays.copyOfRange(ch, start, start + length))));
			this.hasData = true;
		}
	}
	
	
	
}
