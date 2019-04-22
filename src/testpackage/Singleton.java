package testpackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Singleton {  
      	public static void main(String[] args) {
      		Singleton.getInstance();
		}
    private static Singleton instance = new Singleton();  
      
    private Timer asynChecker = new Timer(true);  
      
    private Map<Integer, String> tmpValueMap = new HashMap<Integer, String>();  
      
    private Singleton() {  
        asynChecker.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                tmpValueMap.clear();  
            }  
        }, 5000, 5000);  
    }  
      
    public static Singleton getInstance() {  
        return instance;  
    }  
}  