package mi.practice.java.base.enumerated;
/**
 * 因为其实enum也是一个类, 
 * 所以也可以在里面添加方法.
 * 在定义枚举的时候其实是调用构造函数,
 * 下面演示提供一个带参数的构造函数, 
 * 然后在初始化的时候给出String.
 * 如果要定义其他的方法的时候必须在枚举序列后面加上分号.
 */
public enum OzWitch {
	WEST("Miss Gulch, aka the Wicked Witch of the West"), NORTH(
			"Glinda, the Good Witch of the North"), EAST(
			"Wiched Witch of the East, wearer of the Ruby "
					+ "Slippers, crushed by Dorothy's house"), SOUTH(
			"Good by inference, but missing");
	private String description;

	// 构造函数必须为包访问权限或者private
	private OzWitch(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static void main(String[] args) {
		for (OzWitch witch : OzWitch.values()) {
			System.out.println(witch + ": " + witch.getDescription());
		}
	}

}
