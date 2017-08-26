package testpackage;

import java.util.Hashtable;
import java.util.Iterator;

public class TestString {
	private String name;
	enum e{ONE,T,TH};
	public TestString(String name){
		this.name = name;
		System.out.println(e.ONE.equals(e.ONE));
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		System.out.println("TestString.hashCode()");
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public static void main(String[] args) {
		TestString t1 = new TestString("a");
		TestString t2 = new TestString("a");
		System.out.println(t1.equals(t2));
		Hashtable hs = new Hashtable();

		hs.put(1,t1);
		Iterator i = hs.values().iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		
		
	}
	
}
