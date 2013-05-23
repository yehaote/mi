package mi.practice.java.base.util;
/**
 * 通过Java调用系统命令
 */
public class OSExecuteDemo {
	public static void main(String[] args){
		OSExecute.command("touch pathTest");
	}
}
