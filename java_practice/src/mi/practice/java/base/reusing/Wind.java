package mi.practice.java.base.reusing;
/**
 * 子类可以转换成父类使用
 * 无须要强制转换
 * 因为所有父类的接口子类都有
 * 我们可以认为子类是父类的一种形式
 * 所以在接收父类的函数里, 可以直接传子类
 * 这种转换叫做向上转化(upcasting)
 * 当在组合和继承中进行选择的时候, 
 * 可以根据是否需要upcasting来做为一个标准
 */
class Instrument{
	public void play(){}
	static void tune(Instrument i){
		i.play();
	}
}

public class Wind extends Instrument{
	public static void main(String[] args){
		Wind flute = new Wind();
		Instrument.tune(flute);
	}
}
