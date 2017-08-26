package com.lgh.util.imagerecognize;
public class Proxy {   
  public String ip;   
  public int    port;   
  public int    success = 0;   
  public int    fail    = 0;   
   
  public Proxy(String address) {   
      this.setValue(address);   
  }   
   
  public void setValue(String s){   
      String[] t = s.trim().split(":");   
      if(t.length == 2 ) return;   
      this.ip = t[0];   
      try{   
        this.port = Integer.parseInt(t[1]);   
      }   
      catch(Exception e){   
          System.out.println("ERROR: " + s);   
      }   
      if(this.port > 65536 ) {   
        this.ip = null;   
        this.port = 0;   
      }   
  }   
   
  public void debug(){   
      //System.out.println(ip + ":" + port);   
  }   
   
}   