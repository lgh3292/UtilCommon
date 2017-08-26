package com.lgh.util.imagerecognize;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
   
/**  
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2008</p>  
 * <p>Company: </p>  
 * @author not attributable  
 * @version 1.0  
 */   
   
public class Tickets extends JFrame {   
  JPanel framMain;   
  JMenuBar jMenuBar1 = new JMenuBar();   
  JMenu jMenuFile = new JMenu();   
  JMenuItem jMenuFileLoad = new JMenuItem();   
  JMenuItem jMenuFileExit = new JMenuItem();   
  JMenu jMenuHelp = new JMenu();   
  JMenuItem jMenuHelpAbout = new JMenuItem();   
  JButton btn1 = new JButton();   
  TitledBorder titledBorder1;   
  JLabel msg1 = new JLabel();   
  TitledBorder titledBorder2;   
  JLabel msg2 = new JLabel();   
  JLabel msg3 = new JLabel();   
  JLabel msg4 = new JLabel();   
  JLabel msg5 = new JLabel();   
  JButton btn2 = new JButton();   
  JButton btn3 = new JButton();   
  JButton btn4 = new JButton();   
  JButton btn5 = new JButton();   
  JTextField txtCode1 = new JTextField();   
  JTextField txtCode2 = new JTextField();   
  JTextField txtCode3 = new JTextField();   
  JTextField txtCode4 = new JTextField();   
  JTextField txtCode5 = new JTextField();   
  TitledBorder titledBorder3;   
  TitledBorder titledBorder4;   
   
   
  //ipconfig file   
  IPConfig ipcfg;   
  TitledBorder titledBorder5;   
  public static JLabel failCnt = new JLabel();   
  public static JLabel successCnt = new JLabel();   
   
  JButton code1 = new JButton();   
  JButton code2 = new JButton();   
  JButton code3 = new JButton();   
  JButton code4 = new JButton();   
  JButton code5 = new JButton();   
   
  public static JTextArea input = new JTextArea();   
  public static JTextField txtArticle = new JTextField();   
  JScrollPane sInput = new JScrollPane();   
   
  JButton btnAdd = new JButton();   
   
  JLabel labArticle = new JLabel();   
  public static JTextField txtInterval = new JTextField();   
  JLabel jLabel1 = new JLabel();   
  JLabel jLabel2 = new JLabel();   
  JButton btnRun = new JButton();   
   
  private boolean isRuning = false;   
   
