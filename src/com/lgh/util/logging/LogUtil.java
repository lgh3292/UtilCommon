package com.lgh.util.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;


public class LogUtil {
	
	static Logger log = Logger.getLogger(LogUtil.class); 
	/**FATAL<ERROR<WARN<INFO<DEBUG */
	public static void log(Object message,LogLevel info){
		if(info == LogLevel.DEBUG){
			log.debug(message);
		}else if(info == LogLevel.INFO){
			log.info(message);
		}else if(info == LogLevel.WARN){
			log.warn(message);
		}else if(info == LogLevel.ERROR){
			log.error(message);
		}else if(info == LogLevel.FATAL){
			log.fatal(message);
		}
	}
	
	public static void log(Object message,Throwable t,LogLevel info){
		if(info == LogLevel.DEBUG){
			log.debug(message,t);
		}else if(info == LogLevel.INFO){
			log.info(message,t);
		}else if(info == LogLevel.WARN){
			log.warn(message,t);
		}else if(info == LogLevel.ERROR){
			log.error(message,t);
		}else if(info == LogLevel.FATAL){
			log.fatal(message,t);
		}
	}
	
	public static void log(Object text) {
    	log(text,LogLevel.INFO);
    }
	
	
    /**
     * Log a debug message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void debug(String text) {
    	log(text,LogLevel.DEBUG);
    }
    
    /**
     * Log a debug message to the log file
     * @param text  the text which is logged to the log file
     * @param t        the exception which will be logged to the file
     */
    public static void debug(String text, Throwable t) {
    	log(text,t,LogLevel.DEBUG);
    }
    
    /**
     * Log a info message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void info(Object text)  {
    	log(text,LogLevel.INFO);
    }
    
    /**
     * Log a info message to the log file
     * @param text  the text which is logged to the log file
     * @param t        the exception which will be logged to the file
     */
    public static void info(Object text, Throwable t)  {
    	log(text,t,LogLevel.INFO);
    }
    

    /**
     * Log a error (functional) message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void error(String text)  {
    	log(text,LogLevel.ERROR);
    }

    /**
     * Log a error (functional) message to the log file
     * @param text  the text which is logged to ITO and the log file
     * @param t        the exception which will be logged to the file
     */
    public static void error(String text, Throwable t)  {
    	log(text, t,LogLevel.ERROR);
    }
    
    /**
     * @return true if the debug level is enabled
     */
    public static boolean isDebugLogEnabled() {
    	return true;
    }
    
    public static void main(String[] args) {
    	error("test");
    	System.out.print("tes");
    }

}
