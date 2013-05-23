package mi.practice.java.base.enumerated;
/**
 * 在枚举里面可以覆盖默认的方法
 */
public enum SpaceShip {
	SCOUT, CARGO, TRANSPORT, CRUISER, BATTLESHIP, MOTHERSHIP;
	@Override
	public String toString() {
		// 第一个字母保持不变, 剩下的转为缩写
		String id = name();
		String lower = id.substring(1).toLowerCase();
		return id.charAt(0) + lower;
	}

	public static void main(String[] args) {
		for (SpaceShip s : values()) {
			System.out.println(s);
		}
	}
}
