package dp.test.xml.xsd;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.xml.sax.SAXException;


public class XSDValidateExample
{
	
	@Test
	public void testSimpleSchema() throws SAXException {
	
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = factory.newSchema(XSDValidateExample.class.getResource("order.xsd"));
		Validator validator = schema.newValidator();
				
		StringWriter resWriter = new StringWriter();
		Result results = new StreamResult(resWriter);
		try {
			validator.validate(new StreamSource(XSDValidateExample.class.getResourceAsStream("order.xml")), results);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@Test
	public void testSchemaWithReferences() throws SAXException {
		
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schemaRef = factory.newSchema(XSDValidateExample.class.getResource("order-using-refs.xsd"));
		Validator validatorRef = schemaRef.newValidator();
		
		StringWriter resRefWriter = new StringWriter();
		Result resultsRef = new StreamResult(resRefWriter);
		try {
			validatorRef.validate(new StreamSource(XSDValidateExample.class.getResourceAsStream("order.xml")), resultsRef);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	@Test
	public void testSchemaWithNamedTypes() throws SAXException {
		
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schemaNamed = factory.newSchema(XSDValidateExample.class.getResource("order-using-named-types.xsd"));
		Validator validatorNamed = schemaNamed.newValidator();
		
		StringWriter resNamedWriter = new StringWriter();
		Result resultsNamed = new StreamResult(resNamedWriter);
		try {
			validatorNamed.validate(new StreamSource(XSDValidateExample.class.getResourceAsStream("order.xml")), resultsNamed);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
}
