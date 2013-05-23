package mi.practice.java.base.exceptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputFile {
	
	private BufferedReader in;
	
	public InputFile(String fname)throws Exception{
		try{
			in = new BufferedReader(new FileReader(fname));
		}catch (FileNotFoundException e){
			//找不到文件, 所以不需要关闭
			System.out.println("Could not open "+fname);
			throw e;
		}catch(Exception e){
			//其他所有的异常都需要关闭
			try{
				in.close();
			}catch(IOException e2){
				System.out.println("in.close() unsucessful");
			}
			throw e; //再抛出异常
		}finally{
			//不要在这里关闭
			//在这里关闭了下面就不能调用了
		}
	}
	
	public String getLine(){
		String s;
		try{
			s=in.readLine();
		}catch(IOException e){
			throw new RuntimeException("readLine() failed");
		}
		return s;
	}
	
	public void dispose(){
		try{
			in.close();
			System.out.println("dispose() successful");
		}catch (IOException e) {
			throw new RuntimeException("in.close() failed");
		}
	}
}
