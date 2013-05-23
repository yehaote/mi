package mi.practice.java.base.strings;

import java.util.Random;
/**
 * 介绍StringBuilder的使用
 * StringBuilder包含很多方法, 
 * 如append(), insert(), replace(), substring(), reverse(), delete()
 * @author waf
 */
public class UsingStringBuilder {
	public static Random rand=new Random(47);
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("[");
		for(int i=0;i<25;i++){
			result.append(rand.nextInt(100));
			result.append(", ");
		}
		result.delete(result.length() -2 , result.length());//去掉最后的", "
		result.append("]");
		return result.toString();
	}
	public static void main(String[] args){
		UsingStringBuilder usb = new UsingStringBuilder();
		System.out.println(usb);
	}
}
