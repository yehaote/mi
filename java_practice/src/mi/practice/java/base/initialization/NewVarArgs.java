package mi.practice.java.base.initialization;
/**
 * 可变参数跟数组的最大区别是
 * 1.它可以支持空参数
 * 2.它可以直接单或多参数
 * 3.它也支持数组
 */
public class NewVarArgs {
	static void printArray(Object... args) {
		for (Object obj : args) {
			System.out.print(obj + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		printArray(new Integer(47), new Float(3.14), new Double(11.11));
		printArray(47, 3.14F, 11.11);
		printArray("One", "Two", "Three");
		printArray(new A(), new A(), new A());
		printArray((Object[]) new Integer[] { 1, 2, 3, 4 });
		printArray();
		
//		printArray( new Integer[] { 1, 2, 3, 4 });//这样也可以, 只是会有警告
	}
}
