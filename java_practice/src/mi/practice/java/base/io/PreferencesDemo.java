package mi.practice.java.base.io;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
/**
 * Java提供Preferences类来存储小数据,
 * Preferences可以存储基本类型和小于8k的String
 * Preferences就是一个key-value的Map.
 * 它是直接存储在class文件里的?
 * 不是它是储存在系统的资源里面的, 
 * windows的话是在注册表里.
 */
public class PreferencesDemo {
	public static void main(String[] args) throws BackingStoreException {
		Preferences prefs = Preferences
				.userNodeForPackage(PreferencesDemo.class);
		prefs.put("Location", "Oz");
		prefs.put("Footwear", "Ruby Slippers");
		prefs.putInt("Companions", 4);
		prefs.putBoolean("Are there witches?", true);
		int usageCount = prefs.getInt("UsageCount", 0);
		usageCount++;
		prefs.putInt("UsageCount", usageCount);
		for (String key : prefs.keys()) {
			System.out.println(key + ": " + prefs.get(key, null));
		}
		// 必须提供一个默认值
		System.out.println("How mang companions does Dorothy have? "
				+ prefs.getInt("Companions", 0));
	}
}
