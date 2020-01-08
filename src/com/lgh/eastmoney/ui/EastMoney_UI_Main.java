package com.lgh.eastmoney.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.lgh.eastmoney.bfo.EastMoneyPersonBuy;
import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.eastmoney.bfo.RealTimeMarketTrend;
import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.ctl.StockRiseDrop;
import com.lgh.eastmoney.ctl.StockWorker;
import com.lgh.util.DateUtil;
import com.lgh.util.PicUtil;
import com.lgh.util.UIUtil;
import com.lgh.util.coms.UISystemTray;
import com.lgh.util.coms.UTableColumnModel;
import com.lgh.util.coms.UTableModel;
import com.lgh.util.logging.LogUtil;
import com.lgh.util.net.NetworkManager;

public class EastMoney_UI_Main extends JPanel implements ActionListener,Runnable{
	//north Jpanel
	private JTextField stockOrName = new JTextField();
	private JLabel allProfitOrLoss = new JLabel();
	
	//jtable 
	private JTable jtable;
	//south JPanel 用来显示大盘实时信息，点击JPanel跳到下一页
	private int pageNumber = 1;
	private int pageSize;
	//jtable's list data
	private List<EastMoneyRiseDrop> eastMoneyRiseDrops = new ArrayList<EastMoneyRiseDrop>();
	
	//urls message
	private List<EastMoneyURLMsg> dropStockMsgs;
	
	//combobox
	private String SORT_TYPE = "SortBy_Person_Select";
	private String URL_TYPE = "risedrop_url_hushen_a";
	String[] personSelectStocks = ((String)PropertiesConfig.getReadOnlyEastmoneyProperties().get("person_stocks")).split(",");
	private List<EastMoneyPersonBuy> personBuys;
	
	//auto get RealtimeMarketTrend 获得大盘的实时涨跌情况
	private List<RealTimeMarketTrend> marketTrends;
	//autoRun
	private boolean autoRun = true;


	private JFrame jf =new JFrame("IT");
	
	public EastMoney_UI_Main(){
		this.setLayout(new BorderLayout());
		this.add(createNorthPanel(),BorderLayout.NORTH);
		this.add(createCenterPanel(),BorderLayout.CENTER);
		this.add(createSouthPanel(),BorderLayout.SOUTH);
		createMySystemTray();
		createJFrame();
		new Thread(this).start();
	}
	
