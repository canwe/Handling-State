package dp.test.xml.jaxb.entity;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "person")
// NONE forces JAXB to look at annotations on fields and properties, alternatively 
// we could use FIELD or PROPERTIES and omit the individual annotations.
@XmlAccessorType(XmlAccessType.NONE)
// propOrder - defines the sequence of fields in the XML, if specified it has to specify all fields
@XmlType(name = "person", propOrder = { "name", "addresses" })
public class Person
{
	
	@XmlAttribute
	private int id;
	@XmlElement
	private String name;
	// Wrapper allows to add extra layer of tags around the collection
	@XmlElementWrapper(name = "addresses")
	@XmlElement(name = "address")
	private Collection<Address> addresses;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Collection<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(Collection<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Person ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "name = " + this.name + TAB
	        + "addresses = " + this.addresses + TAB
	        + " )";
	
	    return retValue;
	}


	
	

}
