package dp.test.xml.jaxb.entity;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Basically this class must be contained in every package that is supplied as parameter to 
 * {@link javax.xml.bind.JAXBContext#newInstance(String)}. The convention is to create a
 * factory method for every class to be recognised by JAXB where name of method is
 * "create" + [class name]. i.e. the Address class has createAddress() method that has no 
 * arguments and has return type compliance.
 * 
 * @author DPavlov
 */
@XmlRegistry
public class ObjectFactory
{

	public Address createAddress() {
		return new Address();
	}
	
	public Person createPerson() {
		return new Person();
	}
	
	public People createPeople() {
		return new People();
	}
	
}