	/**
	 * a ui thread that will started once system start
	 * 1.will auto get online date once user click the button "auto"
	 */
	public void run(){
		long nextRunTime_Stock = System.currentTimeMillis();
		long autoRunTime_Stock = Long.valueOf((String) PropertiesConfig.getReadOnlyEastmoneyProperties().get("ui_auto_flash_time"));
		
		//RealTimeMarketTrend
		List<EastMoneyURLMsg> eastMoneyRiseDropURLMsgs = EastMoneyUtil.getRiseDropStockMsgs("realtime_market_trend");
		
		//Get StockVolume and RiseDrop on time,run worker every day 15:10PM
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,15);
		calendar.set(Calendar.MINUTE, 10);
		calendar.set(Calendar.SECOND, 00);
		long nextRunTime_getVolumeAndRiseDrop = calendar.getTimeInMillis();
		while(true){
			try {
				
				if(NetworkManager.getInstance().isNetEnabled()){
					//定时跑StockWorker自动从eastmoney上获得交易明细
					if(System.currentTimeMillis()>nextRunTime_getVolumeAndRiseDrop){
						int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
						if(dayOfWeek!=Calendar.SATURDAY&&dayOfWeek!=Calendar.SUNDAY){
							//如果是周六或者周日就不跑数据
							runStockWorker(calendar);		
						}else{
							LogUtil.info(".....Don't need to run StockWorker,it's dayOfWeek："+dayOfWeek);
						}
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						nextRunTime_getVolumeAndRiseDrop = calendar.getTimeInMillis();
					}
					
					
					boolean transTime = DateUtil.isTransactionTime(new Date());
					//1.在交易时间要隔时间跑次 2.非交易时间情况下数据为0要跑
					if(transTime||(!transTime&&eastMoneyRiseDrops.size()==0)){
						if(autoRun&&nextRunTime_Stock<System.currentTimeMillis()){
							try {
								//1.auto get Stocks riseDrop message 自动查询个股涨跌
								doQueryJB();
								//2.auto get RealTimeMarketTrend 自动获得实时大盘实时行情
								List<RealTimeMarketTrend> marketTrends = new ArrayList<RealTimeMarketTrend>();
								for(EastMoneyURLMsg msg:eastMoneyRiseDropURLMsgs){
									List<RealTimeMarketTrend> subMarketTrends = new StockRiseDrop().getRealtimeMarketTrend(msg.getURL());
									if(subMarketTrends!=null&&subMarketTrends.size()>0){
										marketTrends.addAll(subMarketTrends);
									}
								}
								if(marketTrends!=null&&marketTrends.size()>0){
									this.marketTrends = marketTrends;
								}
							} catch (Exception e) {
								e.printStackTrace();LogUtil.error("error", e);
							}finally{
								nextRunTime_Stock=System.currentTimeMillis()+autoRunTime_Stock;
							}
						}
					 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}finally{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
				
		}
	}
	
	private void runStockWorker(Calendar calendar){
		try {
			String flag = PropertiesConfig.getWritableEastmoneyProperties().getProperty(DateUtil.getCurrentDate("yyyyMMdd"));
			//flag is used to detect whether the current date has runned the work
			if(flag==null||(flag!=null&&flag.trim().equals(""))){
				if(UISystemTray.getInstance()!=null){
					UISystemTray.getInstance().getTrayIcon().displayMessage("volume and risedrop", "system start run StockWorker", MessageType.INFO);	
				}
				new StockWorker().start();
			}else{
				LogUtil.info(DateUtil.getCurrentDate("yyyyMMdd")+" StockWorker is runned!");
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
	}
	
	private void createJFrame(){
		jf.setSize(800, 600);
		jf.setIconImage(PicUtil.TrayIcon);
		jf.setLocationRelativeTo(null);
		jf.add(this);
		jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                jf.setVisible(false);
            }
        });
		jf.setVisible(true);
	}
	
	private JPanel createNorthPanel(){
		JPanel jp = new JPanel();
		//search field
		stockOrName.addKeyListener(new KeyAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				List<EastMoneyRiseDrop> temp = new ArrayList<EastMoneyRiseDrop>();
				List<EastMoneyRiseDrop> findTemp = new ArrayList<EastMoneyRiseDrop>();
				String searchContent = stockOrName.getText().trim();
				boolean isNumeric = false;
				String[] searchIndexs = searchContent.split(" ");
				if(searchContent!=null&&!searchContent.trim().equals("")){
					for(EastMoneyRiseDrop drop:eastMoneyRiseDrops){
						for(String searchIndex:searchIndexs){
						    isNumeric = isNumeric(searchIndex);
							if(isNumeric){
								if(drop.getEmStockId().indexOf(searchIndex)>=0){
									findTemp.add(0,drop);
								}
							}else{
								if(drop.getEmStockName().indexOf(searchIndex)>=0){
									findTemp.add(0,drop);
								}
							}
						}
					}
					eastMoneyRiseDrops.removeAll(findTemp);
					jtable.getRowSorter().modelStructureChanged();
					//只有单个searchIndex的时候才排序
					if(searchIndexs!=null&&searchIndexs.length==1){
						FieldComparator comparator = new FieldComparator(isNumeric);	
						Collections.sort(findTemp,comparator);
					}
					temp.addAll(findTemp);
					temp.addAll(eastMoneyRiseDrops);
					eastMoneyRiseDrops.removeAll(eastMoneyRiseDrops);
					eastMoneyRiseDrops.addAll(temp);
				}else{
					Collections.sort(eastMoneyRiseDrops, new PersonSelectComparator(SORT_TYPE));
				}
				SwingUtilities.updateComponentTreeUI(jtable);
			}
		});
		//queryJB
		JButton queryJB = new JButton("query");
		queryJB.addActionListener(this);
		queryJB.setActionCommand("queryJB");

		//JComboBox risedrop_urls
		JComboBox urlsTypeBox = new JComboBox();
		dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("ui_risedrop_url_all");
		for(EastMoneyURLMsg dropStockMsg:dropStockMsgs){
			urlsTypeBox.addItem(dropStockMsg.getType());
		}
		urlsTypeBox.setSelectedItem(URL_TYPE);
		urlsTypeBox.setMaximumSize(new Dimension(50,20));
		urlsTypeBox.addActionListener(this);
		urlsTypeBox.setActionCommand("UrlsType");
		
