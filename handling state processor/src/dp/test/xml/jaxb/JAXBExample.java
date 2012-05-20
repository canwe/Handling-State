package dp.test.xml.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import dp.test.xml.jaxb.entity.Address;
import dp.test.xml.jaxb.entity.People;
import dp.test.xml.jaxb.entity.Person;

/**
 * The basics of JAXB is to provide binding between java objects and the XML
 * via creating intermediate binding java classes implementations that allow to
 * extract the data.
 * 
 * @author DPavlov
 */
public class JAXBExample
{

//	@Test
//	public void testGenerateSchemaExample() throws JAXBException, IOException {
//
//		final JAXBContext context = JAXBContext.newInstance(Person.class);
//
//		final StringWriter writer = new StringWriter();
//		// This call generates all tree of JAXB mappings into schema for Person class
//		context.generateSchema(new WriterSchemaOutputResolver(writer));
//
//		System.out.println(writer.toString());
//
//	}
//
//	@Test
//	public void testMarshallUnmarshalExample() throws JAXBException {
//
//		// You can specify the packages separated by colon
//		// e.g. "dp.test.xml.jaxb:dp.test.xml.jaxb.entity"
//		// BUT each of those packages must have the ObjectFactory class
//		final JAXBContext context = JAXBContext.newInstance("dp.test.xml.jaxb.entity");
//
//		final Address addr = new Address();
//		addr.setId(1);
//		addr.setHouse("221b");
//		addr.setStreet("Baker Str");
//		addr.setCity("London");
//		addr.setPostcode("NW1 6XE");
//		addr.setCountry("UK");
//
//		final Person sherlock = new Person();
//		sherlock.setId(1);
//		sherlock.setName("Sherlock Holmes");
//		sherlock.setAddresses(Arrays.asList(addr));
//
//		final StringWriter writer = new StringWriter();
//		// this is where we convert the object to XML
//		context.createMarshaller().marshal(sherlock, writer);
//
//		System.out.println(writer.toString());
//
//		// this is where we convert the XML to object
//		final Person fromXML = (Person) context.createUnmarshaller().unmarshal(
//				new StringReader(writer.toString()));
//
//		System.out.println(fromXML.toString());
//	}
	
	@Test
	public void testMarshallUnmarshalCollectionsExample() throws JAXBException {
		
		// You can specify the packages separated by colon 
		// e.g. "dp.test.xml.jaxb:dp.test.xml.jaxb.entity"
		// BUT each of those packages must have the ObjectFactory class
		final JAXBContext context = JAXBContext.newInstance("dp.test.xml.jaxb.entity");
		
		final Address addr = new Address();
		addr.setId(1);
		addr.setHouse("221b");
		addr.setStreet("Baker Str");
		addr.setCity("London");
		addr.setPostcode("NW1 6XE");
		addr.setCountry("UK");
		
		final Person sherlock = new Person();
		sherlock.setId(1);
		sherlock.setName("Sherlock Holmes");
		sherlock.setAddresses(Arrays.asList(addr));

		final Person wattson = new Person();
		wattson.setId(2);
		wattson.setName("Dr Wattson");
		wattson.setAddresses(Arrays.asList(addr));
		
		final People people = new People();
		people.setPeople(Arrays.asList(sherlock, wattson));
		
		final StringWriter writer = new StringWriter();
		// this is where we convert the object to XML
		context.createMarshaller().marshal(people, writer);
		
		System.out.println(writer.toString());
		
		// this is where we convert the XML to object
		final People fromXML = (People) context.createUnmarshaller().unmarshal(
				new StringReader(writer.toString()));
		
		System.out.println(fromXML.toString());
		
	}
	
}
