package com.lgh.util.imagerecognize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class IPConfig {   
   
  public static Vector ipconfig =   new Vector(100);;   
  //public static Vector  ipSuccess =  new Vector(100);;   
  //public static Vector  ipFail    =  new Vector(100);;   
   
  static int totalCnt   = 0;   
  static int successCnt = 0;   
  static int failCnt    = 0;   
   
  static int current = 0;   
   
  public IPConfig() {   
   
  }   
   
  public IPConfig(File file){   
       this.load(file);   
  }   
   
  public void load(File file) {   
     if( ipconfig == null ){   
         ipconfig = new Vector(100);   
     }   
     else{   
         ipconfig.clear();   
     }   
   
     try {   
          BufferedReader br = new BufferedReader(new FileReader(file));   
          String line = br.readLine();   
          while( line != null ){   
              if( !line.trim().equals("") ){   
                  Proxy p = new Proxy(line);   
                  if( p.port > 0 )   
                      add( p );   
              }   
              line = br.readLine();   
          }   
      }   
      catch (Exception ex) {   
          ex.printStackTrace();   
      }   
  }   
   
  public static void load(String s){   
       String[] proxys = s.split("\n");   
       if(proxys != null && proxys.length > 0 ){   
          for(int i=0; i<proxys.length; i++){   
             Proxy p = new Proxy(proxys[i]);   
               if( p.port > 0 ) add( p );   
          }   
       }   
       debug();   
  }   
   
  public static synchronized Proxy next(){   
      Proxy curProxy = new Proxy("vote.client.sina.com.cn:80");   
      if( ipconfig != null && ipconfig.size() > 0 ){   
          if(current ==ipconfig.size()-1 ){   
              current++;   
          }   
          else{   
              current = 0;   
          }   
          curProxy =(Proxy) ipconfig.get(current);   
      }   
      return curProxy;   
  }   
   
  public static void add(Proxy p){   
        ipconfig.add( p );   
  }   
   
  public static void debug(){   
      for(int i=0; i<ipconfig.size(); i++ ){   
          ((Proxy)ipconfig.get(i)).debug();   
      }   
  }   
   
}   