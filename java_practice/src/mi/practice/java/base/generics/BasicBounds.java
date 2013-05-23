package mi.practice.java.base.generics;

import java.awt.Color;

/**
 * 边界
 * java的泛型提供边界,
 * 应该擦写的关系在没有边界的情况下, 
 * 所有的type都会被擦写到Object,
 * 有了边界以后可以限制范围,
 * 让边界以内的特性还继续保留
 * 
 * 边界里的extends跟类定义里的extends是不一样,
 * 这里只是重用了关键字
 * @author waf
 *
 */
interface HasColor{
	java.awt.Color getColor();
}

class Colored<T extends HasColor>{
	T item;
	Colored(T item){
		this.item=item;
	}
	T getItem(){
		return item;
	}
	java.awt.Color getColor(){
		return item.getColor();
	}
}

class Dimension{
	public int x,y,z;
}
// 不能编译, 必须先是类, 然后才是接口
//class ColoredDimension<T extends HasColor & Dimension>{}
class ColoredDimension<T extends Dimension & HasColor>{
	T item;
	ColoredDimension(T item){
		this.item=item;
	}
	T getItem(){
		return item;
	}
	java.awt.Color color(){
		return item.getColor();
	}
	int getX(){
		return item.x;
	}
	int getY(){
		return item.y;
	}
	int getZ(){
		return item.z;
	}
}

interface Weight{
	int weight();
}
// 像java的类定义一个可以指定一个类和多个接口
class Solid<T extends Dimension & HasColor & Weight>{
	T item;
	Solid(T item){
		this.item=item;
	}
	T getItem(){
		return item;
	}
	java.awt.Color color(){
		return item.getColor();
	}
	int getX(){
		return item.x;
	}
	int getY(){
		return item.y;
	}
	int getZ(){
		return item.z;
	}
	int weight(){
		return item.weight();
	}
}

class Bounded extends Dimension implements HasColor, Weight{
	@Override
	public int weight() {
		return 0;
	}
	@Override
	public Color getColor() {
		return null;
	}
}
public class BasicBounds{
	public static void main(String[] args){
		Solid<Bounded> solid= new Solid<Bounded>(new Bounded());
		solid.color();
		solid.getY();
		solid.weight();
	}
}
