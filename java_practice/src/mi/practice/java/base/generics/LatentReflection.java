package mi.practice.java.base.generics;

import java.lang.reflect.Method;
/**
 * 在Java中也可以通过反射来实现Latent typing
 * @author waf
 */
//没有实现Performs
class Mime{
	public void walkAgainstTheWind(){}
	public void sit(){
		System.out.println("Pretending to sit");
	}
	public void pushInvisibleWalls(){}
	@Override
	public String toString(){
		return "Mime";
	}
}
//没有实现Performs
class SmartDog{
	public void speak(){
		System.out.println("Woof!");
	}
	public void sit(){
		System.out.println("Sitting");
	}
	public void reproduce(){}
}

class CommunicateReflectively{
	public static void perform(Object speaker){
		Class<?> spkr = speaker.getClass();
		try{
			try {
				Method speak =spkr.getMethod("speak");
				speak.invoke(speaker);
			} catch (NoSuchMethodException e) {
				System.out.println(speaker+" cannot speak");
			}
			try {
				Method sit =spkr.getMethod("sit");
				sit.invoke(speaker);
			} catch (NoSuchMethodException e) {
				System.out.println(speaker+" cannot sit");
			}
		}catch(Exception e){
			throw new RuntimeException(speaker.toString(), e);
		}
		
	}
}
public class LatentReflection {
	public static void main(String[] args){
		CommunicateReflectively.perform(new SmartDog());
		CommunicateReflectively.perform(new Robot());
		CommunicateReflectively.perform(new Mime());
	}
}
