package dp.test.xml.jaxp.dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import dp.test.xml.xpath.XPathExample;

/**
 * JAXP Dom allows to load the full DOM model into memory straight away.
 * The benefits are that you can work with the in memory loaded document
 * as you wish, which is good for when you do not know exactly what you will need 
 * from the document (e.g. you want to use XPath to extract the data later on) 
 * or if you are likely to manipulate the document (i.e. edit it). This all comes 
 * at the cost of memory space.
 * 
 * @author DPavlov
 */
public class JAXPDomExample
{

	@Test
	public void testDomLoad() throws ParserConfigurationException, SAXException, IOException {
		
		// Use DOM API to get full XML document
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(XPathExample.class.getResourceAsStream("stock.xml"));
		
		final StringBuilder out = new StringBuilder();
		appendNode(out, document, 0); // Document is in itself also a Node
		
		System.out.println(out.toString());
		
	}
	
	private void appendNode(final StringBuilder toAppendTo, final Node node, final int deep) {
		
		for (int i = 0; i < deep - 1; i++) {
			toAppendTo.append(" |");
		}
		toAppendTo.append(" +->").append(node.getNodeName());
		if (node.getChildNodes().getLength() > 0) {
			toAppendTo.append('\n');
			for (int ii = 0; ii < node.getChildNodes().getLength(); ii++) {
				appendNode(toAppendTo, node.getChildNodes().item(ii), deep + 1);
			}
		} else {
			toAppendTo.append(": ").append(node.getTextContent().replaceAll("\n", "")).append('\n');
		}
		
	}
	
}
