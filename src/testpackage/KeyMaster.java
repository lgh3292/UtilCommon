package testpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class KeyMaster {
	public int i;

	public KeyMaster(int i) {
		this.i = i;
	}

	@Override
	public boolean equals(Object o) {
		return i == ((KeyMaster) o).i;
	}

	@Override
	public int hashCode() {
		return i;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("I:" + i + "   hasCode:" + hashCode())
				.toString();
	}

	public static Object get(List list) {
		return list.get(0);
	}

	public static void add(List list) {
		list.add("0042");
	}

	public static int sum(List list) {
		int sum = 0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			int i = ((Integer) iter.next()).intValue();
			sum += i;
		}
		return sum;
	}
	class A{
		
	}
	class B extends A{
		
	}
	public static void test(List<? super String> list){
		list.add("ff");
	}
	public static void main(String[] args) {
		
		List<A> l1 = new ArrayList<A>();
//		test(l1);
		
		List<Integer> a = new ArrayList<Integer>();

		// a.add("11");
		// add(a);
		a.add(new Integer(11));
		a.add(new Integer(22));
		System.out.println(sum(a));
		Integer i = new Integer("0111");
		// Object o1 = get(new LinkedList());
		Object o2 = get(new LinkedList<KeyMaster>());
		// String s3 = getnew LinkedList<String>());
		Object o4 = get(new LinkedList<Object>());
		String s5 = (String) get(new LinkedList<String>());
		List<KeyMaster> set = (new ArrayList<KeyMaster>());
		KeyMaster k1 = new KeyMaster(1);
		KeyMaster k2 = new KeyMaster(2);
		set.add(k1);
		set.add(k1);
		set.add(k2);
		set.add(k2);
		// Printer.printCollection(set);
		System.out.print(set.size());
		// k2.i = 1;
		// Printer.printCollection(set);
		System.out.print(set.size());
		set.remove(k2);
		System.out.print(set.size());
		set.remove(k2);
		System.out.print(set.size());

		Iterator iterator = set.iterator();
		// while (iterator.hasNext()) {
		// KeyMaster m = (KeyMaster)iterator.next();
		// if(m.equals(k1)){
		// System.out.println("ok1");
		// }
		// if(m.equals(k2)){
		// System.out.println("ok2");
		// }
		// }
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		String[] temp = new String[5];
		String[] array2 = list.toArray(temp);
		Iterator<String> i2 = list.iterator();
		set.remove(k2);
		System.out.print(set.size());
		set.remove(k2);
		System.out.print(set.size());
	}
}