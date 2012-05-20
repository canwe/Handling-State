package dp.test.xml.jaxp.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.xml.sax.SAXException;

import dp.test.xml.jaxp.sax.entity.Stock;

/**
 * JAXP SAX (Simple API for XML) is in fact the foundation of all java XML (even DOM
 * uses it behind the scenes). The use of SAX directly however can give you some benefits
 * in situations where you know exactly what data you will need to extract from the XML.
 * SAX is event driven, meaning it provides mechanism of reacting to event of encountering
 * nodes while the parsing of document is in progress. No data is stored in memory unless
 * you write the code to do so. The advantage is less memory consumption and maybe a little 
 * bit gain in speed since you can do a quick bail out switches in your code for the stuff 
 * you do not need. The cost is that you only have one go at this thing (i.e. it all happens 
 * while it happens), and you cannot manipulate the XML with this.
 * 
 * @author DPavlov
 */
public class JAXPSAXExample
{
	
	@Test
	public void testSAXNoNamespace() throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(false); // This setting is very important - it influences the node name
		SAXParser parser = factory.newSAXParser();
		
		final List<Stock> result = new ArrayList<Stock>();
		parser.parse(JAXPSAXExample.class.getResourceAsStream("stock.xml"), new SAXHandlerNoNamespace(result));
		
		System.out.println(result);
		
	}

	@Test
	public void testSAXWithNamespace() throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true); // This setting is very important - it influences the node name
		SAXParser parser = factory.newSAXParser();
		
		final List<Stock> result = new ArrayList<Stock>();
		parser.parse(JAXPSAXExample.class.getResourceAsStream("stock-ns.xml"), new SAXHandlerWithNamespace(result));
		
		System.out.println(result);
		
	}
	
}
