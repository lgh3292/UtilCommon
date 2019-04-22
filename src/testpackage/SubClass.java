package testpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SubClass extends SuperClass {
	private SubClass(String a){
		super(5);
	}
	
    public static void main(String[] a){
    	SubClass s= new SubClass("a");
//    	s.test();
    	
    }
	private List test(){
		List bb = new ArrayList();
		bb.add("test");
		
		for(Object aa:bb)
		{
			
			bb.remove("test");
		}
		System.out.print(bb.size());
		
		
		return null;
	}
	
	
	 public static <A> String  copy2(List<A> list, List<A> list2 ) {
			return "";
	  }
	
	
	
}
