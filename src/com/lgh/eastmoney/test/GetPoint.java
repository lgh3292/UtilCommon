package com.lgh.eastmoney.test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GetPoint extends JFrame{
	JLabel mousePointLabel,pointColorLabel,printColorLabel;
	JTextField mousePointTextField,pointColorTextField,pointColor16TextField;
	public GetPoint(){
		this.setTitle("鼠标工具");

		mousePointLabel=new JLabel("鼠标坐标：");
		pointColorLabel=new JLabel("获取颜色：");
		printColorLabel=new JLabel("■RGB■");
		mousePointTextField=new JTextField();
		pointColorTextField=new JTextField();
		pointColor16TextField=new JTextField();

		mousePointTextField.setFocusable(false);
		pointColorTextField.setFocusable(false);
		pointColor16TextField.setFocusable(false);
		
		Point point= MouseInfo.getPointerInfo().getLocation();
		mousePointTextField.setText(point.x+","+point.y);
		try {
			Color color = new Robot().getPixelColor(point.x, point.y);
			int red=color.getRed();
			int blue=color.getBlue();
			int green=color.getGreen();
			printColorLabel.setForeground(color);
			pointColorTextField.setText(red+","+green+","+blue);
			pointColor16TextField.setText(to16Str(red)+","+to16Str(green)+","+to16Str(blue));
		} catch (AWTException e1) {}
		
		this.setLayout(null);
		mousePointLabel.setBounds(25, 15, 75, 20);
		mousePointTextField.setBounds(95, 15, 75, 20);
		pointColorLabel.setBounds(25, 45, 75, 20);
		pointColorTextField.setBounds(95, 45, 75, 20);
		printColorLabel.setBounds(25, 75, 75, 20);
		pointColor16TextField.setBounds(95, 75, 75, 20);
		this.add(mousePointLabel);
		this.add(mousePointTextField);
		this.add(pointColorLabel);
		this.add(pointColorTextField);
		this.add(printColorLabel);
		this.add(pointColor16TextField);
		this.setBounds(200, 200, 200, 135);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
		this.getPoint();
	}
	
	/**
	 * 循环为界面值复制
	 */
	private void getPoint(){
		int x,y,lx=-1,ly=-1;
		while(true){
			Point point= MouseInfo.getPointerInfo().getLocation();//通过MouseInfo获取鼠标指针当前坐标值
			x=point.x;
			y=point.y;
			if(lx!=x||ly!=y){//如果鼠标指针位置与记录位置不一致，重新为界面元素赋值
				lx=x;ly=y;
				mousePointTextField.setText(x+","+y);
				try {
					Color color=new Robot().getPixelColor(x, y);//通过Robot实例获取指定坐标位置的颜色
					int red=color.getRed();
					int blue=color.getBlue();
					int green=color.getGreen();
					printColorLabel.setForeground(color);
					pointColorTextField.setText(red+","+green+","+blue);
					pointColor16TextField.setText(to16Str(red)+","+to16Str(green)+","+to16Str(blue));
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(50);
				System.gc();//睡眠50毫秒，然后调用gc来回收垃圾，尽量减少内存使用（循环中最好带上）
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取数字的16进制值
	 * @param num
	 * @return
	 */
	private String to16Str(int num){
		String[] array=new String[]{"0","1","2","3","4","5","6","7",
									"8","9","A","B","C","D","E","F"};
		if(num<16){
			return array[num];
		}
		int front=num/16;
		int end=num%16;
		String ends=array[end];
		if(front>16){
			return to16Str(front)+ends;
		}else{
			return array[front]+ends;
		}
	}

	public static void main(String[] args) {
		new GetPoint();
	}
}

