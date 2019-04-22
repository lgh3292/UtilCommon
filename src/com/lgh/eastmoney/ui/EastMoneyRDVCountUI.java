package com.lgh.eastmoney.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.lgh.eastmoney.bfo.EastMoneyRDVCount;
import com.lgh.eastmoney.ctl.EastMoneyService;
import com.lgh.util.DateUtil;
import com.lgh.util.coms.UTableColumnModel;
import com.lgh.util.coms.UTableModel;

/**
 * check data rise drop and volume count ，查看股票的每天成功导入数据库的条数
 * @author liuguohu
 *
 */
public class EastMoneyRDVCountUI extends JPanel implements ActionListener{
	//jtable 
	private JTable jtable;
	//jtable's list data
	private List<EastMoneyRDVCount> eastMoneyRDVCounts = new ArrayList<EastMoneyRDVCount>();
	private Calendar startDate,endDate;
	private JFrame jf = new JFrame("count");
	public EastMoneyRDVCountUI(){
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
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -30);
		startDate.add(Calendar.MINUTE, -30);
		System.out.println(DateUtil.getDateStr(startDate.getTime(), "yyyyMMdd")+"  "+DateUtil.getDateStr(endDate.getTime(), "yyyyMMdd"));
		findData(startDate,endDate);
	}
	
	private void findData(Calendar startDate,Calendar endDate){
		List<EastMoneyRDVCount> temp = new EastMoneyService().findEastMoneyRDVCount(startDate, endDate);
		eastMoneyRDVCounts.removeAll(eastMoneyRDVCounts);
		eastMoneyRDVCounts.addAll(temp);
		if(jtable!=null){
			SwingUtilities.updateComponentTreeUI(jtable);
		}
	}
	private void initComs(){
		String[] headNames = new String[]{"date","risedropCount","risedropHisCount","volumeCount"};
		int[] widths = new int[]{30,30,30,30};
		String[] columnOrder = new String[]{"date","risedropCount","risedropHisCount","volumeCount"};
		UTableColumnModel columnModel = new UTableColumnModel(headNames, widths);
		
		UTableModel tableModel = new UTableModel(columnOrder, eastMoneyRDVCounts);
		jtable = new JTable(tableModel,columnModel);
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
	
	public static void main(String[] args) {
		new EastMoneyRDVCountUI();
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
