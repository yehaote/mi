package mi.practice.java.base.arrays;

import java.util.Arrays;

/**
 * java的数组本身是一个对象, 它被创建后存在于heap上.
 * 对象的数组和基本类型的数组几乎是完全相同的,
 * 唯一的不同是对象数组存放的是引用, 
 * 而基本类型数组存放的是基本类型的值.
 * 
 * 想对于数组, 我们只能直接得到数组的大小,
 * 而没有办法知道里面有多少的可用的数据.
 * @author waf
 */
public class ArraysOptions {
	public static void main(String[] args){
		BerylliumSphere[] a; // 没有初始化
		BerylliumSphere[] b = new BerylliumSphere[5];//默认引用值为null
		System.out.println("b: "+Arrays.toString(b));
		BerylliumSphere[] c = new BerylliumSphere[4];
		for(int i=0;i<c.length;i++){
			if(c[i] == null){
				c[i] = new BerylliumSphere();
			}
		}
		// 只用值的初始化
		BerylliumSphere[] d = {new BerylliumSphere(),
				new BerylliumSphere(),new BerylliumSphere(),new BerylliumSphere()};
		// 声明数组初始化, 并附加值 
		// 最后多个逗号有区别没? 没有
		a = new BerylliumSphere[]{new BerylliumSphere(),new BerylliumSphere(),};
		System.out.println("a.length = "+a.length);
		System.out.println("b.length = "+b.length);
		System.out.println("c.length = "+c.length);
		System.out.println("d.length = "+d.length);
		// 不是指拷贝, 仅仅是引用复制
		a=d;
		System.out.println("a.length = "+a.length);
		
		// 基本类型数组
		int[] e;
		// 基本类型的默认值为0
		int[] f=new int[5];
		System.out.println("f: "+Arrays.toString(f));
		int[] g = new int[4];
		for(int i=0;i<g.length;i++){
			g[i]= i*i;
		}
		int[] h = {11,47,93};
//		System.out.println("e.length = "+e.length); // 没有初始化会编译出错
		System.out.println("f.length = "+f.length);
		System.out.println("g.length = "+g.length);
		System.out.println("h.length = "+h.length);
		// 不是指拷贝, 仅仅是引用复制
		e=h;
		System.out.println("e.length = "+e.length);
		e = new int[]{1,2};
		System.out.println("e.length = "+e.length);
	}
}
