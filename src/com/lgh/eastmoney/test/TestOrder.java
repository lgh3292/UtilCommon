package com.lgh.eastmoney.test;

import com.lgh.util.logging.LogUtil;

public class TestOrder {
	public static People p = new People();
static {
	LogUtil.info("static body");
}

private static class People{
	public People(){
		LogUtil.info("people");
	}
}
public static void main(String[] args) {
	new TestOrder();
}

}
