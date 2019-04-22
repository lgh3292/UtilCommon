package com.lgh.util.coms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.lgh.util.UIUtil;

public class UUnitTest {
	private static Component testJTable(){
		List<Object> data = new ArrayList<Object>();
		data.add(new People("张三",22));
		data.add(new People("李",23));
		
		
		
		String[] headNames = new String[]{"姓名","年龄"};
		String[] columnOrder = new String[]{"name","age"};
		int[] widths = new int[]{50,50};
		JTable jtable = new JTable(new UTableModel(columnOrder, data),new UTableColumnModel(headNames, widths));
		jtable.setRowHeight(18);
		jtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);//设置最大化时,表也自动跟着最大化
		jtable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jtable.getTableHeader().setReorderingAllowed(false);
		jtable.setFillsViewportHeight(true);
//        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        jtable.setFont(new Font("宋体",Font.PLAIN,15));
//        jtable.getTableHeader().setReorderingAllowed(false);
		return jtable;
	}
	public static void main(String[] args) {
		UIUtil.showOnJFrame(testJTable());
		
	}
	
	private static class People{
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		private int age;
		public People(String name,int age){
			this.name = name;
			this.age = age;
		}
	}
}
