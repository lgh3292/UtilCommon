/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lgh.util;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * add mouse moved components 
 * add the component 
 * @author lgh
 */
public class MouseMotionUtil {
    //move

    private int relativeX; //relatively x coordinate
    private int relativeY; //relatively y coordinate
    private int absoluteX; //absolute x coordinate
    private int absoluteY; //absolute y coordinate
    private int setX;//moving mouse's x coordinate
    private int setY;//moving mouse's y coordinate
    private boolean isPressed;//whether is mouse id press,default value is false
    private Component parent;
    /**implement mouse moving**/
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean draging = false;
    private Robot robot;
    private Image image;
    private JWindow jf;
    private JPanel jp;
    private Rectangle motionRec;
    private float[] dash1 = {1.0f};
    private BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

    public MouseMotionUtil() {
        dim.setSize(dim.getWidth(), dim.getHeight() - 30);
        motionRec = new Rectangle();
        jp = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
                }
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(stroke);
                g2d.draw(motionRec);
            }
        };

    }

    public void newWindow() {
        jf = new JWindow();
        jf.setSize(dim);
        jf.setLayout(new BorderLayout());
        jf.add(jp, BorderLayout.CENTER);
    }

    /**
     * add moving listener and mouse listener to current component
     */
    public void addMouseMotionListener(Component parent, Component c) {
        this.parent = parent;
        addMotionAndMouseListener(c);
    }

    /**
     * add moving listener to the component
     */
    private void addMotionAndMouseListener(Component c) {
        c.addMouseListener(new MyMouseListener());
        c.addMouseMotionListener(new MyMouseMotionListener());
    }

    /**
     * get the Screen's image
     */
    public void getRobotImage() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                Logger.getLogger(MouseMotionUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        image = robot.createScreenCapture(new Rectangle(dim.width, dim.height));
    }

    private class MyMouseListener extends MouseAdapter {

        @Override
		public void mousePressed(MouseEvent e) {
            isPressed = true;
            relativeX = e.getX();
            relativeY = e.getY();

        }

        @Override
		public void mouseReleased(MouseEvent e) {
            if (isPressed) {
                absoluteX = parent.getLocationOnScreen().x + e.getX();
                absoluteY = parent.getLocationOnScreen().y + e.getY();
                setX = absoluteX - relativeX;
                setY = absoluteY - relativeY;
                parent.setLocation(setX, setY);
            }
            if (draging && isPressed) {
                jf.dispose();
                jf = null;
                draging = false;
            }
            isPressed = false;
        }
    }

    private class MyMouseMotionListener extends MouseMotionAdapter {

        /**implement that just move broken line,the others is not**/
        @Override
		public void mouseDragged(MouseEvent e) {
            try {
                if (isPressed && !draging) {
                    draging = true;
                    newWindow();
                    getRobotImage();
                    motionRec.setSize(parent.getSize());
                    if (!jf.isVisible()) {
                        jf.setVisible(true);
                    }
                }
                absoluteX = parent.getLocationOnScreen().x + e.getX();
                absoluteY = parent.getLocationOnScreen().y + e.getY();
                setX = absoluteX - relativeX;
                setY = absoluteY - relativeY;
                motionRec.setLocation(setX, setY);
                /**reduce the repaint count **/
                if ((setX + setY) % 2 == 0) {
                    jp.repaint();
                }
            } catch (Exception ex) {
                Logger.getLogger(MouseMotionUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

