package com.lgh.util.imagerecognize;
   
import javax.swing.JButton;   
import javax.swing.JTextField;   
import javax.swing.JLabel;   
import javax.swing.ImageIcon;   
import java.net.*;   
import java.io.*;   
import java.util.Enumeration;   
import java.util.Hashtable;   
   
public class Vote extends Thread {   
   
    JButton    imageCode;   
    JTextField txtCode;   
    JButton    btnVote;   
    JLabel     message;   
   
    int   status = 1;  //0 - wait 1-getCode 2-voteing   
   
    private  byte[] headerCode;   
   
    private  byte[] headerVote;   
    private  byte[] bodyVote;   
   
    private  byte[] responseHeader;   
    private  byte[] responseBody;   
   
    private String currentCookie = null;   
   
    //投票的作品ID   
    public static String articleId = "44";   
   
    static long startTime = 0;   
    static long runTime   = 0;   
   
    static long voteInterval = 1250;   
   
    Proxy proxy = null;   
   
    // log file Name   
    String fileName ="vote.log";   
   
    Hashtable cookies = new Hashtable();   
   
    String valideCode ="0000";   
   
    static int successCnt  = 0;   
    static int repeatCnt   = 0;   
    static int failCnt     = 0;   
   
    public Vote(JButton imageCode,   
                JTextField txtCode,   
                JButton    btnVote,   
                JLabel     message) {   
   
      this.imageCode = imageCode;   
      this.txtCode   = txtCode;   
      this.btnVote   = btnVote;   
      this.message   = message;   
   
      articleId = Tickets.txtArticle.getText();   
   
      voteInterval = Long.parseLong(Tickets.txtInterval.getText());   
   
      if(startTime == 0 ){   
          startTime = System.currentTimeMillis();   
      }   
    }   
   
    @Override
	public void run(){   
        while(true){   
          if(status == 1 ){   
              this.proxy = IPConfig.next();   
              this.getCode();   
              this.status = 0;   
          }   
          else if( status == 0  ){   
            //wait   
            try {   
                yield();   
                Thread.sleep(voteInterval);   
            }   
            catch (InterruptedException ex) {   
   
            }               
          }   
          else if(status == 2){   
             this.vote(valideCode);   
             this.status = 1;   
          }   
        }   
    }   
   
    public void reGetCode(){   
         if(this.status == 0 ) this.status = 1;   
    }   
   
    public void voteTicket(){   
        if( txtCode.getText().trim().length() > 2 && this.status == 0 ){   
           this.status = 2;   
           this.valideCode = txtCode.getText().trim();   
        }   
    }   
   
    private void bCode(){   
        StringBuffer sb = new StringBuffer();   
        sb.append("GET http://vote.client.sina.com.cn/imgserial.php?r=" + Math.random() + " HTTP/1.1\r\n");   
        sb.append("Accept: */*\r\n");   
        sb.append("Accept-Language: zh-CN\r\n");   
        sb.append("Referer: http://vote.client.sina.com.cn/vote.php?pid=41&tid=55&ids=" + Vote.articleId + "\r\n");   
        sb.append("x-flash-version: 9,0,124,0\r\n");   
        sb.append("Accept-Encoding: gzip, deflate\r\n");   
        sb.append("User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1))\r\n");   
        sb.append("Host: vote.client.sina.com.cn\r\n");   
        sb.append("Connection: Keep-Alive\r\n");   
        sb.append("\r\n");   
        headerCode = sb.toString().getBytes();   
        //System.out.println(new String(headerCode));   
    }   
   
    private void bVote(String code){   
       bodyVote = ("pid=41&tid=55&ids=" + Vote.articleId + "&op=vote&style=blue&cknum=" + code).getBytes();   
       StringBuffer sb = new StringBuffer();   
       sb.append("POST http://vote.client.sina.com.cn/vote.php HTTP/1.1\r\n");   
       sb.append("Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*\r\n");   
       sb.append("Accept-Language: zh-CN\r\n");   
       sb.append("Referer: http://vote.client.sina.com.cn/vote.php?pid=41&tid=55&ids=" + Vote.articleId + "\r\n");   
       sb.append("Content-Type: application/x-www-form-urlencoded\r\n");   
       sb.append("Accept-Encoding: gzip, deflate\r\n");   
       sb.append("User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1))\r\n");   
       sb.append("Host: vote.client.sina.com.cn\r\n");   
       sb.append("Connection: Keep-Alive\r\n");   
       sb.append("Content-Length: "+bodyVote.length + "\r\n");   
       sb.append("Cache-Control: no-cache\r\n");   
       if(this.currentCookie != null){   
          sb.append(currentCookie + "\r\n");   
       }   
   
       sb.append("\r\n");   
       this.headerVote = sb.toString().getBytes();   
       //System.out.println(new String(headerVote));   
    }   
   
