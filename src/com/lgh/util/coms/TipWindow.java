/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.coms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.lgh.util.FileUtil;
import com.lgh.util.MouseMotionUtil;
import com.lgh.util.logging.LogUtil;

/**
 * popup the tips,it may String,or the URL
 * @author lgh
 */
public class TipWindow extends JDialog implements Runnable {

    private static Dimension dim;
    private int x, y;
    private int width, height;
    private Component centerCom;

    {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        width = 200;
        height = 150;
        x = (int) (dim.getWidth() - width);
        y = (int) (dim.getHeight());
    }

    public TipWindow(Component centerCom) {
        this.centerCom = centerCom;
        initComponents();
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i <= height + 30; i += 60) {
            try {
                this.setLocation(x, y - i);
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(TipWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setAlwaysOnTop(true);
//        while(this.)
    }

    private void initComponents() {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setLayout(new BorderLayout());
        JPanel tipBar = createTipBar();
        new MouseMotionUtil().addMouseMotionListener(this, tipBar);
        this.add(tipBar, BorderLayout.NORTH);
        this.add(createCenterPanel(), BorderLayout.CENTER);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    /**
     * return the center JPanel
     * @return
     */
    private JPanel createCenterPanel() {
        JPanel mp = new JPanel();
        mp.add(centerCom);
        return mp;
    }

    /**
     * 创建上面的工具条
     */
    private JPanel createTipBar() {
        TipBar tipBar = new TipBar(null);
        return tipBar;
    }

    private class TipBar extends JPanel implements ActionListener {

        private JButton closeButton;

        public TipBar(Image bgImage) {
            initComponents();
        }

        private void initComponents() {
            closeButton = new JButton("test");
            this.setLayout(null);
//            closeButton.addListenAndCommand(this, "closeButton");
            this.add(closeButton);
            this.setPreferredSize(new Dimension(width, 26));
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command != null) {
                if (command.equals("closeButton")) {
                    TipWindow.this.dispose();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            StringBuffer sb = FileUtil.readContect(new File("d://损害个人魅力的26条错.txt"));
            LogUtil.info("");
            new TipWindow(new JLabel(sb.toString()));
//        new TipWindow(new JLabel("<html>tes<td>tde<td>aldkjfaldskfj<td></html>"));
        } catch (IOException ex) {
            Logger.getLogger(TipWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