  //Construct the frame   
  public Tickets() {   
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);   
    try {   
      jbInit();   
    }   
    catch(Exception e) {   
      e.printStackTrace();   
    }   
  }   
   
  static public void main(String[] args){   
  	System.setProperty("http.proxySet", "true");
    System.setProperty("http.proxyHost", "proxy.tay.cpqcorp.net");
    System.setProperty("http.proxyPort", "8080");
        Tickets tickets = new Tickets();   
        tickets.show();   
  }   
   
  //Component initialization   
  private void jbInit() throws Exception  {   
    framMain = (JPanel) this.getContentPane();   
    titledBorder1 = new TitledBorder("");   
    titledBorder2 = new TitledBorder("");   
    titledBorder3 = new TitledBorder("");   
    titledBorder4 = new TitledBorder("");   
    titledBorder5 = new TitledBorder("");   
    framMain.setLayout(null);   
    this.setSize(new Dimension(410, 383));   
    this.setTitle("自动投票器V1.0");   
    jMenuFile.setText("File");   
    jMenuFileLoad.setText("Load");   
   
    jMenuFileLoad.addActionListener(new Tickets_jMenuClick_ActionAdapter(this));   
   
    jMenuFileExit.setText("Exit");   
    jMenuFileExit.addActionListener(new Tickets_jMenuClick_ActionAdapter(this));   
    jMenuHelp.setText("Help");   
    jMenuHelpAbout.setText("About");   
    jMenuHelpAbout.addActionListener(new Tickets_jMenuClick_ActionAdapter(this));   
    btn1.setBounds(new Rectangle(212, 34, 55, 25));   
    //btn1.setEnabled(false);   
    btn1.setText("change");   
   
    framMain.setEnabled(false);   
    framMain.setFont(new java.awt.Font("Dialog", 0, 11));   
    framMain.setBorder(BorderFactory.createLineBorder(Color.black));   
    msg1.setBorder(BorderFactory.createEtchedBorder());   
    msg1.setText("");   
    msg1.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
    msg1.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);   
    msg1.setBounds(new Rectangle(275, 34, 111, 24));   
    msg2.setBounds(new Rectangle(275, 68, 111, 24));   
    msg2.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);   
    msg2.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
    msg2.setBorder(BorderFactory.createEtchedBorder());   
    msg2.setText("");   
    msg3.setBounds(new Rectangle(275, 104, 111, 24));   
    msg3.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);   
    msg3.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
    msg3.setBorder(BorderFactory.createEtchedBorder());   
    msg3.setText("");   
    msg4.setBounds(new Rectangle(275, 140, 111, 24));   
    msg4.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);   
    msg4.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
    msg4.setBorder(BorderFactory.createEtchedBorder());   
    msg4.setText("");   
    msg5.setBounds(new Rectangle(275, 175, 111, 24));   
    msg5.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);   
    msg5.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
    msg5.setBorder(BorderFactory.createEtchedBorder());   
    msg5.setText("");   
    btn2.setText("change");   
    btn2.setBounds(new Rectangle(212, 72, 55, 25));   
    //btn2.setEnabled(false);   
    btn2.setDoubleBuffered(false);   
    btn3.setBounds(new Rectangle(212, 105, 55, 25));   
    //btn3.setEnabled(false);   
    btn3.setDoubleBuffered(false);   
    btn3.setText("change");   
    btn4.setBounds(new Rectangle(211, 141, 55, 25));   
    //btn4.setEnabled(false);   
    btn4.setSelected(false);   
    btn4.setText("change");   
    btn5.setBounds(new Rectangle(212, 178, 55, 25));   
    //btn5.setEnabled(false);   
    btn5.setText("change");   
   
    //txtCode1.setEnabled(false);   
    txtCode1.setNextFocusableComponent(txtCode2);   
    txtCode1.setFocusAccelerator('1');   
    txtCode1.setText("");   
    txtCode1.setBounds(new Rectangle(120, 37, 86, 20));   
    //txtCode2.setEnabled(false);   
    txtCode2.setNextFocusableComponent(txtCode3);   
    txtCode2.setFocusAccelerator('2');   
    txtCode2.setText("");   
    txtCode2.setBounds(new Rectangle(120, 71, 86, 20));   
    //txtCode3.setEnabled(false);   
    txtCode3.setNextFocusableComponent(txtCode4);   
    txtCode3.setFocusAccelerator('3');   
    txtCode3.setText("");   
    txtCode3.setBounds(new Rectangle(120, 107, 86, 20));   
    //txtCode4.setEnabled(false);   
    txtCode4.setNextFocusableComponent(txtCode5);   
    txtCode4.setFocusAccelerator('4');   
    txtCode4.setText("");   
    txtCode4.setBounds(new Rectangle(120, 142, 86, 20));   
    //txtCode5.setEnabled(false);   
    txtCode5.setNextFocusableComponent(txtCode1);   
    txtCode5.setFocusAccelerator('5');   
    txtCode5.setText("");   
    txtCode5.setBounds(new Rectangle(120, 181, 86, 20));   
   
   
    failCnt.setBounds(new Rectangle(31, 229, 349, 18));   
    failCnt.setHorizontalAlignment(SwingConstants.LEFT);   
    failCnt.setHorizontalTextPosition(SwingConstants.CENTER);   
    failCnt.setText("失败:0");   
    successCnt.setText("成功:0");   
    successCnt.setBounds(new Rectangle(29, 206, 354, 18));   
    sInput.setBounds(new Rectangle(32, 248, 348, 50));   
    input.setRequestFocusEnabled(true);   
    input.setText("");   
    btnAdd.setBounds(new Rectangle(30, 302, 113, 21));   
    btnAdd.setText("加  入");   
    btnAdd.addActionListener(new Tickets_btnAdd_actionAdapter(this));   
    code1.setBounds(new Rectangle(32, 34, 82, 29));   
    code1.setBorder(BorderFactory.createLineBorder(Color.black));   
    code1.setText("");   
    code2.setText("");   
    code2.setBorder(BorderFactory.createLineBorder(Color.black));   
    code2.setBounds(new Rectangle(33, 67, 82, 29));   
    code3.setText("");   
    code3.setBorder(BorderFactory.createLineBorder(Color.black));   
    code3.setBounds(new Rectangle(32, 103, 82, 29));   
    code4.setText("");   
    code4.setBorder(BorderFactory.createLineBorder(Color.black));   
    code4.setBounds(new Rectangle(32, 138, 82, 29));   
    code5.setText("");   
    code5.setBorder(BorderFactory.createLineBorder(Color.black));   
    code5.setBounds(new Rectangle(32, 174, 82, 29));   
    txtArticle.setText("88");   
    txtArticle.setBounds(new Rectangle(65, 10, 44, 21));   
    txtArticle.addFocusListener(new Tickets_txtArticle_focusAdapter(this));   
    labArticle.setText("编号");   
    labArticle.setBounds(new Rectangle(34, 10, 50, 18));   
    txtInterval.setText("1250");   
    txtInterval.setBounds(new Rectangle(181, 9, 49, 20));   
    txtInterval.addFocusListener(new Tickets_txtInteval_focusAdapter(this));   
   
    jLabel1.setText("时间间隔");   
    jLabel1.setBounds(new Rectangle(115, 9, 63, 21));   
    jLabel2.setText("毫秒");   
    jLabel2.setBounds(new Rectangle(233, 10, 42, 18));   
    btnRun.setBounds(new Rectangle(316, 10, 68, 20));   
    btnRun.setText("开 始");   
    btnRun.addActionListener(new Tickets_btnRun_actionAdapter(this));   
    jMenuFile.add(jMenuFileLoad);   
    jMenuFile.add(jMenuFileExit);   
    jMenuHelp.add(jMenuHelpAbout);   
    jMenuBar1.add(jMenuFile);   
    jMenuBar1.add(jMenuHelp);   
    this.setJMenuBar(jMenuBar1);   
    framMain.add(txtCode1, null);   
    framMain.add(btn1, null);   
    framMain.add(msg1, null);   
    framMain.add(msg2, null);   
    framMain.add(btn2, null);   
    framMain.add(txtCode2, null);   
    framMain.add(txtCode3, null);   
    framMain.add(btn3, null);   
    framMain.add(msg3, null);   
    framMain.add(msg4, null);   
    framMain.add(btn4, null);   
    framMain.add(txtCode4, null);   
    framMain.add(txtCode5, null);   
    framMain.add(btn5, null);   
    framMain.add(msg5, null);   
    framMain.add(code1, null);   
    framMain.add(code5, null);   
    framMain.add(code2, null);   
    framMain.add(code3, null);   
    framMain.add(code4, null);   
    framMain.add(btnRun, null);   
    framMain.add(labArticle, null);   
    framMain.add(jLabel1, null);   
    framMain.add(txtInterval, null);   
    framMain.add(jLabel2, null);   
    framMain.add(txtArticle, null);   
    framMain.add(sInput, null);   
    sInput.getViewport().add(input, null);   
    framMain.add(btnAdd, null);   
    framMain.add(failCnt, null);   
    framMain.add(successCnt, null);   
   
    //init thread   
    //this.initThread();   
   
  }   
   
   
  //File | Exit action performed   
  public void load() {   
    JFileChooser jfc=new JFileChooser();   
    jfc.showOpenDialog(this);   
    jfc.setFileHidingEnabled(false);   
    if(jfc.getSelectedFile()!=null ){   
      File file = jfc.getSelectedFile();   
      if (file.exists()) {   
          if(ipcfg == null ){   
            ipcfg = new IPConfig();   
          }   
          ipcfg.load(file);   
          IPConfig.debug();   
      }   
    }   
 }   
   
  public void jMenuClick_actionPerformed(ActionEvent e) {   
      if( e == null)   
          System.exit(0);   
   
      JMenuItem item = (JMenuItem)e.getSource();   
   
      if( item.getText().equals("Load")){   
        // System.out.println("Load");   
         this.load();   
      }   
      else if(item.getText().equals("Exit")){   
          //System.out.println("Exit");   
          System.exit(0);   
      }   
      else if(item.getText().equals("About")){   
         //System.out.println("About");   
      }   
  }   
   
   
  @Override
