package com.lgh.util.db;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.lgh.util.UIUtil;

public class LoginDialog extends JDialog{
	//database type and driver
	private JComboBox dbNameBox;
	private JComboBox driverBox;
	//connect url
	private JTextField urlTextField;
    private JPasswordField jPasswordField1;
    private JComboBox nameComboBox;
    
	public LoginDialog(){
		
	}
	
	private JPanel initJPanel(){
		JPanel jp = new JPanel();
		UIUtil.addHVComponentsGroup(jp, false, true, new JLabel("test"),new JLabel("tset2"));
		return jp;
	}
	public static void main(String[] args) {
		UIUtil.showOnJFrame(new LoginDialog().initJPanel());
	}
}
