package mi.practice.java.base.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;
/**
 * Java中的File即是文件又是文件夹,
 * 如果是文件夹使用list显示文件夹下的文件,
 * 还支持带FilenameFilter来显示的方法.
 */
public class DirList {
	public static void main(String[] args){
		File path = new File(".");
		String[] list;
		if(args.length == 0){
			list=path.list();
		}else{
			list=path.list(new DirFilter(args[0]));
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for(String dirItem : list){
			System.out.println(dirItem);
		}
	}
}

class DirFilter implements FilenameFilter{
	private Pattern pattern;
	public DirFilter(String regex){
		pattern=Pattern.compile(regex);
	}
	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
}
