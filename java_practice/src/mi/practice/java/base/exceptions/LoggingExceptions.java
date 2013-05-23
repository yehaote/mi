package mi.practice.java.base.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
/**
 * logger.server()返回severe级日志
 * Logger.getLogger()根据String返回日志对象
 * 
 */
@SuppressWarnings("serial")
class LoggingException extends Exception{
	private static Logger logger = Logger.getLogger("LoggingException");
	public LoggingException(){
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
}

public class LoggingExceptions {
	public static void main(String[] args){
		try{
			throw new LoggingException();
		}catch(LoggingException e){
			System.err.println("Caught "+e);
		}
		try{
			throw new LoggingException();
		}catch(LoggingException e){
			System.err.println("Caught "+e);
		}
	}
}
