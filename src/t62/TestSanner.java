package t62;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestSanner {

	public static void main(String[] args) {
		Map envMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("=== MENU === ");
		sb.append(System.getProperty("line.separator"));
		sb.append("1. Set Number of horizontal lanes (H-street) [min 1, max 3]  ");
		sb.append(System.getProperty("line.separator"));
		sb.append("2. Set Number of vertical lanes  (V-street) [min 1, max 4]");
		sb.append(System.getProperty("line.separator"));
		sb.append("3. Set Probability of a car entering H-street [min 0, max 1]");
		sb.append(System.getProperty("line.separator"));
		sb.append("4. Set Probability of a car entering V-street [min 0, max 1] ");
		sb.append(System.getProperty("line.separator"));
		sb.append("5. Run one simulation cycle ");
		sb.append(System.getProperty("line.separator"));
		sb.append("6. Set and run number of simulation cycles [min 1, max 10] ");
		sb.append(System.getProperty("line.separator"));
		sb.append("Enter your choice>");
		sb.append(System.getProperty("line.separator"));
		System.out.println(sb.toString());
		Scanner sc = new Scanner(System.in);
		for (int i = 1; i < 7; i++) {
			int j = sc.nextInt();
			switch (i) {
			case 1:
				if (1 <= j && j <= 3) {
					envMap.put("horizontal", j);
				} else {System.out.println("input again");
					i = i - 1;
				}
				break;
			case 2:
				if (1 <= j && j <= 4) {
					envMap.put("vertical", j);
				} else {
					System.out.println("input again");
					i = i - 1;
				}
				break;
			case 3:
				if (0 <= j && j <= 1) {
					envMap.put("HProbability", j);
				} else {
					System.out.println("input again");
					i = i - 1;
				}
				break;
			case 4:
				if (0 <= j && j <= 1) {
					envMap.put("VProbability", j);
				} else {
					System.out.println("input again");
					i = i - 1;
				}
				break;
			case 5:
				if (1 <= j && j <= 4) {
					envMap.put("cycle", j);
				} else {
					System.out.println("input again");
					i = i - 1;
				}
				break;
			case 6:
				if (1 <= j && j <= 10) {
					envMap.put("cyclenum", j);
				} else {
					System.out.println("input again");
					i = i - 1;
				}
				break;
			default:
				break;

			}
		}

	}

}
