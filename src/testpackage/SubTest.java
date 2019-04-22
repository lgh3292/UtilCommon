package testpackage;

import java.util.PriorityQueue;

public class SubTest extends Test1{
	
	public SubTest(int a){
		super(a);
//		this.b = 3;
//		this.
	}
	
	public static void main(String[] args) {
		PriorityQueue<String> pq = new PriorityQueue<String>();
		pq.add("1");
		pq.add("2");
		pq.add("3");
		System.out.println(pq.poll() + pq.peek());
	}
}
