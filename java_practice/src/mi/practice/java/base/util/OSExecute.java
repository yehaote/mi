package mi.practice.java.base.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * 通过Java调用系统命令,
 * 可以构建Process对象, 使用ProcessBuilder来进行构建,
 * 参数是命令的String.
 * 通过Process还可以获取程序执行的输入输出流.
 */
public class OSExecute {
	public static void command(String command){
		boolean err = false;
		try{
			Process process = new ProcessBuilder(command.split(" ")).start();
			BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while((s=result.readLine()) != null){
				System.out.println(s);
			}
			BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			// 读取异常
			while((s=errors.readLine()) != null){
				System.err.println(s);
				err=true;
			}
		}catch (Exception e) {
			if(!command.startsWith("CMD /C")){
				command("CMD /C "+command);
			}else{
				throw new RuntimeException(e);
			}
		}
		if(err){
			throw new OSExecuteException("Error executing "+command);
		}
	}
	
	public static void main(String[] args){
	}
}
