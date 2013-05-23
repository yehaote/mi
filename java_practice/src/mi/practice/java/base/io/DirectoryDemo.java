package mi.practice.java.base.io;

import java.io.File;

import mi.practice.java.base.util.Directory;
import mi.practice.java.base.util.PPrint;

public class DirectoryDemo {
	public static void main(String[] args){
		PPrint.pprint(Directory.walk(".").dirs);
		System.out.println("--------------------------");
		for(File file : Directory.local(".", "T.*")){
			System.out.println(file);
		}
		System.out.println("--------------------------");
		for(File file : Directory.walk(".","T.*\\.java")){
			System.out.println(file);
		}
		System.out.println("--------------------------");
		for(File file : Directory.walk(".", ".*[Zz].*\\.class")){
			System.out.println(file);
		}
	}
}
