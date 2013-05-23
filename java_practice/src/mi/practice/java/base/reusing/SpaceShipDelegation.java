package mi.practice.java.base.reusing;
/**
 * 所谓的委托就是指
 * 像组合一样在当前类中包含其他的类, 可是又向继承一样
 * 对外暴露气包含类的所有接口
 * 一般用于对象设计中需要继承的效果, 可是跟父类有没有is-a的关系的情况
 */
public class SpaceShipDelegation {
	@SuppressWarnings("unused")
	private String name;
	private SpaceShipControls controls = new SpaceShipControls();

	public SpaceShipDelegation(String name) {
		this.name = name;
	}

	// 委托方法
	public void back(int velocity) {
		controls.back(velocity);
	}

	public void down(int velocity) {
		controls.down(velocity);
	}

	public void forward(int velocity) {
		controls.forward(velocity);
	}

	public void left(int velocity) {
		controls.left(velocity);
	}

	public void right(int velocity) {
		controls.right(velocity);
	}

	public void truboBoos(int velocity) {
		controls.turboBoost(velocity);
	}

	public void up(int velocity) {
		controls.up(velocity);
	}

	public static void main(String[] args) {
		SpaceShipDelegation delegation = new SpaceShipDelegation(
				"NSEA Protector");
		delegation.forward(100);
	}
}
