package com.lgh.eastmoney.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.bo.EastMoneyRiseDropHistory;
import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.ctl.EastMoneyService;
import com.lgh.util.DateUtil;
import com.lgh.util.UIUtil;
import com.lgh.util.coms.DateChooser;
import com.lgh.util.logging.LogUtil;

public class EastMoneyHistoryUI extends JPanel implements ActionListener,PropertyChangeListener{
	private Color HIDDEN_COLOR = new Color(255,255,254);
	private Color WHITE_COLOR = Color.WHITE;
	private Color BG_LINE_COLOR = new Color(217,217,217);
	private Color GREEN_COLOR = new Color(0,139,0);
	private Color RED_COLOR = new Color(255,0,0);

	
	private List<EastMoneyRiseDropHistory> list;
	private JPopupMenu pop;
	
	//stock message
	private Double higestMoney;
	private Double lowestMoney;
	private Double xLen;//every days len which compute by screenWidht/list.size
	private int currentMouseI;//the current stock index in collection which the mouse point to. 
	private Dimension dimension = new Dimension(800,600);

	private JFrame jf = new JFrame("sub");
	
	
	//all data
	private List<? extends EastMoneyStock> eastMoneyStocks;
	private int selectRow;
	//current select row
	private EastMoneyStock eastMoneyStock;
	private Date transStartDate;
	private Date transEndDate;
	private DateChooser startJL = new DateChooser();
	private DateChooser endJL = new DateChooser();
	
	
	private Robot robot;
	/**set default select row 0**/
	public EastMoneyHistoryUI(List<EastMoneyStock> eastMoneyRiseDrops){
		this(eastMoneyRiseDrops, 0);
	}
	
	
	
	public EastMoneyHistoryUI(List<? extends EastMoneyStock> eastMoneyRiseDrops,int selectRow){
		this.eastMoneyStocks = eastMoneyRiseDrops;
		this.selectRow = selectRow;
		if(eastMoneyRiseDrops.get(selectRow)!=null)
			eastMoneyStock = eastMoneyRiseDrops.get(selectRow);
		initData();
		initComs();
	}

	
	public EastMoneyHistoryUI(EastMoneyRiseDrop eastMoneyRiseDrop) throws Exception{
		this.eastMoneyStock = eastMoneyRiseDrop;
		initData();
		initComs();
	}
	
	

	
	private void initData(){
		if(DateUtil.isWoringTime(new Date())){//set the color to hide 
			GREEN_COLOR = new Color(0,0,0);
			RED_COLOR = new Color(0,0,0);
			HIDDEN_COLOR = new Color(255,255,254);
			WHITE_COLOR = Color.WHITE;
			BG_LINE_COLOR = new Color(217,217,217); 
			dimension = new Dimension(200,150);
		}
		
		if(eastMoneyStocks==null){
			eastMoneyStocks = new ArrayList<EastMoneyStock>();
		}
		//set the default area as 3 months
		if(transStartDate==null){
			setTimeArea(3);
		}
		EastMoneyRiseDropHistory history = new EastMoneyRiseDropHistory();
		history.setEmStockId(eastMoneyStock.getEmStockId());
		history.setTransStartDate(transStartDate);
		history.setTransEndDate(transEndDate);
		list= (List<EastMoneyRiseDropHistory>) new EastMoneyService().findData(history,EastMoneyRiseDropHistory.class);
		
		//add risedrop data
		Date import_date = EastMoneyUtil.getTheNewestImportDate();
		EastMoneyRiseDrop emrisedrop = new EastMoneyRiseDrop();
		emrisedrop.setEmStockId(eastMoneyStock.getEmStockId());
		emrisedrop.setTransStartDate(DateUtil.getPreDays(import_date, 1));
		emrisedrop.setTransEndDate(transEndDate);
		List<EastMoneyRiseDrop> drops= (List<EastMoneyRiseDrop>) new EastMoneyService().findData(emrisedrop,EastMoneyRiseDrop.class);
		for(EastMoneyRiseDrop drop:drops){
			EastMoneyRiseDropHistory his = drop.getEastMoneyRiseDropHistory();
			list.add(his);
		}
		
		
		for(int i=0;i<list.size();i++){
			
			EastMoneyRiseDropHistory curHis = list.get(i);
			//the date must be healthy
			if(curHis.isHealthy()){
				//if it's the first data,we set the riseDropScope and riseDropMoney as default value.
				if(i==0){
					higestMoney = curHis.getHigestPrice();
					lowestMoney = curHis.getLowestPrice();
					curHis.setRiseDropScope(new Double(0));
					curHis.setRiseDropMoney(new Double(0));
					curHis.setYesShoupan(0.0);
				}else{
					EastMoneyRiseDropHistory preHis = list.get(i-1);
					if(curHis.getHigestPrice()>higestMoney){
						higestMoney = curHis.getHigestPrice();
					}else if(curHis.getLowestPrice()<lowestMoney){
						lowestMoney = curHis.getLowestPrice();
					}
					curHis.setYesShoupan(preHis.getShoupan());
					curHis.setRiseDropMoney(curHis.getShoupan()-curHis.getKaipan());
					curHis.setRiseDropScope((curHis.getShoupan()-preHis.getShoupan())/preHis.getShoupan());
				}
			}
		}
		

	}
	
	
 
