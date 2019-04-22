/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.lgh.util.logging.LogUtil;

/**
 *
 * @author Administrator
 */
public class PicUtil {
public static void main(String[] args) {
	LogUtil.info(PicUtil.TrayIcon==null);
}
    public static JPanel jp = new JPanel();
    public static MediaTracker mt1;
    /**load status image,MailToolBar background image**/
    public static Image TrayIcon;

    static {
        try {
        	mt1 = new MediaTracker(jp);
            TrayIcon = loadImage(new File("source/1.png").getAbsolutePath(), 0);
			mt1.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    //buttons
    public static Map<String, Image[]> map;
    public static MediaTracker mt2;
      
   
    public static Hashtable<String, Image> hash = new Hashtable<String, Image>();

    /** Creates a new instance of pic */
    public PicUtil() {
    }

    static {
        try {
            MediaTracker mt2 = new MediaTracker(jp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    /**
     * add images
     */
    public static Image loadImage(String filename, int index) {
        Image image = Toolkit.getDefaultToolkit().createImage(filename);
        mt1.addImage(image, index);
        return image;
    }

    /**
     * loadImage  appoint the count 
     */
    public static void loadImage(String str, int nums, int index) {
        Image[] images = new Image[nums];
        for (int i = 0; i < images.length; i++) {
            images[i] = Toolkit.getDefaultToolkit().createImage(PicUtil.class.getResource(str
                    + (i + 1) + ".png"));
            mt2.addImage(images[i], index + i);
        }
        map.put(str, images);
    }

    /**
     * get the image by URL
     * @param filePath
     * @return
     */
//    public static ImageIcon createIcon(URL url) {
//
//        if (url != null) {
//            return new ImageIcon(url);
//        } else {
//            return null;
//        }
//    }
    /**
     * Returns an ImageIcon,or null if the path waw invalid.
     */
    public static ImageIcon crateIcon(String filePath) {
        URL url = PicUtil.class.getResource(filePath);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("Couldn't find file:" + filePath);
            return null;
        }
    }
    /**
     * get the icon by the filename's suffix
     */
    public static Image getImage(String postfix) {
        Image bi = null;
        if ((bi = hash.get(postfix)) != null) {
            return bi;
        } else {
            try {
                bi = ImageIO.read(PicUtil.class.getResource(postfix));
                hash.put(postfix, bi);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return bi;
    }

    
    /**
     * get the icon by the filename's suffix
     */
    public static Image getImage(InputStream is) {
        Image bi = null;
        try {
            bi = ImageIO.read(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bi;
    }
    /**
     * cut the image,return the planar array
     * @param image
     * @param width (the first image's width)
     * @param height(the first image's height)
     * @return
     */
    public static BufferedImage[][] getBufferedImages2(BufferedImage image, int subWidth,
            int subHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        int columnCount = width / subWidth;
        int rowCount = height / subHeight;
        BufferedImage[][] buf = new BufferedImage[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                buf[i][j] = image.getSubimage(j * subWidth, i * subHeight, subWidth, subHeight);
            }
        }
        return buf;
    }

    /**
     * cut the image
     * @param image
     * @param width (the first image's width)
     * @param height(the first image's height)
     * @return
     */
    public static BufferedImage[] getBufferedImages(BufferedImage image, int subWidth, int subHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        int columnCount = width / subWidth;
        int rowCount = height / subHeight;
        BufferedImage[] buf = new BufferedImage[rowCount * columnCount];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < columnCount; j++) {
                int k = columnCount * i + j;
                buf[k] = image.getSubimage(j * subWidth, i * subHeight, subWidth, subHeight);
            }
        }
        return buf;
    }

    /**
     * do lucency to the image
     * @param buf
     * @return
     */
    public static BufferedImage doLucency(BufferedImage image, Color c) {
        int crgb = c.getRGB();
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int j = 0; j < width; j++) {
            for (int k = 0; k < height; k++) {
                int rgb = image.getRGB(j, k);
                if ((crgb ^ rgb) == 0) {
                    temp.setRGB(j, k, 0x00FFFFFF);
                } else {
                    temp.setRGB(j, k, rgb);
                }
            }
        }
        image = temp;
        return image;
    }

    /**
     * do lucency to batch images
     * @param buf
     * @return
     */
    public static BufferedImage[] doLucency(BufferedImage[] buf, Color c) {
        for (int i = 0; i < buf.length; i++) {
            buf[i] = doLucency(buf[i], c);
        }
        return buf;
    }
}
