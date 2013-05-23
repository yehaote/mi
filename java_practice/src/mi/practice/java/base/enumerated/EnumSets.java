package mi.practice.java.base.enumerated;

import java.util.EnumSet;
import static mi.practice.java.base.enumerated.AlarmPoints.*;

/**
 * EnumSet添加到SE5, 主要用于替代传统的int-base bit flags.
 * 因为这样更加清晰, 标识位不够直意.
 * EnumSet也是为了速度设计的, 它比HashSet要快很多.
 * EnumSet的元素必须是enum.
 */
public class EnumSets {
	public static void main(String[] args) {
		// 初始化一个空的EnumSet
		EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);
		// 添加一个枚举
		points.add(BATHROOM);
		System.out.println(points);
		points.addAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
		System.out.println(points);
		// 生成一个包含所有类型的EnumSet
		points = EnumSet.allOf(AlarmPoints.class);
		System.out.println(points);
		points.removeAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
		System.out.println(points);
		// 指定一个枚举类型的范围
		points.removeAll(EnumSet.range(OFFICE1, OFFICE4));
		System.out.println(points);
		// 类似取补集
		points = EnumSet.complementOf(points);
		System.out.println(points);
	}
}
