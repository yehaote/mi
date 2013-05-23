package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在java中泛型的参数类型是不可知的,
 * java是采用擦写的方式来实现泛型的, 
 * 也就是说只有程序执行的时候才知道参数类型, 
 * 并去判断类型是否相等
 * @author waf
 */
class Frob{}
class Fnorkle{}
class Quark<Q>{}
class Particle<POSTION,MOMENTUM>{}

public class LostInformation {
	public static void main(String[] args){
		List<Frob> list=new ArrayList<Frob>();
		Map<Frob, Fnorkle> map = new HashMap<Frob, Fnorkle>();
		Quark<Fnorkle> quark= new Quark<Fnorkle>();
		Particle<Long, Double> p = new Particle<Long, Double>();
		System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
	}
}
