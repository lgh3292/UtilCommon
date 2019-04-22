package com.lgh.eastmoney.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.eastmoney.bo.EastMoneyInOutFlow;
import com.lgh.eastmoney.ctl.StockMoneyInOutFlow;
import com.lgh.util.coms.UTableColumnModel;
import com.lgh.util.coms.UTableModel;
import com.lgh.util.logging.LogUtil;

public class StockMoneyInOutFlowUI extends JPanel implements ActionListener,Runnable{
	//jtable 
	private JTable jtable;
	//jtable's list data
	private List<EastMoneyInOutFlow> inOutFlows = new ArrayList<EastMoneyInOutFlow>();
	private JFrame jf = new JFrame("StockMoneyInOutFlowUI");
	
	private StockMoneyInOutFlow flow;
	public static void main(String[] args) {
		new StockMoneyInOutFlowUI();
	}
	
	
	public void run(){
		
	}
	public StockMoneyInOutFlowUI(){
		super(new BorderLayout());
		initComs();
		showOnJFrame();
		new Thread(new Runnable(){
			public void run(){
				initData();
			}
		}).start();
	}
	private void showOnJFrame(){
        jf.setSize(800, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.add(this, BorderLayout.CENTER);
        jf.setVisible(true);
	}
	
	private void initData(){
		List<EastMoneyURLMsg> dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("in_out_flow_stock");
		if(flow==null){
			flow = new StockMoneyInOutFlow();
		}
		
		Set<?> set = flow.getAllMessageByMulThread(dropStockMsgs.get(0));
		inOutFlows.clear();
		inOutFlows.addAll((Collection<? extends EastMoneyInOutFlow>) set);
		
		SwingUtilities.updateComponentTreeUI(jtable);
		
	}
	
	private void initComs(){
		String[] headNames = new String[]{"transDate","emStockId","emStockName","marketType","industryCode","induNames","days","ZJNETIN","ZJNETINPCT","MTIME"};
		int[] widths = new int[]{30,30,30,15,15,30,15,40,40,50};
		String[] columnOrder = new String[]{"transDate","emStockId","emStockName","marketType","industryCode","induNames","days","ZJNETIN","ZJNETINPCT","MTIME"};
		UTableColumnModel columnModel = new UTableColumnModel(headNames, widths);
		
		UTableModel tableModel = new UTableModel(columnOrder, inOutFlows);
		jtable = new EMTable(tableModel,columnModel,inOutFlows);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		sorter.setComparator(3, new DataComparator());
		sorter.setComparator(6, new DataComparator());
		sorter.setComparator(7, new DataComparator());
		sorter.setComparator(8, new DataComparator());
		jtable.setRowSorter(sorter);
		this.add(new JScrollPane(jtable),BorderLayout.CENTER);
		this.add(createSouth(),BorderLayout.SOUTH);
	}
	private JPanel createSouth(){
		JPanel jp = new JPanel();
		JButton jb = new JButton("reload");
		jb.addActionListener(this);
		jb.setActionCommand("reload");
		jp.add(jb);
		return jp;
	}
	
	private class DataComparator implements Comparator{
		public DataComparator(){
		}
		
		public int compare(Object o1, Object o2) {
			if(o1 instanceof Integer&&o2 instanceof Integer){
				return Integer.valueOf((Integer)o1-(Integer)o2)>0?1:-1;
			}
			if(o1 instanceof Double&&o2 instanceof Double){
				return Double.valueOf((Double)o1-(Double)o2)>0?1:-1;
			}
			return -1;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand!=null){
			if(actionCommand.equals("reload")){
				initData();
			}
		}
	}
}
