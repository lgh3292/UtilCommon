/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

import com.lgh.util.logging.LogUtil;

/**
 * LayoutUtil
 * set defaultLookAndFeel
 * @author Administrator
 */
public class UIUtil {

    /**
     * set the component's default LookAndFeel
     * @param com
     */
    public static void setDefaultLookAndFeel(Component com) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UIUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UIUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UIUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(UIUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(com);
    }

    /**
     * add the lanscape orientation component
     */
//    public static Container void addComponentGroup(boolean createGap,boolean direction,Component... components){
//
//    }
    /**
     * add a row components
     * @param container
     * @param createGap whether needs gap
     * @param components
     */
    public static Container addHVComponentsGroup(Container container, boolean createGap, boolean directionH, Component... components) {
        try {
            int column = components.length;
            GroupLayout layout = new GroupLayout(container);
            container.setLayout(layout);
            //Turn on automatically adding gaps between components
            layout.setAutoCreateGaps(createGap);

            //Turn on autumatically creating gaps between components that touch
            //the edge of the container and the container
            layout.setAutoCreateContainerGaps(true);

            //create a squential group for the horizontal axis.
            GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

            if (directionH) {
                //The sequential group int turn contains some parallel groups.
                //For example:one parallel groups contains two JButton:beginJB,cancelJB;
                for (int j = 0; j < column; j++) {
                    ParallelGroup p = layout.createParallelGroup();
                    p.addComponent(components[j]);
                    hGroup.addGroup(p);
                }

                //Create a sequential group for the vertical axis.
                GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
                ParallelGroup p = layout.createParallelGroup(Alignment.BASELINE);
                for (int j = 0; j < column; j++) {
                    p.addComponent(components[j]);
                }
                vGroup.addGroup(p);
                layout.setHorizontalGroup(hGroup);
                layout.setVerticalGroup(vGroup);
            } else {
                //The sequential group int turn contains some parallel groups.
                //For example:one parallel groups contains two JButton:beginJB,cancelJB;
                ParallelGroup pv = layout.createParallelGroup();
                for (int j = 0; j < column; j++) {
                    pv.addComponent(components[j]);
                    hGroup.addGroup(pv);
                }
                //Create a sequential group for the vertical axis.
                GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
                for (int j = 0; j < column; j++) {
                    ParallelGroup ph = layout.createParallelGroup(Alignment.BASELINE);
                    ph.addComponent(components[j]);
                    vGroup.addGroup(ph);
                }
                layout.setHorizontalGroup(hGroup);
                layout.setVerticalGroup(vGroup);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return container;
    }

    /**
     * set BorderLayout
     * @param container
     * @param east
     * @param west
     * @param south
     * @param north
     * @param center
     */
    public static Container setBorderLayout(Container container, Component east, Component west, Component south, Component north, Component center) {
        container.setLayout(new BorderLayout());
        if (east != null) {
            container.add(east, BorderLayout.EAST);
        }
        if (west != null) {
            container.add(west, BorderLayout.WEST);
        }
        if (south != null) {
            container.add(south, BorderLayout.SOUTH);
        }
        if (north != null) {
            container.add(north, BorderLayout.NORTH);
        }
        if (center != null) {
            container.add(center, BorderLayout.CENTER);
        }
        return container;
    }

    /**
     * add planar components to the JPanel
     * @param jp
     * @param components
     */
    public static void addPlanarButtonGroup(JPanel jp, Component[][] components, boolean createGap) {
        int row = components.length;//row numbers

        int column = components[0].length;//tier numbers

        GroupLayout layout = new GroupLayout(jp);
        jp.setLayout(layout);
        //Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(createGap);

        //Turn on automatically creating gaps between components that touch
        //the edge of the container and the container.
        layout.setAutoCreateContainerGaps(false);

        //Create a sqquential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        //The sequential group int turn contains some parallel groups.
        //For example:one parallel groups contains two JButton:beginJB,cancelJB;
        for (int j = 0; j < column; j++) {
            ParallelGroup p = layout.createParallelGroup();
            for (int k = 0; k < row; k++) {
                if (components[k][j] != null) {
                    p.addComponent(components[k][j]);
                }
            }
            hGroup.addGroup(p);
        }
        layout.setHorizontalGroup(hGroup);
        //Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for (int i = 0; i < row; i++) {
            ParallelGroup p = layout.createParallelGroup(Alignment.BASELINE);
            for (int j = 0; j < column; j++) {
                if (components[i][j] != null) {
                    p.addComponent(components[i][j]);
                }
            }
            vGroup.addGroup(p);
        }
        layout.setVerticalGroup(vGroup);
    }

    /**
     * test the component
     */
    public static void showOnJFrame(Component com) {
        try {
            JFrame jf = new JFrame("test thread");
            jf.setSize(800, 600);
//        jf.setIconImage(Pic.loading);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setLayout(new BorderLayout());
            jf.add(new JScrollPane(com), BorderLayout.CENTER);
            KeyAdapter adapter= new KeyAdapter() {
    			
    			@Override
				public void keyTyped(KeyEvent e) {
    				// TODO Auto-generated method stub
    				LogUtil.info(e.getKeyCode());
    			}
    			
    			@Override
				public void keyReleased(KeyEvent e) {
    				// TODO Auto-generated method stub
    				LogUtil.info(e.getKeyCode());
    			}
    			
    			@Override
				public void keyPressed(KeyEvent e) {
    				// TODO Auto-generated method stub
    				LogUtil.info(e.getKeyCode());
    			}
    		};
    		jf.addKeyListener(adapter);
            jf.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        UIUtil.showOnJFrame(addHVComponentsGroup(new JPanel(), true, false, new JButton("a"), new JButton("a"), new JButton("a")));
    }
}
