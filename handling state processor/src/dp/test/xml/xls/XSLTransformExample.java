package dp.test.xml.xls;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;


public class XSLTransformExample
{

	@Test
	public void testSimpleTransformationExample() throws TransformerException {
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(
				new StreamSource(XSLTransformExample.class.getResourceAsStream("stock.xsl")));
		
		final StringWriter writer = new StringWriter();
		transformer.transform(
				new StreamSource(XSLTransformExample.class.getResourceAsStream("stock.xml")), 
				new StreamResult(writer));
		
		System.out.println(writer.toString());
		
	}
	
}
