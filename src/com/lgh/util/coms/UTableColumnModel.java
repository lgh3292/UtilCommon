package com.lgh.util.coms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class UTableColumnModel extends DefaultTableColumnModel{
	private Color selectColor = Color.cyan;
	private Color unselectColor = Color.WHITE;
	
	/**
	 * @param selectColor the selectColor to set
	 */
	public void setSelectColor(Color selectColor) {
		this.selectColor = selectColor;
	}


	/**
	 * @param unselectColor the unselectColor to set
	 */
	public void setUnselectColor(Color unselectColor) {
		this.unselectColor = unselectColor;
	}
	
	private String[] headNames;
	private int[] widths;
	private HeadTableCellRenderer headCell;
	public UTableColumnModel(String[] headNames,int[] widths){
		this.headNames = headNames;
		this.widths = widths;
		initModel();
	}
	
	
	public UTableColumnModel(){
		initModel();
	}
	
	private void initModel(){
		headCell = new HeadTableCellRenderer();
		for (int i = 0; i < headNames.length; i++) {
			ColumnTableCellRenderer cell = new ColumnTableCellRenderer();
			TableColumn tc = new TableColumn(i);
			tc.setHeaderValue(headNames[i]);
			tc.setPreferredWidth(widths[i]);
//			tc.setWidth(50);
			tc.setResizable(true);
			tc.setCellRenderer(cell);
//			tc.setHeaderRenderer(headCell);
			this.addColumn(tc);
		}
	}
	
	
	/**
	 * the inner table cellRenderer
	 * @author liuguohu
	 *
	 */
	private class ColumnTableCellRenderer extends JLabel implements TableCellRenderer,MouseListener{
		
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			this.setOpaque(true);
			if(isSelected&&selectColor!=null){
				this.setBackground(selectColor);
			}else if(unselectColor!=null){
				this.setBackground(unselectColor);
			}
			this.setText(value==null?"":value.toString());
			return this;
		}

		public void mouseClicked(MouseEvent e) {

				System.out.println("mouseClicked");
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * the head cell Renderer 
	 * @author liuguohu
	 *
	 */
	private class HeadTableCellRenderer extends JLabel implements TableCellRenderer{

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			this.setBackground(new Color(207, 222, 239));
			this.setFont(new Font("ËÎÌו", Font.PLAIN, 12));
//			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0,
					new Color(80, 110, 170)));
//			this.setOpaque(true);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			if (value == null) {
				this.setText("");
			} else {
				this.setText(value.toString());
			}
			return this;
		}
		
	}
	public static void main(String[] args) {
	}
}
