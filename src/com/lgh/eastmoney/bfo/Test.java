package com.lgh.eastmoney.bfo;

import java.util.ArrayList;
import java.util.List;

public class Test extends EastMoneyURLMsg{

	private List<? extends EastMoneyPersonBuy> list;
	 


	/**
	 * @return the list
	 */
	public List<? extends EastMoneyPersonBuy> getList() {
		return list;
	}


	/**
	 * @param list the list to set
	 */
	public void setList(List<? extends EastMoneyPersonBuy> list) {
		this.list = list;
	}


	public Test(String type, String host, String listener, String parameters) {
		super(type, host, listener, parameters);
		// TODO Auto-generated constructor stub
	}


	public static void main(String[] args) {
		Test test = new Test(null, null, null, null);
		List list = new ArrayList();
		list.add(1);
		test.setList(list);
		
		int sum=0;
		for(int i=0;i<=100;i++){
			sum+=i;
		}
		System.out.println(10/3);
		System.out.println(103);
	}
}
