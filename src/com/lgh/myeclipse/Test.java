package com.lgh.myeclipse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
	private static final String L = "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself.";

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userId = null;
		int intLicenseNum = 1;
		String strLicenseNum = null;
		boolean bProFlag = true;
		String strProFlag = null;
		while ((userId == null) || ("".equals(userId.trim()))) {
			System.out.print("用户名: ");
			try {
				userId = br.readLine();
			} catch (IOException localIOException1) {
			}
		}
		System.out.print("注册码可用的用户数量(默认 1, 最大 999): ");
		try {
			strLicenseNum = br.readLine();
		} catch (IOException localIOException2) {
		}
		Object nf = new DecimalFormat("000");
		if ((strLicenseNum == null) || ("".equals(strLicenseNum.trim())))
			strLicenseNum = ((NumberFormat) nf).format(intLicenseNum);
		else {
			strLicenseNum = ((NumberFormat) nf).format(Integer
					.parseInt(strLicenseNum));
		}
		System.out.print("个人版 或 标准版(默认 个人版, n 标准版): ");
		try {
			strProFlag = br.readLine();
		} catch (IOException localIOException3) {
		}
		if ((strProFlag != null) && (!("".equals(strProFlag.trim())))) {
			bProFlag = false;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(1, 2);
		cal.add(6, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String need = userId.substring(0, 1) + "Y"
				+ ((bProFlag) ? "E3MP" : "E2MY") + "-100" + strLicenseNum + "-"
				+ sdf.format(cal.getTime()) + "0";
		String dx = need
				+ "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself."
				+ userId;
		int suf = decode(dx);
		String code = need + suf;
		System.out.println("注册码: " + change(code));
	}

	static int decode(String s) {
		int i = 0;
		char[] ac = s.toCharArray();
		int j = 0;
		for (int k = ac.length; j < k; ++j)
			i = 31 * i + ac[j];
		return Math.abs(i);
	}

	static String change(String s) {
		if ((s == null) || (s.length() == 0))
			return s;
		byte[] abyte0 = s.getBytes();
		char[] ac = new char[s.length()];
		int i = 0;
		for (int k = abyte0.length; i < k; ++i) {
			int j = abyte0[i];
			if ((j >= 48) && (j <= 57))
				j = (j - 48 + 5) % 10 + 48;
			else if ((j >= 65) && (j <= 90))
				j = (j - 65 + 13) % 26 + 65;
			else if ((j >= 97) && (j <= 122))
				j = (j - 97 + 13) % 26 + 97;
			ac[i] = (char) j;
		}
		return String.valueOf(ac);
	}
}