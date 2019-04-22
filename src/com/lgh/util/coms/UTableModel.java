package com.lgh.util.coms;

import java.lang.reflect.Method;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * the parameters contains the column name's order and the Table list data
 * @author liuguohu
 *
 */
public class UTableModel extends AbstractTableModel {
	private String[] columnOrder;
	private List<? extends Object> list;
	public UTableModel(String[] columnOrder,List<? extends Object> list){
		this.columnOrder = columnOrder;
		this.list = list;
	}
	
	public int getColumnCount() {
		if(columnOrder!=null){
			return columnOrder.length;
		}
		return 0;
	}

	public int getRowCount() {
		return list==null?0:list.size();
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(list.size()>rowIndex&&list.get(rowIndex)!=null){
			Object object = list.get(rowIndex);
			if(columnOrder.length>columnIndex){
				String fieldName = columnOrder[columnIndex];
	            String firstChatacter = fieldName.substring(0, 1).toUpperCase();
	            String getMethodName = "get" + firstChatacter + fieldName.substring(1);
				Method getMethod = null;
				Object returnValue = null;
				try {
					getMethod = object.getClass().getMethod(getMethodName);
					returnValue = getMethod.invoke(object);
				} catch (Exception e) {
		            getMethodName = "is" + firstChatacter + fieldName.substring(1);//catch it if it fail?
		            try {
						getMethod = object.getClass().getMethod(getMethodName);
						returnValue = getMethod.invoke(object);
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}
//				Class<?> cls = getMethod.getReturnType();
				return returnValue;
			}
		}
		return null;
	}

}
