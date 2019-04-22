package testpackage;

public class TestThread {

  public static void main(String[] args){
	  
	  Thread t = new Thread(){
		  public void run(){
			  System.out.println("run");
		  }
	  };
	  
	  t.start();
	  System.out.println("out");
  }
}
