package mi.practice.java.base.access;
/**
 * 练习8
 * 
 */
class Connection{
	public static int howMany(){
		return count;
	}
	
	public String toString(){
		return ("Connection "+ count);
	}
	
	private static int count = 0;
	private Connection(){};
	static Connection makeConnection(){
		count++;
		return new Connection();
	}
}

@SuppressWarnings("unused")
public class ConnectionManager {
	public ConnectionManager(){
		System.out.println("构造函数");
	}
	
	static int howManyLeft = 3;
	static Connection[] connections = new Connection[3];
	{
		for(Connection x : connections){
			x = Connection.makeConnection();
			System.out.println("初始化连接池");
		}
	}
	
	public static Connection getConnection(){
		if(howManyLeft > 0){
			return connections[--howManyLeft];
		}else{
			System.out.println("No more connections");
			return null;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		ConnectionManager cm = new ConnectionManager();
		System.out.println(cm.howManyLeft);
		cm.getConnection();
		System.out.println(cm.howManyLeft);
		cm.getConnection();
		System.out.println(cm.howManyLeft);
		cm.getConnection();
		System.out.println(cm.getConnection());
		System.out.println(cm.howManyLeft);
	}
}