    private void getCode(){   
        Socket socket = null;   
        OutputStream os = null;   
        InputStream is = null;   
        //btnVote.setEnabled(false);   
        imageCode.setIcon(null);   
        this.bCode();   
        try {   
           socket = new Socket(proxy.ip, proxy.port);   
           socket.setSoTimeout(20000);   
           os = socket.getOutputStream();   
           os.write(headerCode );   
           //System.out.println(new String(headerCode));   
           os.flush();   
           is = socket.getInputStream();   
           byte data[] = new byte[10240];   
           int rc = is.read(data);   
           this.splitHeader(data,rc);   
           this.getCookie();   
           int responseCode = getResponseCode();   
           //System.out.println(new String(data,0,rc));   
           if ( responseCode == 200 ) {   
             if( this.isImage() ){   
               byte picDate[] = getPicture();   
               if (picDate != null) {   
                 imageCode.setIcon(new ImageIcon(picDate));   
                 //btnVote.setEnabled(true);   
                 //txtCode.setEnabled(true);   
                 //txtCode.setText("");   
                 //message.setText("");   
                 this.valideCode = Model.match(picDate);   
                 txtCode.setText(this.valideCode);   
               }   
             }   
          }   
       }   
       catch (Exception e) {   
           //System.out.println("Code Error " +failCodeCnt + " " + this.proxy.ip );   
       }   
       finally{   
         if(os != null)   
         try {   
             os.close();   
         }   
         catch (IOException ex1) {   
         }   
         os = null;   
         if(is != null )   
         try {   
             is.close();   
         }   
         catch (IOException ex2) {   
         }   
         is = null;   
   
         if(socket != null)   
         try {   
               socket.close();   
         }   
         catch (IOException ex3) {   
         }   
         socket = null;   
       }   
   }   
   
   private void vote(String code){   
        Socket socket = null;   
        OutputStream os = null;   
        InputStream is = null;   
   
        //btnVote.setEnabled(false);   
        //txtCode.setEnabled(false);   
        message.setText("开始投票");   
        this.bVote(code);   
        //System.out.println(new String(headerVote));   
        //System.out.println(new String(bodyVote));   
        try {   
            socket = new Socket(proxy.ip, proxy.port);   
            socket.setSoTimeout(20000);   
            os = socket.getOutputStream();   
            os.write(this.headerVote);   
            os.write(this.bodyVote);   
            os.flush();   
            is = socket.getInputStream();   
            byte data[] = new byte[10240];   
            int rc = is.read(data);   
            //System.out.println(new String(data,0,rc));   
            this.splitHeader(data,rc);   
   
            int responseCode = this.getResponseCode();   
            int voteResult = getResult();   
            //System.out.println(this.valideCode +"="+this.currentCookie + ":"+voteResult);   
            if( responseCode != 200 ){   
               failCnt++;   
               this.message.setText("失败");   
               //this.nextProxy();   
            }   
            else if( voteResult == 0 ){   
               successCnt++;   
               this.message.setText("成功");   
               //this.nextProxy();   
            }   
            else if(voteResult == 2){   
               repeatCnt++;   
               this.message.setText("重复");   
               //this.nextProxy();   
            }   
            else if(voteResult == 1){   
               failCnt++;   
               this.message.setText("验证码错误");   
            }   
            else if( voteResult ==-1 ){   
              failCnt++;   
              this.message.setText("失败");   
              //this.nextProxy();   
            }   
            Tickets.successCnt.setText("成功:" + successCnt+" 重复:" + repeatCnt + "/失败" + failCnt);   
   
            this.status = 1;   
            runTime = (System.currentTimeMillis() - startTime)/ 1000;   
            Tickets.failCnt.setText("每分钟成功:" + successCnt*60/runTime + " 总共花时=" + runTime /60/60 +"小时:"+ (runTime/60 % 60)+ "分钟" );   
   
        }   
        catch (Exception e) {   
        }   
        finally{   
          if(os != null)   
          try {   
              os.close();   
          }   
          catch (IOException ex1) {   
          }   
         os = null;   
         if(is != null )   
         try {   
             is.close();   
         }   
         catch (IOException ex2) {   
         }   
         is = null;   
   
         if(socket != null)   
         try {   
               socket.close();   
         }   
         catch (IOException ex3) {   
         }   
         socket = null;   
        }   
   }   
   
