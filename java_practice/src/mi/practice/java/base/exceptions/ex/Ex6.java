package mi.practice.java.base.exceptions.ex;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@SuppressWarnings("serial")
class Ex6Exception1 extends Exception{
	static Logger logger = Logger.getLogger("exception1");
	public Ex6Exception1(){
		StringWriter sw = new StringWriter();
		printStackTrace(new PrintWriter(sw));
		logger.severe(sw.toString());
	}
}

@SuppressWarnings("serial")
class Ex6Exception2 extends Exception{
	static Logger logger = Logger.getLogger("exception2");
	public Ex6Exception2(){
		StringWriter sw = new StringWriter();
		printStackTrace(new PrintWriter(sw));
		logger.severe(sw.toString());
	}
}
public class Ex6 {
	public static void main(String[] args){
		try{
			throw new Ex6Exception1();
		}catch(Ex6Exception1 e){
		}
		try{
			throw new Ex6Exception2();
		}catch(Ex6Exception2 e){
		}
	}
}
