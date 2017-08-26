package testpackage;

public class ClassABTest {
	private ClassA a1 = new ClassA();
	private ClassA a2 = new ClassA();
	public void threadA(){
		new Thread(new Runnable(){
			public void run(){
				a1.testA1();
			}
		}).start();
	}
	public void threadB(){
		new Thread(new Runnable(){
			public void run(){
				a2.testA2();
			}
		}).start();
	}
	public static void main(String[] args) {
		ClassABTest test = new ClassABTest();
		test.threadA();
		test.threadB();
	}
}