protected void processWindowEvent(WindowEvent e) {   
    super.processWindowEvent(e);   
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {   
       jMenuClick_actionPerformed(null);   
    }   
  }   
   
  private void initThread(){   
    //Vote Thread   
    Vote vote_1 = new Vote(code1,txtCode1,btn1,msg1);   
    Vote vote_2 = new Vote(code2,txtCode2,btn2,msg2);   
    Vote vote_3 = new Vote(code3,txtCode3,btn3,msg3);   
    Vote vote_4 = new Vote(code4,txtCode4,btn4,msg4);   
    Vote vote_5 = new Vote(code5,txtCode5,btn5,msg5);   
   
    btn1.addActionListener(new Vote_actionAdapter(vote_1));   
    btn2.addActionListener(new Vote_actionAdapter(vote_2));   
    btn3.addActionListener(new Vote_actionAdapter(vote_3));   
    btn4.addActionListener(new Vote_actionAdapter(vote_4));   
    btn5.addActionListener(new Vote_actionAdapter(vote_5));   
    txtCode1.addActionListener(new Vote_actionAdapter(vote_1));   
    txtCode2.addActionListener(new Vote_actionAdapter(vote_2));   
    txtCode3.addActionListener(new Vote_actionAdapter(vote_3));   
    txtCode4.addActionListener(new Vote_actionAdapter(vote_4));   
    txtCode5.addActionListener(new Vote_actionAdapter(vote_5));   
   
    code1.addActionListener(new Code_actionAdapter(vote_1));   
    code2.addActionListener(new Code_actionAdapter(vote_2));   
    code3.addActionListener(new Code_actionAdapter(vote_3));   
    code4.addActionListener(new Code_actionAdapter(vote_4));   
    code5.addActionListener(new Code_actionAdapter(vote_5));   
   
    vote_1.start();   
    try {   
        Thread.sleep(2500);   
    } catch (InterruptedException e) {   
        e.printStackTrace();   
    }   
    vote_2.start();   
    try {   
        Thread.sleep(1250);   
    } catch (InterruptedException e) {   
   
        e.printStackTrace();   
    }   
    vote_3.start();   
    try {   
        Thread.sleep(2250);   
    } catch (InterruptedException e) {   
   
        e.printStackTrace();   
    }   
    vote_4.start();   
    try {   
        Thread.sleep(3250);   
    } catch (InterruptedException e) {   
   
        e.printStackTrace();   
    }   
    vote_5.start();   
  }   
   
  void btnAdd_actionPerformed(ActionEvent e) {   
      if( input.getLineCount() > 0 ){   
          IPConfig.load(input.getText());   
          input.setText("");   
      }   
  }   
   
  void txtArticle_focusLost(FocusEvent e) {   
      Vote.articleId = ((JTextField)e.getSource()).getText();   
  }   
   
  void txtInterval_focusLost(FocusEvent e){   
      Vote.voteInterval = Long.parseLong(((JTextField)e.getSource()).getText());   
  }   
   
  void btnRun_actionPerformed(ActionEvent e) {   
        if(isRuning ) return;   
        this.initThread();   
        this.isRuning = true;   
  }   
   
}   
   