	private void initComs(){
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();LogUtil.error("error", e1);
		}
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				EastMoneyHistoryUI.this.requestFocus();
			}
			
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int index = (int)(e.getX()/xLen);
				Point p = e.getLocationOnScreen();
				Color c = robot.getPixelColor((int)p.getX(),(int)p.getY());
				if(c.equals(RED_COLOR)||c.equals(GREEN_COLOR)||c.equals(HIDDEN_COLOR)){
					if(currentMouseI!=index){
						if(pop!=null){
							pop.setVisible(false);
						}
						if(list.size()>index){
							pop  = createJPopupMenu(list.get(index));
							pop.show(EastMoneyHistoryUI.this, x+15, y);
							currentMouseI = index;
							pop.setVisible(true);
						}
					}else{
						if(pop!=null){
							pop.setVisible(true);
						}
					}
				}else{
					if(pop!=null){
						pop.setVisible(false);
					}
						 
				}
				
				
			}
			public void mouseDragged(MouseEvent e) {
			}
		});
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					selectRow++;
					gotoShowStock(selectRow);
				}else if (e.getKeyCode() == KeyEvent.VK_UP) {
					selectRow--;
					gotoShowStock(selectRow);
				}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					selectRow-=10;
					gotoShowStock(selectRow);
				}else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					selectRow+=10;
					gotoShowStock(selectRow);
				}
			}
		});
		//three month,sizMonth,oneYear,twoYear,fiveYear,alldata
		Dimension DIM = new Dimension(60,30);
		StockButton threeMonthsJB = new StockButton("3m","threeMonthsJB",DIM);
		StockButton fiveMonthsJB = new StockButton("6m","sixMonthsJB",DIM);
		StockButton oneYearJB = new StockButton("1y","oneYearJB",DIM);
		StockButton twoYearsJB = new StockButton("2y","twoYearJB",DIM);
		StockButton threeYearsJB = new StockButton("3y","threeYearJB",DIM);
		StockButton fourYearsJB = new StockButton("4y","fourYearJB",DIM);
		StockButton fiveYearsJB = new StockButton("5y","fiveYearJB",DIM);
		StockButton allDataJB = new StockButton("all","alldataJB",DIM);
		
		startJL.addPropertiesListener(this, "startDate");
		endJL.addPropertiesListener(this, "endDate");
		UIUtil.addHVComponentsGroup(this, false, true,new JLabel("Begin:"),startJL.getJLabel(),new JLabel("   End:"),endJL.getJLabel(),
				threeMonthsJB,fiveMonthsJB,oneYearJB,twoYearsJB,threeYearsJB,fourYearsJB,fiveYearsJB,allDataJB);
		showOnJFrame();
	}
 
	public void gotoShowStock(int selectRow){
		if(eastMoneyStocks.get(selectRow)!=null){
			eastMoneyStock = eastMoneyStocks.get(selectRow);
			initData();
			repaint();
		}
	}
	
	public void setTimeArea(int months){
		transStartDate = DateUtil.getPreMonths(new Date(), -months);
		transEndDate  = new Date();
		startJL.setDate(transStartDate);
		endJL.setDate(transEndDate);
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("startDateJB")){
			//setTimeArea(3);
		}else if(actionCommand.equals("endDateJB")){
			//setTimeArea(3);
		}else if(actionCommand.equals("threeMonthsJB")){
			setTimeArea(3);
		}else if(actionCommand.equals("sixMonthsJB")){
			setTimeArea(6);
		}else if(actionCommand.equals("oneYearJB")){
			setTimeArea(12);
		}else if(actionCommand.equals("twoYearJB")){
			setTimeArea(24);
		}else if(actionCommand.equals("threeYearJB")){
			setTimeArea(36);
		}else if(actionCommand.equals("fourYearJB")){
			setTimeArea(48);
		}else if(actionCommand.equals("fiveYearJB")){
			setTimeArea(60);
		}else if(actionCommand.equals("alldataJB")){
			setTimeArea(1000);
		}
		flush();
	}
	private void flush(){
		EastMoneyHistoryUI.this.requestFocus();
		initData();
		repaint();
	}
	
	/**propertyChangeListener**/
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if(propertyName.equals("startDate")){
			Calendar c = (Calendar) evt.getNewValue();
			transStartDate = c.getTime();
			flush();
		}else if(propertyName.equals("endDate")){
			Calendar c = (Calendar) evt.getNewValue();
			transEndDate = c.getTime();
			flush();
		}
	}
	
	private void showOnJFrame(){
        jf.setSize(dimension);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.add(this, BorderLayout.CENTER);
        jf.setVisible(true);
	}
	
	private class StockButton extends JButton{
		public StockButton(String text,String actionCommand,Dimension dim){
			super(text);
//			this.setOpaque(false);
//			this.setContentAreaFilled(false);
	        this.setPreferredSize(dim);
//	        this.setSize(new Dimension(text.length() * 15, 28));
	        this.setMinimumSize(dim);
	        this.setMaximumSize(dim);
	        this.setContentAreaFilled(false);
	        this.setBorderPainted(true);
			this.setFocusPainted(false);
//	        this.setIcon(new ImageIcon(Util.getImage("playlist/up1.png")));
//	        this.setPressedIcon(new ImageIcon(Util.getImage("playlist/up2.png")));
			this.addActionListener(EastMoneyHistoryUI.this);
			this.setActionCommand(actionCommand);
		}
	}
	private class StockJMenuItem extends JMenuItem{
		public StockJMenuItem(String title,String content,Double colorFlag){
			this.setLayout(new BorderLayout());
			this.setBackground(WHITE_COLOR);
			JLabel jl1 = new JLabel(title);
			JLabel jl2 = new JLabel(content);
			if(colorFlag>0){
				jl2.setForeground(RED_COLOR);
			}else if(colorFlag<0){
				jl2.setForeground(GREEN_COLOR);
			}
			this.setEnabled(false);
			this.add(jl1,BorderLayout.WEST);
			this.add(jl2,BorderLayout.EAST);
			this.setPreferredSize(new Dimension(150,20));
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private JPopupMenu createJPopupMenu(EastMoneyRiseDropHistory history){
		pop = new JPopupMenu();
		//UIUtil.addHVComponentsGroup(this, false, true, transDateJL);
		pop.add(new StockJMenuItem("",history.getTransDate()+"",0.0));
		pop.add(new StockJMenuItem("开  盘",history.getKaipan()+"",history.getKaipan()-history.getYesShoupan()));
		pop.add(new StockJMenuItem("最  高",history.getHigestPrice()+"",history.getHigestPrice()-history.getYesShoupan()));
		pop.add(new StockJMenuItem("最  低",history.getLowestPrice()+"",history.getLowestPrice()-history.getYesShoupan()));
		pop.add(new StockJMenuItem("收  盘",history.getShoupan()+"",history.getShoupan()-history.getYesShoupan()));
		pop.add(new StockJMenuItem("成交量",history.getTransVolumeStr()+"",0.0));
		pop.add(new StockJMenuItem("成交额",history.getTransMoneyStr()+"",0.0));
		pop.add(new StockJMenuItem("涨跌幅",history.getRiseDropScopeStr()+"",history.getRiseDropScope()));
		//pop.show(EastMoneyHistoryUI.this, e.getX()+15, e.getY()+15);
		pop.setSize(200, 200);
		return pop;
	}
	
	/**
	 * get the prefer line with while draw the Bg line
	 * @return
	 */
	private double getPreferLineWidth(){
		for(int i=0;i<10;i++){
			if(xLen*i*2>40){
				return (xLen*i*2);
			}
		}
		return 40;
	}
	/**
	 * get the prefer line with height draw the Bg line
	 * @return
	 */
	private double getPreferLineHeigh(){
		for(int i=0;i<10;i++){
			if(xLen*i*2>30){
				return (xLen*i*2);
			}
		}
		return 30;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.white);
		
		double height = dimension.getHeight();
		double width = dimension.getWidth();
		xLen = width/list.size();

		
		double preferLineWidth = getPreferLineWidth();
		double preferLineHeight = getPreferLineHeigh();
		//draw bg line画背景线
		for(int i=0;i<width/preferLineWidth;i++){
			g.setColor(BG_LINE_COLOR);
			g.drawLine((int)(i*preferLineWidth+xLen/3), 0, (int)(i*preferLineWidth+xLen/3), (int) height);
		}
		
		for(int i=0;i<height/preferLineHeight;i++){
			g.setColor(BG_LINE_COLOR);
			g.drawLine(0, (int)(i*preferLineHeight), (int)width, (int)(i*preferLineHeight));
		}	
		
		
		//画出股票涨跌幅信息
		double xGap = xLen/3;
		double ySubLen = height/(higestMoney-lowestMoney);//every 0.01's gap
		for(int i=0;i<list.size();i++){
			EastMoneyRiseDropHistory curHis = list.get(i);
			//当开盘,收盘数据有异常时,不画这条信息
			if(curHis.isHealthy()){
				double shoupan = curHis.getShoupan();
				double kaipan = curHis.getKaipan();
				double higestPrice = curHis.getHigestPrice();
				double lowestPrice = curHis.getLowestPrice();
				int x = (int) (i*xLen);
				
				int kanpanY = (int) ((kaipan-lowestMoney)*ySubLen);
				int shoupanY = (int) ((shoupan-lowestMoney)*ySubLen);
				int higestY = (int) ((higestPrice-lowestMoney)*ySubLen);
				int lowestY = (int) ((lowestPrice-lowestMoney)*ySubLen);
				if(curHis.getRiseDropMoney()>0){
					int yGap = (int) (ySubLen*(shoupan-kaipan));
					//hidden color so that mouse over can know 
					g.setColor(HIDDEN_COLOR);
					g.drawRect(x, (int) (height-kanpanY), (int) (xLen-xGap), -yGap);
					g.setColor(RED_COLOR);
					drawRect(x, (int) (height-kanpanY), (int) (xLen-xGap), -yGap,g);
					
					//中间需要空的，所以需要画两条线，因为是收涨所以当然是收盘大于开盘，A条线是最高到收盘，B条线是最低到开盘
					g.drawLine(x+(int)(xLen-xGap)/2, (int) (height-higestY), x+(int)(xLen-xGap)/2, (int) (height-shoupanY));
					g.drawLine(x+(int)(xLen-xGap)/2, (int) (height-kanpanY), x+(int)(xLen-xGap)/2, (int) (height-lowestY));
				}else{
					g.setColor(GREEN_COLOR);
					int yGap = (int) (ySubLen*(kaipan-shoupan));
					g.drawRect(x, (int) (height-shoupanY), (int) (xLen-xGap), -yGap);
					//这里也画两条线吧
					//因为是收跌，所以A线应该是最高到开盘，B线应该是最低到收盘
					g.drawLine(x+(int)(xLen-xGap)/2, (int) (height-higestY), x+(int)(xLen-xGap)/2, (int) (height-kanpanY));
					g.drawLine(x+(int)(xLen-xGap)/2, (int) (height-shoupanY), x+(int)(xLen-xGap)/2, (int) (height-lowestY));
				}
			}
		}
		
		//draw stock message显示股票ID,name
		g.setColor(Color.BLACK);
		g.drawString(eastMoneyStock.getEmStockId()+" "+eastMoneyStock.getEmStockName(),(int)width-100, 15);
		g.drawString("  current:"+selectRow+"/"+eastMoneyStocks.size(), (int)width-100,30);
	}
	
	private void drawRect(int x,int y,int width,int height,Graphics g){
		g.drawLine(x, y, x+width, y);
		g.drawLine(x, y+height, x+width, y+height);
		g.drawLine(x, y, x, y+height);
		g.drawLine(x+width, y, x+width, y+height);
	}


	/* (non-Javadoc)
	 * @see java.awt.Component#repaint()
	 */
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
		Dimension dimension = this.getSize();
		if(dimension.getWidth()>0&&dimension.getHeight()>0){
			this.dimension = dimension;
		}
	}


	public static void main(String[] args) {
		try {
			EastMoneyRiseDrop drop = new EastMoneyRiseDrop();
			drop.setEmStockId("601299");
			drop.setEmStockName("test");
			new EastMoneyHistoryUI(drop);
			
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
	}
}
