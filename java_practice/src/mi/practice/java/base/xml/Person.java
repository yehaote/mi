package mi.practice.java.base.xml;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

/**
 * 需要使用XOM.
 * 使用XML进行序列化
 */
public class Person {
	private String first, last;

	public Person(String first, String last) {
		this.first = first;
		this.last = last;
	}

	public Element getXml() {
		Element person = new Element("person");
		Element firstName = new Element("first");
		firstName.appendChild(first);
		Element lastName = new Element("last");
		lastName.appendChild(last);
		person.appendChild(firstName);
		person.appendChild(lastName);
		return person;
	}

	// 构造器负责解析XML ELEMENT
	public Person(Element person) {
		first = person.getFirstChildElement("first").getValue();
		last = person.getFirstChildElement("last").getValue();
	}

	@Override
	public String toString() {
		return first + " " + last;
	}

	// Make it human-readable
	public static void format(OutputStream os, Document doc) throws Exception {
		Serializer serializer = new Serializer(os, "utf-8");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();
	}

	public static void main(String[] args) throws Exception {
		List<Person> people = Arrays.asList(
				new Person("Dr. Bunsen", "Honeydew"), new Person("Gonzo",
						"The Great"), new Person("Phillip J.", "Fry"));
		System.out.println(people);
		Element root = new Element("people");
		for (Person p : people) {
			root.appendChild(p.getXml());
		}
		Document doc = new Document(root);
		format(System.out, doc);
	}
}
