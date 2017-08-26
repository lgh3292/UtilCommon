package testpackage;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {
    private int x =50;
   // privJScrollPaneÒ»¸öË«Ïò¹öÆÁate 
    int y = 50;
    private Image offScreenImage = null;
    /** Creates a new instance of Main */
    public TankClient() {
        this.setSize(800,600);
        this.addWindowListener(new WindowAdapter(){
            @Override
			public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        new Thread(new ThreadSleep()).start();
    }
    @Override
	public void paint(Graphics  g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,30,30);
        g.setColor(c);
        x = x+1;
    }
    //
    @Override
	public void update(Graphics g){
        if(offScreenImage == null){
            offScreenImage = this.createImage(800,600);
        }
        Graphics goffScreenImage = offScreenImage.getGraphics();
        Color c = goffScreenImage.getColor();
        goffScreenImage.setColor(Color.WHITE);
        goffScreenImage.fillRect(0,0,800,600);
        paint(goffScreenImage);
        g.drawImage(offScreenImage,0,0,null);
    }
    private class ThreadSleep implements Runnable{
        public ThreadSleep(){
        }
        public void run() {
            while(true){
                repaint();
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TankClient tc = new TankClient();
        
    }
    
    
}
