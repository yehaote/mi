package mi.practice.java.base.typeinfo;
/**
 * 通过Class进行类型转换多做了一些判断, 
 * 其实是一样的.
 * 还有一点就是使用Class可以动态转换, 
 * 不能写明类型增加通用性, 
 * 主要是在泛型编程的时候用处会多一点
 * @author waf
 */
class Building{}
class House extends Building{}

public class ClassCasts {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Building b = new House();
		Class<House> houseType = House.class;
		House h = houseType.cast(b);
		h=(House)b;
	}
}
