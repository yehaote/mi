package mi.practice.java.base.initialization;
/**
 * 数组的引用赋值
 */
public class ArraysOfPrimitives {
	public static void main(String[] args){
		int a1[]={1,2,3,4,5};
		int a2[];
		a2=a1;
		for(int i=0;i<a2.length;i++){
			a2[i]=a2[i]+1;
//			a2[i]++; //这里也可用使用自增的形式来写
		}
		for(int i=0;i<a1.length;i++){
			System.out.println("a1["+i+"] = "+a1[i]);
		}
	}
}
