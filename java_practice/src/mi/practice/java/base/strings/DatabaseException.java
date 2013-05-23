package mi.practice.java.base.strings;
/**
 * String.format()
 * 可以直接调用formatter返回格式化字符串
 * @author waf
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception{
	
	public DatabaseException(int transactionID, int queryID, String message){
		super(String.format("(t%d, q%d) %s", transactionID, queryID, message));
	}
	
	public static void main(String[] args){
		try{
			throw new DatabaseException( 3, 7, "Write failed");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
