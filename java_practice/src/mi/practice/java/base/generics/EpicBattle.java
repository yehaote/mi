package mi.practice.java.base.generics;

import java.util.List;

/**
 * 边界结合泛型的更广的使用例子.
 * 注意在边界里的通配符只能对单一的边界, 不能使用&
 * @author waf
 */
interface SuperPower{}
interface XRayVision extends SuperPower{
	void seeThroughWalls();
}
interface SuperHearing extends SuperPower{
	void hearSubtleNoises();
}
interface SuperSmell extends SuperPower{
	void trackBySmell();
}

class SuperHero<Power extends SuperPower>{
	Power power;
	SuperHero(Power power) {
		this.power=power;
	}
	Power getPower(){
		return power;
	}
}
class SuperSleuth<Power extends XRayVision> extends SuperHero<Power>{
	SuperSleuth(Power power) {
		super(power);
	}
	void see(){
		power.seeThroughWalls();
	}
}
class CanineHero<Power extends SuperHearing & SuperSmell>
	extends SuperHero<Power>{
	CanineHero(Power power) {
		super(power);
	}
	void hear(){
		power.hearSubtleNoises();
	}
	void smell(){
		power.trackBySmell();
	}
}
class SuperHearSmell implements SuperHearing, SuperSmell{
	@Override
	public void trackBySmell() {}
	@Override
	public void hearSubtleNoises() {}
}
class DogBoy extends CanineHero<SuperHearSmell>{
	DogBoy() {
		super(new SuperHearSmell());
	}
}

public class EpicBattle {
	// 在泛型方法中使用继承, 因为power是以SuperHearing为边界,
	// 所以可以调用power的hearSubtleNoises.
	static <Power extends SuperHearing>
		void useSuperHearing(SuperHero<Power> hero){
		hero.getPower().hearSubtleNoises();
	}
	static <Power extends SuperHearing &SuperSmell>
		void superFind(SuperHero<Power> hero){
		hero.getPower().hearSubtleNoises();
		hero.getPower().trackBySmell();
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		DogBoy dogBoy = new DogBoy();
		useSuperHearing(dogBoy);
		superFind(dogBoy);
		//还可以这么做
		List<? extends SuperHearing> audioBoys;
		//不过不能这么做
//		List<? extends SuperHearing & SuperSmell> dogBoys;
	}
}