   /**  
    * 把HTTP响应分解为响应头和BODY部分，并保存到相应的字节数组中  
    * @param s byte[]  
    * @param len int  
    */   
   private void splitHeader(byte s[],int len){   
        int cnt = 0;   
        int i=0;   
        if( len == 0 ) return;   
        for( ; i<len-1; i++){   
            if(s[i] == 13 && s[i+1] == 10 ){   
                cnt++;   
                i++;   
                if(cnt == 2 ) break;   
                continue;   
            }   
            cnt = 0;   
        }   
        if(cnt == 2 && i > 0 ){   
            responseHeader = new byte[i];   
            System.arraycopy(s,0,responseHeader,0,i);   
            this.responseBody = new byte[len - i-1];   
            System.arraycopy(s,i+1,this.responseBody,0,len - i-1);   
        }   
    }   
    /**  
     * 获取头字段的属性值  
     * @param key String  
     * @return String  
     */   
    private String getHeader(String key){   
      String result = "";   
      if(key == null ) return null;   
   
      BufferedReader br = new BufferedReader(   
                             new InputStreamReader(   
                                new ByteArrayInputStream(this.responseHeader)));   
      try {   
        String line = br.readLine();   
        while(line != null && !line.trim().equals("") ){   
              if(line.toLowerCase().indexOf(key.toLowerCase()) != -1){   
                 String[] s = line.split(":");   
                 if(s != null && s.length > 1 ){   
                   result += s[1].trim() + ";";   
                 }   
              }   
              line = br.readLine();   
        }   
      }   
      catch (IOException ex) {   
      }   
      return result;   
   }   
   
   /**  
    * 获取响应代码  
    * @param data byte[]  
    * @return int  
    */   
   private int getResponseCode(){   
        int code = 0;   
        int index = 0;   
        String res = null;   
        if( this.responseHeader != null && this.responseHeader.length > 0 ){   
          for( ; index<this.responseHeader.length -1; index++){   
             if(this.responseHeader[index] == 13 && this.responseHeader[index+1] == 10 ){   
                res = new String(this.responseHeader,0,index);   
             }   
          }   
        }   
   
        if(res != null ){   
            String[] s = res.split(" ");   
            code = Integer.parseInt(s[1]);   
        }   
        return code;   
    }   
   
    private void getCookie(){   
        this.currentCookie = null;   
        this.cookies.clear();   
        String cookie = this.getHeader("Set-Cookie");   
        if( cookie == null || cookie.equals("")) return;   
        String[] s = cookie.split(";");   
        if( s == null) return;   
        for(int i=0; i<s.length;i++ ){   
            String[] str = s[i].split("=");   
            if(str != null && str.length > 1 ){   
               if( str[0].trim().equalsIgnoreCase("path") ||   
                   str[0].trim().equalsIgnoreCase("domain") ) continue;   
               this.cookies.put(str[0],str[1]);   
            }   
        }   
   
        Enumeration  keys = cookies.keys();   
        String cookieName = null;   
        if( keys.hasMoreElements()){   
            cookieName = (String)keys.nextElement();   
            currentCookie = "Cookie: "+cookieName + "=" + (String)cookies.get(cookieName);   
        }   
        while( keys.hasMoreElements()){   
            cookieName = (String)keys.nextElement();   
            currentCookie = currentCookie + "; " + cookieName + "=" + (String)cookies.get(cookieName);   
        }   
        //System.out.println(this.currentCookie);   
    }   
   
   private byte[] getPicture(){   
      if( this.isImage() ){   
          return this.responseBody;   
      }   
      else return null;   
   }   
   
   private int getResult(){   
        int result = -1;   
        BufferedReader br = new BufferedReader(   
                               new InputStreamReader(   
                                 new ByteArrayInputStream(this.responseBody)));   
   
        try {   
           String line = br.readLine();   
           while(line != null ){   
               if(line.indexOf("错误") != -1){   
                    result = 1;   
                    break;   
               }   
               else if(line.indexOf("感谢您的参与") != -1){   
                  result = 0;   
                  break;   
               }   
              else if(line.indexOf("重复")!= -1){   
                  result = 2;   
                  break;   
              }   
              line = br.readLine();   
          }   
          //System.out.println(line);   
        }   
        catch (IOException e) {   
        }   
        return result;   
    }   
   
    /**  
     * 根据HTTP返回类型判断是否为图片数据  
     * @param s byte[]  
     * @param len int  
     * @return boolean  
     */   
    public boolean isImage(){   
       boolean result = false;   
       String contentType = this.getHeader("content-type");   
       if(contentType != null &&   
          contentType.toLowerCase().indexOf("image") != -1 ){   
          result = true;   
       }   
       return result;   
    }   
   
    private synchronized void logger(byte[] s,int len){   
        FileOutputStream logfile;   
        try {   
           logfile = new FileOutputStream(fileName, true);   
           logfile.write(s, 0, len);   
           logfile.flush();   
           logfile.close();   
        }   
        catch (Exception ex) {   
   
        }   
    }   
}   