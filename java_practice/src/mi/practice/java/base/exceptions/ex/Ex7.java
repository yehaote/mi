package mi.practice.java.base.exceptions.ex;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class Ex7 {
	static Logger logger = Logger.getLogger("exception");
	static void logException(Exception e){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		logger.severe(sw.toString());
	}
	public static void main(String[] args){
		int[] ints = new int[2];
		try{
			System.out.println(ints[0]);
			System.out.println(ints[1]);
			System.out.println(ints[2]);
		}catch(ArrayIndexOutOfBoundsException e){
//			e.printStackTrace(System.out);
			logException(e);
		}
		
	}
}
