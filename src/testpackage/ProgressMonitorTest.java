/*
 * Main.java
 *
 * Created on 2007年4月16日, 上午9:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package testpackage;

/**
 *
 * @author tewang
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ProgressMonitorInputStream;

public class ProgressMonitorTest {
    public static void main(String[] args) {
        // 创建一个包含“Click me”的窗口
        final JFrame f = new JFrame("ProgressMonitor Sample");
        f.getContentPane().setLayout(new FlowLayout());
        JButton b = new JButton("Click me");
        f.getContentPane().add(b);
        f.pack();
        
        // 设置按钮的动作事件
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 这儿使用了新的线程处理按钮的动作事件，因为我们需要
                //主窗口的线程响应用户。这样你可以多次点击该按钮，
                //会启动多个读取文件的线程。主窗口也保持响应。
                new Thread() {
                    @Override
					public void run() {
                        try {
                            // 打开文件输出流，把InputStream包装在ProgressMonitorInputStream中。
                            //在当前目录中需要放置一个大文件，建议超过50M
                            InputStream in = new FileInputStream("C:\\TEMP\\encryption.exe");
                            ProgressMonitorInputStream pm =
                                    new ProgressMonitorInputStream(f,"Reading a big file",in);
                            // 读取文件，如果总耗时超过2秒，将会自动弹出一个进度监视窗口。
                            //   显示已读取的百分比。
                            int c;
                            while((c=pm.read()) != -1) {
                            
                                // 处理代码
                            }
                            pm.close();
                            System.out.println("finish");
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.start();
            }});
            
            // 设置缺省的窗口关闭行为，并显示窗口。
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
    }
}
