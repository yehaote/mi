package mi.practice.java.base.reusing;
/**
 * 演示protected的用法
 * protected让继承类也享有访问权限
 * protected默认情况下还提供了包访问权限
 * 虽然可以创建protected成员, 不过最好的方法还是把所有的成员设置成private
 * 然后提供protected方法对这些成员进行操作
 */
class Villain{
	private String name;
	protected void set(String nm){
		name=nm;
	}
	
	public Villain(String name){
		this.name=name;
	}
	
	public String toString(){
		return "I'm a Villain and my name is "+name;
	}
}

public class Orc extends Villain{
	private int orcNumber;
	public Orc(String name, int orcNumber){
		super(name);
		this.orcNumber=orcNumber;
	}
	
	public void change(String name, int orcNumber){
		set(name);//在继承类中可以调用这个方法
		this.orcNumber=orcNumber;
	}
	
	public String toString(){
		return "Orc "+orcNumber+": "+super.toString();
	}
	
	public static void main(String[] args){
		Orc orc = new Orc("Limburger", 12);
		System.out.println(orc);
		orc.change("Bob", 19);
		System.out.println(orc);
	}
}
