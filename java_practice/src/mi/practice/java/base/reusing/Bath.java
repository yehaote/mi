package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.*;
/**
 * 演示初始化的几种方式
 */
class Soap{
	private String s;
	Soap(){
		print("Soap()");
		s="Constructed";
	}
	public String toString(){
		return s;
	}
}

public class Bath {
	//在定义的时候进行初始化
	private String s1="Happy",
			s2="Happy", s3, s4;
	private Soap castille;
	private int i;
	private float toy;
	//在构造函数里进行初始化
	public Bath(){
		s3="Joy";
		toy =3.14f;
		castille= new Soap();
	}
	//在实例化的时候初始化
	{
		i=47;
	}
	public String toString(){
		if(s4 == null){//懒实例化, 在用的时候进行初始化
			s4="Joy";
		}
		return  "s1 = "+s1+"\n"+
				"s2 = "+s2+"\n"+
				"s3 = "+s3+"\n"+
				"s4 = "+s4+"\n"+
				"i = "+i+"\n"+
				"toy = "+toy+"\n"+
				"castille = "+castille;
	}
	public static void main(String[] args){
		Bath b = new Bath();
		print(b);
	}
}
