package dp.test.xml.jaxb.entity;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * In order to achieve marshalling/unmarshalling collection of object
 * to/from a single xml file we need a wrapper object such as this one
 * that would allows to wrap our individual person objects into
 * a list of people.
 * 
 * @author DPavlov
 */
@XmlRootElement(name = "people")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "people")
public class People
{

	@XmlElement(name = "person")
	private Collection<Person> people;

	
	public Collection<Person> getPeople() {
		return people;
	}

	
	public void setPeople(Collection<Person> people) {
		this.people = people;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((people == null) ? 0 : people.hashCode());
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
		People other = (People) obj;
		if (people == null) {
			if (other.people != null)
				return false;
		} else if (!people.equals(other.people))
			return false;
		return true;
	}

	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "People ( "
	        + super.toString() + TAB
	        + "people = " + this.people + TAB
	        + " )";
	
	    return retValue;
	}
	
	
	
}