class Tickets_jMenuClick_ActionAdapter implements ActionListener {   
      Tickets adaptee;   
   
      Tickets_jMenuClick_ActionAdapter(Tickets adaptee) {   
        this.adaptee = adaptee;   
      }   
      public void actionPerformed(ActionEvent e) {   
          adaptee.jMenuClick_actionPerformed(e);   
     }   
}   
   
class Code_actionAdapter implements java.awt.event.ActionListener {   
  Vote adaptee;   
   
  Code_actionAdapter(Vote adaptee) {   
    this.adaptee = adaptee;   
  }   
  public void actionPerformed(ActionEvent e) {   
    adaptee.reGetCode();   
  }   
}   
   
   
class Vote_actionAdapter implements java.awt.event.ActionListener {   
  Vote adaptee;   
   
  Vote_actionAdapter(Vote adaptee) {   
    this.adaptee = adaptee;   
  }   
  public void actionPerformed(ActionEvent e) {   
      adaptee.reGetCode();   
  }   
}   
   
class Tickets_btnAdd_actionAdapter implements java.awt.event.ActionListener {   
  Tickets adaptee;   
   
  Tickets_btnAdd_actionAdapter(Tickets adaptee) {   
    this.adaptee = adaptee;   
  }   
  public void actionPerformed(ActionEvent e) {   
    adaptee.btnAdd_actionPerformed(e);   
  }   
}   
   
class Tickets_txtArticle_focusAdapter extends java.awt.event.FocusAdapter {   
  Tickets adaptee;   
   
  Tickets_txtArticle_focusAdapter(Tickets adaptee) {   
    this.adaptee = adaptee;   
  }   
  @Override
public void focusLost(FocusEvent e) {   
    adaptee.txtArticle_focusLost(e);   
  }   
}   
class Tickets_txtInteval_focusAdapter extends java.awt.event.FocusAdapter {   
  Tickets adaptee;   
  Tickets_txtInteval_focusAdapter(Tickets adaptee) {   
    this.adaptee = adaptee;   
  }   
  @Override
public void focusLost(FocusEvent e) {   
      adaptee.txtInterval_focusLost(e);   
  }   
}   
   
class Tickets_btnRun_actionAdapter implements java.awt.event.ActionListener {   
  Tickets adaptee;   
   
  Tickets_btnRun_actionAdapter(Tickets adaptee) {   
    this.adaptee = adaptee;   
  }   
  public void actionPerformed(ActionEvent e) {   
    adaptee.btnRun_actionPerformed(e);   
  }   
}   