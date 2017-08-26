package com.lgh.util.coms;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.lgh.util.PicUtil;

public class UISystemTray{
	private static UISystemTray INSTANCE = null;
	public final String ACTION_COMMAND = "TaskbarEvent";
    private PopupMenu pop;
    private SystemTray systemTray;
    private TrayIcon trayIcon;
    private ActionListener listener;
    private String toolTip;
    
    public static UISystemTray getInstance() {
    	return INSTANCE;
    }
    
    public static UISystemTray getInstance(ActionListener listener,PopupMenu pop,String toolTip) {
    	synchronized (UISystemTray.class) {
    		if(INSTANCE==null){
        		INSTANCE = new UISystemTray(listener, pop, toolTip);
        	}
		}
    	return INSTANCE;
    }
    private UISystemTray(ActionListener listener,PopupMenu pop,String toolTip) {
        try {
        	this.listener = listener;
        	this.toolTip = toolTip;
        	this.pop = pop;
			initMySystemTray();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * get TrayIcon
     * @return
     */
    public TrayIcon getTrayIcon(){
    	return trayIcon;
    }

    private void initMySystemTray() throws Exception {
    	trayIcon = new TrayIcon(PicUtil.TrayIcon,"×îÐ¡»¯", pop);
        systemTray = SystemTray.getSystemTray();
        trayIcon.setToolTip(toolTip);
//        trayIcon.displayMessage("caption", "text",null);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(listener);
        trayIcon.setActionCommand(ACTION_COMMAND);
        
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }
 
}
