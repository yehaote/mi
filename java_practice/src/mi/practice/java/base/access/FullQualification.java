package mi.practice.java.base.access;
/**
 * 全限定的方式实例化类
 */
public class FullQualification {
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args){
		java.util.ArrayList list = new java.util.ArrayList();
	}
}
