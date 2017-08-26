package testpackage;

public class Message {
	public static void test(){
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	String getText() { return "text"; }
	
	public static void main(String[] args) {
		Message m1 = new Message();
		Message m2 =new Message();
		
		String a = new String("sina.com");
		String b = new String("sina.com");
		System.out.println(a==b);
		System.out.println(a.equals(b));
		System.out.println(m1==m2);
		int c =0;
		System.out.println(m1.equals(m2));
	}
}