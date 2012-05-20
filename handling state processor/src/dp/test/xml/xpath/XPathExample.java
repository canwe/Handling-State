package dp.test.xml.xpath;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XPath can only be use with fully loaded DOM or at least a Node.
 * 
 * @author DPavlov
 */
public class XPathExample {
	
	@Test
	public void testSimpleXPathExample() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		// XPath object is compiled for reuse
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expression = xpath.compile("/warehouse/stock/symbol/text()");
		
		// Use DOM API to get full XML document
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(XPathExample.class.getResourceAsStream("stock.xml"));

		final String result = (String) expression.evaluate(document, XPathConstants.STRING);
		
		System.out.println("STRING: " + result);
		
		final NodeList resultNL = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
		
		for (int i = 0; i < resultNL.getLength(); i++) {
			System.out.println("NODE[" + i + "]: " + resultNL.item(i).getTextContent());
		}
		
	}

}
