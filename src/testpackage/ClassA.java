package testpackage;

public class ClassA {
	private Integer userRole;
	public  synchronized void testA1(){
		System.out.println("testA1");
		try {
			Thread.sleep(1000);
			System.out.println("testA1 finish");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public  synchronized void testA2(){
		System.out.println("testA2");
		try {
			Thread.sleep(1000);
			System.out.println("testA2 finish");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		UIUtil.showOnJFrame(new JButton("aa"));
		System.out.println((3600+3014+360+1130+180)/2+540);
		System.out.println((60000-10000+4082+700+2198+1950)/2+9000);
		System.out.println(new ClassA().userRole.intValue());
	}
	
}
