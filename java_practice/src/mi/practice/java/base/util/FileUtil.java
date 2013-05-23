package mi.practice.java.base.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * 文件一些操作的工具类
 */
public class FileUtil {
	/**
	 * 猜文件的编码
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String guessFileCharset(String fileName) throws Exception {
		BufferedInputStream input = new BufferedInputStream(
				new FileInputStream(fileName));
		try {
			int p = (input.read() << 8) + input.read();
			String code = null;
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}
			return code;
		} finally {
			input.close();
		}
	}
}
