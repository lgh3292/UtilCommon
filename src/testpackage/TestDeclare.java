package testpackage;

public class TestDeclare implements DeclareStuff {
	public static void main(String[] args) {
		int x = 5;
		new TestDeclare().doStuff(++x);
		int a =args.length;
	}

	public int doStuff(int s) {
		s += EASY + ++s;
		System.out.println("s" + s);
		return 0;
	}
}