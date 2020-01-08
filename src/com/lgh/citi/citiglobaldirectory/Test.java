package com.lgh.citi.citiglobaldirectory;

import com.lgh.citi.citiglobaldirectory.bo.Employee;
import com.lgh.citi.citiglobaldirectory.bo.IDInformation;

public class Test {
	public void test(final Employee s){
		s.setIdInformation(new IDInformation("test"));
	}
	
	public static void main(String[] args) {
		String a = "hello2222"; 
        final String b = "hello";
        String d = "hello";
        String c = b + 2; 
        String e = d + 2;
        System.out.println(c+"  "+e);
        System.out.println((a == c));
        System.out.println((a == e));
	}
}
