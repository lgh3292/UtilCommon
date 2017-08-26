package com.lgh.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;



public class LogUtil {
	
	static Logger log = Logger.getLogger(LogUtil.class); 
	
	public static void println(Object object){
		
		System.out.println(object==null?"null":object.toString());
	}
	public static void log(Object msg,Level info){
//		if(info.equals(Level.WARNING)){
			System.out.println(msg);
//		}
		
		
		log.info(msg);
	}
	
	
	
    /**
     * Log a debug message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void debug(String text) {
    	log(text,Level.INFO);
    }
    
    /**
     * Log a debug message to the log file
     * @param text  the text which is logged to the log file
     * @param t        the exception which will be logged to the file
     */
    public static void debug(String text, Throwable t) {
    	log(text,Level.INFO);
    }
    
    /**
     * Log a info message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void info(Object text)  {
    	log(text,Level.INFO);
    }
    
    /**
     * Log a info message to the log file
     * @param text  the text which is logged to the log file
     * @param t        the exception which will be logged to the file
     */
    public static void info(Object text, Throwable t)  {
    	log(text,Level.INFO);
    }
    
    /**
     * Log a text into the statistics log file
     * @param text the text which is logged to the log file
     */
    public static void logStats(String text)  {
    	log(text,Level.INFO);
    }
    
    /**
     * Log a error (functional) message to the log file
     * @param text  the text which is logged to the log file
     */
    public static void error(String text)  {
    	log(text,Level.INFO);
    }

    /**
     * Log a error (functional) message to the log file
     * @param text  the text which is logged to ITO and the log file
     * @param t        the exception which will be logged to the file
     */
    public static void error(String text, Throwable t)  {
    	log.error(text, t);
    }
    
    /**
     * @return true if the debug level is enabled
     */
    public static boolean isDebugLogEnabled() {
    	return true;
    }
    
    /**
     * Find the method that has called the public static methods of this
     * class. With Java 1.3, we have to print the stack trace and search for
     * the calling method.
     *
     * TODO: With Java 1.4 we can use following approach, which seems to have
     * a better performance:
     * <pre>
     *        Exception e = new Exception();
     *        StackTraceElement[] elems = e.getStackTrace();
     *        ...
     *         String callerMethodName = elems[index].getMethodName();
     * </pre>
     * @return
     */
	private static String getCallingMethod() {
		StringWriter sw = new StringWriter();
		try {
			Exception e = new Exception();
			e.printStackTrace(new PrintWriter(sw));
			String stackTrace = sw.getBuffer().toString();

			// find the first method in the stack trace that doesn't
			// belong to the current class
			int start = -4;
			do {
				start = stackTrace.indexOf("\tat ", start + 4);
				if (!stackTrace.startsWith("com.hp.fraud.service.util.FraudServiceLog.", start + 4)) {
					if (!stackTrace.startsWith("com.hp.es.fraud.service.util.RucLogWrapper.", start + 4)) {
						if (!stackTrace.startsWith("com.hp.ruc.log.", start + 4)) {
							break;
						}
					}
				}
				// RUC log

			} while (start > 0);

			if (start > 0) {
				String method = null;

				// in debug mode display the method in the format "method(line number)"
				if (isDebugLogEnabled()) {
					int end = stackTrace.indexOf(')', start);
					if (end >= 0) {
						method = stackTrace.substring(start + 4, end + 1);
						if (method.endsWith("Unknown Source)")) {
							// then use the standard format below
							method = null;
						}
					}
				}

				// just display "method()"
				if (method == null) {
					int end = stackTrace.indexOf('(', start);
					if (end >= 0) {
						method = stackTrace.substring(start + 4, end) + "()";
					}
				}

				return method;
			}
		} finally {
			try {
			} catch (Exception e) {
			}
		}

		return "unknown";
	}

	
	/*
	 * Print an error without the stack trace
	 */
	public static void errorNoStack(String text, Throwable t) {log(text,Level.INFO);
	}

	/*
	 * Get the error message from an exception
	 */
	private static String getExceptionMessage(Throwable t) {
		if (t.getMessage()!= null) {
			return t.getMessage();
		}
		return t.toString();
		
	}

}
