package mi.practice.java.base.interfaces;
/**
 * 接口可以继承其他的接口, 使用extends关键字
 * 还能同时继承多个接口
 */
interface Monster{
	void menance();
}
interface DangerousMonster extends Monster{
	void destroy();
}
interface Lethal{
	void kill();
}
class DragonZilla implements DangerousMonster{
	@Override
	public void menance(){}
	@Override
	public void destroy(){}
}
interface Vampire extends DangerousMonster, Lethal{
	void drinkBlood();
}
class VeryBadVampire implements Vampire{
	@Override
	public void menance(){}
	@Override
	public void destroy(){}
	@Override
	public void kill(){}
	@Override
	public void drinkBlood(){}
}
public class HorrorShow {
	static void u(Monster b){
		b.menance();
	}
	static void v(DangerousMonster d){
		d.menance();
		d.destroy();
	}
	static void w(Lethal l){
		l.kill();
	}
	public static void main(String[] args){
		DangerousMonster barney = new DragonZilla();
		u(barney);
		v(barney);
		Vampire vlad = new VeryBadVampire();
		u(vlad);
		v(vlad);
		w(vlad);
	}
}
