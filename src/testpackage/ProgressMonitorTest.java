/*
 * Main.java
 *
 * Created on 2007��4��16��, ����9:08
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
        // ����һ��������Click me���Ĵ���
        final JFrame f = new JFrame("ProgressMonitor Sample");
        f.getContentPane().setLayout(new FlowLayout());
        JButton b = new JButton("Click me");
        f.getContentPane().add(b);
        f.pack();
        
        // ���ð�ť�Ķ����¼�
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ���ʹ�����µ��̴߳���ť�Ķ����¼�����Ϊ������Ҫ
                //�����ڵ��߳���Ӧ�û�����������Զ�ε���ð�ť��
                //�����������ȡ�ļ����̡߳�������Ҳ������Ӧ��
                new Thread() {
                    @Override
					public void run() {
                        try {
                            // ���ļ����������InputStream��װ��ProgressMonitorInputStream�С�
                            //�ڵ�ǰĿ¼����Ҫ����һ�����ļ������鳬��50M
                            InputStream in = new FileInputStream("C:\\TEMP\\encryption.exe");
                            ProgressMonitorInputStream pm =
                                    new ProgressMonitorInputStream(f,"Reading a big file",in);
                            // ��ȡ�ļ�������ܺ�ʱ����2�룬�����Զ�����һ�����ȼ��Ӵ��ڡ�
                            //   ��ʾ�Ѷ�ȡ�İٷֱȡ�
                            int c;
                            while((c=pm.read()) != -1) {
                            
                                // �������
                            }
                            pm.close();
                            System.out.println("finish");
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.start();
            }});
            
            // ����ȱʡ�Ĵ��ڹر���Ϊ������ʾ���ڡ�
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
    }
}
