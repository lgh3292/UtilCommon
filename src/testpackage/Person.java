package testpackage;

import java.util.HashSet;
import java.util.Iterator;

public class Person {
	private String name;

	public Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("Person.equals()");
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		System.out.println("Person.hashCode()");
		return super.hashCode();
	}

	public static void main(String[] args) {
		HashSet hs = new HashSet();
		Person a = new Person("liu");
		Person b = new Person("liu");
		Person c = new Person("liu");
		hs.add(a);
		hs.add(b);
		hs.add(c);
		System.out.println(a.hashCode()+ "  "+b.hashCode());
		hs.remove(a);
		Iterator i = hs.iterator();
		
		while(i.hasNext()){
			System.out.println(i.next());
		}
		
	}
}
