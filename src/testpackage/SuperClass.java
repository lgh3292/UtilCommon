package testpackage;

public class SuperClass {
	private static final SuperClass s = new SuperClass();
	private SuperClass(){
		System.out.println("SuperClass");
	}
	public SuperClass(int a){
		System.out.println("SuperClass"+a);
	}
}
