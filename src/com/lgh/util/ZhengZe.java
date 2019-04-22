package com.lgh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhengZe {
//	public static void main(String[] args) {
//		try {//
//			Pattern p = Pattern.compile("[\\\"]{1}[^\\[]{1,}[\\\"]{1}");
////			String content = "var quote_123={rank:[\"6009881,600988,*ST宝龙,10.20,10.18,10.35,10.47,10.11,440,4264,0.15,1.47%,10.33,3.53%,-88.07%,-1210,1,1654,2609,-1,-1,-0.19%,0.86,0.60%,1850.00,001153|002481|003511|003562,10.35,10.36,2011-07-28 14:36:40,0,70753700,0\"],pages:45}";
//			String content = "var C1Cache={quotation:[\"0000011,上证指数,2694.84,6734236,61646554,-28.66,-1.05%,2723.49,-1\",\"3990012,深证成指,11998.58,5884654,46967961,-118.28,-0.98%,12116.86,1\"],record:[\"182,15,698\",\"347,19,933\"]}";
//			Matcher m = p.matcher(content);
//			
//			
//			List<MatchResult> results = new ArrayList<MatchResult>();
//			 while(m.find()) results.add(m.toMatchResult());
//			 for(MatchResult r: results){
//				 LogUtil.info(r.group());
//			 }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	  
	/**
	 * get the result from the input
	 * @param input
	 * @return
	 */ 
	public static List<String> getResult(CharSequence input,String regex,String removeable){
		List<String> list = new ArrayList<String>(); 
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		List<MatchResult> results = new ArrayList<MatchResult>();
		while (m.find()){
			results.add(m.toMatchResult());
		}
		for(MatchResult m1:results){
			if(removeable!=null){
				list.add(m1.group().substring(removeable.length()));
			}else{
				list.add(m1.group());
			}
			
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		String input1 = new String("abc");
		String input2 = new String("a");
		Pattern p = Pattern.compile("a.");
		Matcher m1 = p.matcher(input1);
		Matcher m2 = p.matcher(input2);
		System.out.println(m1.find());
		System.out.println(m2.find());
		while (m1.find()){
			MatchResult result = m1.toMatchResult();
			System.out.println(result.group());
		}
		
		while (m2.find()){
			MatchResult result = m2.toMatchResult();
			System.out.println(result.group());
		}
	}
}

