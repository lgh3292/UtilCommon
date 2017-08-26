package com.lgh.eastmoney.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JTable;

import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.util.coms.UTableColumnModel;
import com.lgh.util.coms.UTableModel;
import com.lgh.util.logging.LogUtil;

public class EMTable extends JTable implements MouseListener{
	//sub history data detail ui
	private EastMoneyHistoryUI eastMoneyHistoryUI;
	private List<? extends EastMoneyStock> datas;
	public EMTable(UTableModel tableModel,UTableColumnModel columnModel,List<? extends EastMoneyStock> datas){
		this(tableModel,columnModel);
		this.datas = datas;
	}
	
	
	public EMTable(UTableModel tableModel,UTableColumnModel columnModel){
		super(tableModel,columnModel);
		init();
	}
	
	private void init(){
		this.addMouseListener(this);
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			if(eastMoneyHistoryUI!=null){
				eastMoneyHistoryUI = null;
			}
			try {
				eastMoneyHistoryUI = new EastMoneyHistoryUI(this.datas,getRowSorter().convertRowIndexToModel(this.getSelectedRow()));
				
			} catch (Exception e1) {
				e1.printStackTrace();LogUtil.error("error", e1);
			}
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
	public static void main(String[] args) throws InterruptedException {
		int a= 0;
		while(true){
			Thread.sleep(10);
			if(a==100){
				return;
			}
			System.out.println(a);
			a++;
		}
	}
}