		//JComboBox sortType
		JComboBox sortTypeBox = new JComboBox();
		String[] sortTypes = ((String)PropertiesConfig.getReadOnlyEastmoneyProperties().get("ui_sort_type")).split(",");
		for(String sortType:sortTypes){
			sortTypeBox.addItem(sortType);
		}
		sortTypeBox.setMaximumSize(new Dimension(50,20));
		sortTypeBox.addActionListener(this);
		sortTypeBox.setActionCommand("SortType");
		//clearJB
		JButton clearDataJB = new JButton("clear");
		clearDataJB.addActionListener(this);
		clearDataJB.setActionCommand("clearJB");
		
		//autoJB
		JButton autoJB = new JButton("startAuto");
		if(autoRun){
			autoJB.setText("stopAuto");
		}else{
			autoJB.setText("startAuto");
		}
		autoJB.addActionListener(this);
		autoJB.setActionCommand("autoJB");
		
		UIUtil.addHVComponentsGroup(jp, false, true,stockOrName,sortTypeBox,urlsTypeBox,autoJB,queryJB,clearDataJB,allProfitOrLoss);
		return jp;
	}
	
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		}
		return true;
		}

    /**
     * init the systemTray
     */
    private UISystemTray createMySystemTray() {
        UISystemTray mst = null;
    	PopupMenu pop = new PopupMenu("菜单");
		String[] items = {"EastMoneyInOutFlow","EastMoneyRDVCount","NetDisable","导入(I)","退出(X)"};//可变
		int a = items.length-1;
		for (int i = 0; i<items.length;i++) {
			MenuItem item = new MenuItem(items[i]);
			item.setActionCommand("SystemTray" + a--);
			item.addActionListener(this);
			pop.add(item);
			//pop.addSeparator();
		}
        if (SystemTray.isSupported()) {
            mst =  UISystemTray.getInstance(null,pop,"IT");
            final TrayIcon trayIcon = mst.getTrayIcon();
           
            trayIcon.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton()==MouseEvent.BUTTON1){
						if (!jf.isVisible()) {
							if(e.isAltDown()){
								stockOrName.requestFocus();
								jf.setState(JFrame.NORMAL);
								jf.setVisible(true);
							}else{
								StringBuffer sb = new StringBuffer();
								for(RealTimeMarketTrend trend:marketTrends){
									sb.append(trend.getRiseDropScope().substring(0, trend.getRiseDropScope().length()-1)+"  ");
								}
								trayIcon.displayMessage("warn messge!", "you can't go into the program unit press the setting keyboard("+sb.toString()+"  "+allProfitOrLoss.getText()+")", TrayIcon.MessageType.WARNING);
							}
						} else {
							if (JFrame.NORMAL == jf.getState()) {
								jf.setVisible(false);
								jf.setState(JFrame.ICONIFIED);
							} else {
								jf.setVisible(true);
								jf.setState(JFrame.NORMAL);
							}
						}
					} 
				}
            });
        }
        return mst;
    }
    
	private JPanel createCenterPanel(){

		String[] headNames = new String[]{"id","name","RiseDrop","newestPrice","transMoney","cityEarnings","personBuyMsg"};
		int[] widths = new int[]{30,30,30,30,30,20,50};
		String[] columnOrder = new String[]{"emStockId","emStockName","riseDropScope","newestPrice","transMoney","cityEarnings","personBuyMsg"};
		UTableColumnModel columnModel = new UTableColumnModel(headNames, widths);
		
		UTableModel tableModel = new UTableModel(columnOrder, eastMoneyRiseDrops);
		jtable = new EMTable(tableModel,columnModel,eastMoneyRiseDrops);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		sorter.setComparator(2, new DataComparator(2));
		sorter.setComparator(3, new DataComparator(3));
		sorter.setComparator(4, new DataComparator(4));
		sorter.setComparator(5, new DataComparator(5));
		jtable.setRowSorter(sorter);
		
		JPanel jp = new JPanel(new BorderLayout());
		jp.add(new JScrollPane(jtable),BorderLayout.CENTER);
		return jp;
	}
	

	private JPanel createSouthPanel(){
		final StringBuffer sb = new StringBuffer();
		final JPanel southPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString(sb.toString(), 10, 15);
			}

		};
		southPanel.setPreferredSize(new Dimension(10, 20));
		southPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				pageNumber++;
				if(pageNumber>pageSize){
					pageNumber=1;
				}
				southPanel.repaint();
			}
			
		});
		Thread t = new Thread(){
			public void run(){
				long nextGetMessage = System.currentTimeMillis();
				int pageCount = 3;
				while(true){
					try {
						if(System.currentTimeMillis()>=nextGetMessage){
							if(marketTrends!=null){
								sb.delete(0, sb.length());
								pageSize = marketTrends.size()/pageCount+(marketTrends.size()%pageCount>0?1:0);
								List<?> temp = EastMoneyUtil.getObjectList(marketTrends, pageNumber, pageCount);
								for(Object o:temp){
									RealTimeMarketTrend marketTrend = (RealTimeMarketTrend)o;
									sb.append(marketTrend.getEmStockName()+" "+marketTrend.getCurrentPoint()+" "+marketTrend.getRiseDropScope()+"  "+new DecimalFormat("0.00").format(marketTrend.getTransMoney()/10000)+"(亿元)"+"   ");
								}
							}
							southPanel.repaint();
							nextGetMessage=System.currentTimeMillis()+200;
						}
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		
		return southPanel;
	}
	
	public static void main(String[] args) {
		new EastMoney_UI_Main();
	}

	public static void testSort(){
		JFrame frame = new JFrame("JTable的排序测试");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 表格中显示的数据
		Object rows[][] = { { "王明", "中国", 44 }, { "姚明", "中国", 25 },
		{ "赵子龙", "西蜀", 1234 }, { "曹操", "北魏", 2112 },
		{ "Bill Gates", "美国", 45 }, { "Mike", "英国", 33 } };
		String columns[] = { "姓名", "国籍", "年龄" };
		TableModel model = new DefaultTableModel(rows, columns);
		JTable table = new JTable(model);
		final RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		sorter.addRowSorterListener(new RowSorterListener() {
			
			public void sorterChanged(RowSorterEvent e) {
				System.out.println("aaa");
			}
		});
		table.setRowSorter(sorter);
		JScrollPane pane = new JScrollPane(table);
		frame.add(pane, BorderLayout.CENTER);
		frame.setSize(300, 150);
		frame.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("queryJB")){
			final JButton jb = (JButton) e.getSource();
			new Thread(new Runnable(){
				public void run(){
					jb.setEnabled(false);
					try {
						doQueryJB();
					} catch (Exception e) {
						e.printStackTrace();LogUtil.error("error", e);
					}
					jb.setEnabled(true);
				}
			}).start();
		}else if(actionCommand.equals("SortType")){
			JComboBox box = (JComboBox) e.getSource();
			SORT_TYPE = (String) box.getSelectedItem();
			jtable.getRowSorter().modelStructureChanged();
			Collections.sort(eastMoneyRiseDrops, new PersonSelectComparator(SORT_TYPE));
			
			SwingUtilities.updateComponentTreeUI(jtable);
		}else if(actionCommand.equals("clearJB")){
			eastMoneyRiseDrops.removeAll(eastMoneyRiseDrops);
			SwingUtilities.updateComponentTreeUI(jtable);
		}else if(actionCommand.equals("autoJB")){
			JButton autoJB = (JButton)e.getSource();
			autoRun = !autoRun;
			if(autoRun){
				autoJB.setText("stopAuto");
			}else{
				autoJB.setText("startAuto");
			}
		}else if(actionCommand.equals("UrlsType")){
			JComboBox box = (JComboBox) e.getSource();
			this.URL_TYPE = (String) box.getSelectedItem();
		}
		
		//sys trayIcon event
		else if(actionCommand.equals("SystemTray0")){
			System.exit(0);
		}else if(actionCommand.equals("SystemTray1")){
		}else if(actionCommand.equals("SystemTray2")){
			MenuItem item = (MenuItem)e.getSource();
			if(item.getLabel().equals("NetDisable")){
				item.setLabel("NetEnable");
				NetworkManager.getInstance().disableNet();
			}else{
				item.setLabel("NetDisable");
				NetworkManager.getInstance().enableNet();
			}
		}else if(actionCommand.equals("SystemTray3")){
			new EastMoneyRDVCountUI();
		}else if(actionCommand.equals("SystemTray4")){
			new StockMoneyInOutFlowUI();
		}
		System.out.println(actionCommand);
	}
	
	private void doQueryJB() throws Exception{
		long start = System.currentTimeMillis();
		try {
			//init the personBuys data 个人所卖股票
			if(personBuys==null){
				List<String> personBuyStr = EastMoneyUtil.getPersonBuy();
				personBuys = new ArrayList<EastMoneyPersonBuy>();
				for(String s:personBuyStr)
					personBuys.add(new EastMoneyPersonBuy(s));
			}
			
			Set<EastMoneyRiseDrop> datas =new HashSet<EastMoneyRiseDrop>();// Collections.synchronizedSet(new HashSet<EastMoneyRiseDrop>());
			StockRiseDrop riseDrop = new StockRiseDrop();
			if(URL_TYPE.equals("risedrop_url_all")){
				for(EastMoneyURLMsg msg:dropStockMsgs){
					datas.addAll(riseDrop.getAllRealTimeStockMessage(msg));
				}
			}else{
				for(EastMoneyURLMsg msg:dropStockMsgs){
					if(msg.getType().equals(URL_TYPE)){
						Set<EastMoneyRiseDrop> set =  riseDrop.getAllRealTimeStockMessage(msg);
						datas.addAll(set);
					}
				}
			}
			boolean updateComponent = false;
			if(datas.size()>0){//ouput personbuy stock message
				//1.删除不在查询到的集合里的数据
				List<EastMoneyRiseDrop> removeList = new ArrayList<EastMoneyRiseDrop>();
				for(EastMoneyRiseDrop eastMoneyRiseDrop:eastMoneyRiseDrops){
					boolean isPerselect = false;
					for(EastMoneyPersonBuy personBuy:personBuys){
						if(personBuy.getEmStockId().equals(eastMoneyRiseDrop.getEmStockId())){
							isPerselect = true;//不删除自选股，即使这次没有更新
							break;
						}
					}
					if(!datas.contains(eastMoneyRiseDrop)&&!isPerselect){
						removeList.add(eastMoneyRiseDrop);
					}
				}
				if(datas.size()!=eastMoneyRiseDrops.size()){
					updateComponent = true;
				}
				eastMoneyRiseDrops.removeAll(removeList);
				
				//2.添加个人买卖股票信息到 datas里,更新需要更新的数据，否则添加此条数据
				double curDayProfit = 0;
				double allProfit = 0;
				for(EastMoneyRiseDrop drop:datas){
					for(EastMoneyPersonBuy personBuy:personBuys){
						if(personBuy.getEmStockId().equals(drop.getEmStockId())){
							curDayProfit+=personBuy.getCurrentDayProfitOrLoss(drop.getRiseDropMoney());
							allProfit+=personBuy.getAllProfitOrLoss(drop.getNewestPrice());
							drop.setPersonBuyMsg(personBuy);
						}
					}
					
					boolean update = false;
					synchronized (eastMoneyRiseDrops) {
						for(EastMoneyRiseDrop eastMoneyRiseDrop:eastMoneyRiseDrops){
							if(eastMoneyRiseDrop.getEmStockId().equals(drop.getEmStockId())){
								eastMoneyRiseDrop.update(drop);
								update =true;
								break;
							}
						}
					}
					if(!update){
						eastMoneyRiseDrops.add(drop);
					}
				}
				if(curDayProfit>0){
					allProfitOrLoss.setForeground(EastMoneyUtil.RED_COLOR);
				}else{
					allProfitOrLoss.setForeground(EastMoneyUtil.GREEN_COLOR);
				}
				DecimalFormat format = new DecimalFormat("0.00");
				allProfitOrLoss.setText("cur:"+format.format(curDayProfit)+ " all:"+format.format(allProfit));
			}
			LogUtil.info("...getting..."+URL_TYPE+" waste "+(System.currentTimeMillis()-start)+"(second).."+eastMoneyRiseDrops.size());
			//SortBy_RiseDrop is default sort type
			System.out.println("updateComponent:"+updateComponent);
			if(updateComponent){
				Collections.sort(eastMoneyRiseDrops, new PersonSelectComparator(SORT_TYPE));
				SwingUtilities.updateComponentTreeUI(jtable);
			}else{
				jtable.repaint();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();LogUtil.error("error", e1);
		}
	}
 
	
	private class DataComparator implements Comparator{
		private int column;
		public DataComparator(int column){
			this.column = column;
		}
		
		public int compare(Object o1, Object o2) {
			if(column==3||column==4||column==5){
				if(o1 instanceof Double&&o2 instanceof Double){
					return Double.valueOf((Double)o1-(Double)o2)>0?1:-1;
				}
			}else if(column==2){
				if(o1 instanceof String&&o2 instanceof String){
					String s1 = (String)o1;
					String s2 = (String)o2;
					if(s1!=null&&s1.length()>1&&s2!=null&&s2.length()>1){
						Double d1 = Double.valueOf(s1.substring(0,s1.length()-1));
						Double d2 = Double.valueOf(s2.substring(0,s2.length()-1));
						return d1>d2?1:-1;
					}
				}
			}
			return -1;
		}
	}
	
 
	private class FieldComparator implements Comparator{
		private boolean isNumeric;
		public FieldComparator(boolean isNumeric){
			this.isNumeric = isNumeric;
		}
		public int compare(Object o1, Object o2) {
			if(o1 instanceof EastMoneyRiseDrop && o2 instanceof EastMoneyRiseDrop){
				EastMoneyRiseDrop drop1 = (EastMoneyRiseDrop)o1;
				EastMoneyRiseDrop drop2 = (EastMoneyRiseDrop)o2;
				if(isNumeric){
					String stock1 = drop1.getEmStockId();
					String stock2 = drop2.getEmStockId();
					return stock1.compareTo(stock2);
				}else{
					String stockName1 = drop1.getEmStockName();
					String stockName2 = drop2.getEmStockId();
					return stockName1.compareTo(stockName2);
				}
				
			}
			return 0;
		}
	}
	private class PersonSelectComparator implements Comparator{
		private String sortType;
		
		public PersonSelectComparator(String sortType){
			this.sortType = sortType;
		}
		public int compare(Object o1, Object o2) {
			if(o1 instanceof EastMoneyRiseDrop && o2 instanceof EastMoneyRiseDrop&&sortType!=null){
				EastMoneyRiseDrop drop1 = (EastMoneyRiseDrop)o1;
				EastMoneyRiseDrop drop2 = (EastMoneyRiseDrop)o2;
				if(sortType.trim().indexOf("SortBy_RiseDrop")>=0){
					Double riseDrop1 = Double.valueOf(drop1.getRiseDropScope().substring(0, drop1.getRiseDropScope().length()-1));
					Double riseDrop2 = Double.valueOf(drop2.getRiseDropScope().substring(0, drop2.getRiseDropScope().length()-1));
					if(sortType.trim().indexOf("Up")>0){
						return Double.valueOf(riseDrop2-riseDrop1)>0?1:-1;
					}else{
						return Double.valueOf(riseDrop1-riseDrop2)>0?1:-1;
					}
				}else if(sortType.trim().indexOf("SortBy_Money")>=0){
					Double money1 = Double.valueOf(drop1.getNewestPrice());
					Double money2 = Double.valueOf(drop2.getNewestPrice());
					if(sortType.trim().indexOf("Up")>0){
						return Double.valueOf(money2-money1)>0?1:-1;
					}else{
						return Double.valueOf(money1-money2)>0?1:-1;
					}
				}else if(sortType.trim().equals("SortBy_Person_Select")){
					String stock1 = drop1.getEmStockId();
					String stock2 = drop2.getEmStockId();
					int found1 = -1;
					int found2 = -1;
					for(int i=0;i<personSelectStocks.length;i++){
						if(stock1.trim().equals(personSelectStocks[i])){
							found1 = i;
						}
						if(stock2.trim().equals(personSelectStocks[i])){
							found2 = i;
						}
					}
					if(found1>-1){
						if(found2>-1){
							return found1-found2;
						}else{
							return -1;
						}
					}else{
						if(found2>-1){
							return 1;
						}
					}
					return Integer.valueOf(stock1).intValue()-Integer.valueOf(stock2);
				}
			}else if(o1 instanceof Double&&o2 instanceof Double){
				return (int) ((Double)o1>(Double)o2?1:-1);
			}
			return 0;
		}
	}
	
} 
