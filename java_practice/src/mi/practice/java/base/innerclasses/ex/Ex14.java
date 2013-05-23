package mi.practice.java.base.innerclasses.ex;

interface Monster{
	void menance();
}
interface DangerousMonster extends Monster{
	void destroy();
}
interface Lethal{
	void kill();
}
interface Vampire extends DangerousMonster, Lethal{
	void drinkBlood();
}
class HorrorShow {
	DangerousMonster getDangerousMonster(){
		return new DangerousMonster() {
			@Override
			public void menance() {
			}
			@Override
			public void destroy() {
			}
		};
	}
	Vampire getVampire(){
		return new Vampire() {
			@Override
			public void kill() {
			}
			@Override
			public void menance() {
			}
			@Override
			public void destroy() {
			}
			@Override
			public void drinkBlood() {
			}
		}; 
	}
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
}
public class Ex14 {
	public static void main(String[] args){
		HorrorShow hs = new HorrorShow();
		DangerousMonster barney = hs.getDangerousMonster();
		HorrorShow.u(barney);
		HorrorShow.v(barney);
		Vampire vlad = hs.getVampire();
		HorrorShow.u(vlad);
		HorrorShow.v(vlad);
		HorrorShow.w(vlad);
	}
}