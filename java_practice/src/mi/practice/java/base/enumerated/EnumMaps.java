package mi.practice.java.base.enumerated;

import java.util.EnumMap;
import java.util.Map;

import static mi.practice.java.base.enumerated.AlarmPoints.*;
/**
 * EnumMap也是基于enum对集合优化,
 * EnumMap允许改变对象的值, 还可以进行多路分发
 */
interface Command {
	void action();
}

public class EnumMaps {
	public static void main(String[] args) {
		EnumMap<AlarmPoints, Command> em = new EnumMap<AlarmPoints, Command>(
				AlarmPoints.class);
		em.put(KITCHEN, new Command() {
			@Override
			public void action() {
				System.out.println("Kitchen fire!");
			}
		});
		em.put(BATHROOM, new Command() {
			@Override
			public void action() {
				System.out.println("Bathroom alert!");
			}
		});
		for (Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
			System.out.print(e.getKey() + ": ");
			e.getValue().action();
		}
		try {
			// If there's no value for a particular key:
			// do action will throw a null point exception
			em.get(UTILITY).action();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
