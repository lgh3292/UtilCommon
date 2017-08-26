package testpackage;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.JButton;

public class ClassButton extends JButton implements MouseListener{
	
	public ClassButton(String name){
		this.setText(name);
		ScheduledThreadPoolExecutor s = null;
		s.allowCoreThreadTimeOut(true);
		this.addMouseListener(this);
		this.setBackground(Color.gray);
//		this.setIcon(...);
//		this.setBorder(...);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
