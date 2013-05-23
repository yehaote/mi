package mi.practice.java.base.exceptions;
import static mi.practice.java.base.util.Print.*;

import java.io.FileNotFoundException;
import java.io.IOException;

class WrapCheckedException{
	void throwRuntimeException(int type){
		try{
			switch(type){
			case 0: throw new FileNotFoundException();
			case 1: throw new IOException();
			case 2: throw new RuntimeException("Where am I ?");
			default: return;
			}
		}catch(Exception e){
			//转换成uncheck exception
			throw new RuntimeException(e);
		}
	}
}

@SuppressWarnings("serial")
class SomeOtherException extends Exception{}

public class TurnOffChecking {
	public static void main(String[] args){
		WrapCheckedException wce = new WrapCheckedException();
		//可以直接调用这个方法而不需要try-catch
		wce.throwRuntimeException(3);
		//也可以捕获处理
		for(int i=0;i<4;i++){
			try{
				if(i < 3){
					wce.throwRuntimeException(i);
				}else{
					throw new SomeOtherException();
				}
			}catch(SomeOtherException e){
				print("SomeOtherException: "+e);
			}catch(RuntimeException re){
				try{
					throw re.getCause();//这里是重点, 类似于把unchecked转换为checked
				}catch (FileNotFoundException e) {
					print("FileNotFoundException: "+e);
				}catch (IOException e) {
					print("IOException: "+e);
				}catch (Throwable t) {
					print("Throwable: "+t);
				}
			}
		}
		
	}
}
