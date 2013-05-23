package mi.practice.java.base.enumerated;

import static mi.practice.java.base.enumerated.Spiciness.*;
/**
 * 可以使用静态import的方法来对enum进行import,
 * 如果枚举类跟使用在一个文件或者包内,不需要这样引用.
 */
public class Burrito {
	Spiciness degree;
	public Burrito(Spiciness degree){
		this.degree= degree;
	}
	@Override
	public String toString(){
		return "Burrito is "+degree;
	}
	public static void main(String[] args){
		System.out.println(new Burrito(NOT));
		System.out.println(new Burrito(MEDIUM));
		System.out.println(new Burrito(HOT));
	}
}
