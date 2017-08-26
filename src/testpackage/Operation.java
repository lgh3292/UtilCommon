package testpackage;

public enum Operation {

	PLUS {
		@Override
		double eval(double x, double y) {
			return x + y;
		}
	},

	MINUS {
		@Override
		double eval(double x, double y) {
			return x - y;
		}
	},

	TIMES {
		@Override
		double eval(double x, double y) {
			return x * y;
		}
	},

	DIVIDE {
		@Override
		double eval(double x, double y) {
			return x / y;
		}
	};

	// Do arithmetic op represented by this constant

	abstract double eval(double x, double y);

	public static void main(String args[]) {

		double x = Double.parseDouble("24");

		double y = Double.parseDouble("25");

		for (Operation op : Operation.values())

			System.out.printf("%f %s %f = %f%n", x, op, y, op.eval(x, y));

	}

}