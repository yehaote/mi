package mi.practice.java.base.annotations.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * 根据标注的类产生SQL的建表语句.
 * 先检查类是否包含@DBTable标注, 
 * 然后再根据Field的标注.
 */
public class TableCreator {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("arguments: annotated classes");
			System.exit(0);
		}
		for (String className : args) {
			Class<?> cl = Class.forName(className);
			DBTable dbTable = cl.getAnnotation(DBTable.class);
			if (dbTable == null) {
				System.out.println("No DBTable annotations in class "
						+ className);
				continue;
			}
			String tableName = dbTable.name();
			// If the name is empty, use the class name:
			if (tableName.length() < 1) {
				tableName = cl.getName().toUpperCase();
			}
			List<String> columnDefs = new ArrayList<String>();
			for (Field field : cl.getDeclaredFields()) {
				String columName = null;
				Annotation[] anns = field.getDeclaredAnnotations();
				if (anns.length < 1) {
					continue;
				}
				if (anns[0] instanceof SQLInteger) {
					SQLInteger sInt = (SQLInteger) anns[0];
					// Use field name if name not specified
					if (sInt.name().length() < 1) {
						columName = field.getName().toUpperCase();
					} else {
						columName = sInt.name();
					}
					columnDefs.add(columName + " INT"
							+ getConstraints(sInt.constraints()));
				}
				if (anns[0] instanceof SQLString) {
					SQLString sString = (SQLString) anns[0];
					// Use field name if name not specified
					if (sString.name().length() < 1) {
						columName = field.getName().toUpperCase();
					} else {
						columName = sString.name();
					}
					columnDefs.add(columName + " VARCHAR(" + sString.value()
							+ ")" + getConstraints(sString.constraints()));
				}
				// 这里打印是为了观察整个过程
				StringBuilder createCommand = new StringBuilder("CREATE TABLE "
						+ tableName + "(");
				for (String columnDef : columnDefs) {
					createCommand.append("\n    " + columnDef + ",");
				}
				// Remove trailing comma
				String tableCreate = createCommand.substring(0,
						createCommand.length() - 1)
						+ ");";
				System.out.println("Table Creation SQL for " + className
						+ " is :\n" + tableCreate);
			}
		}
	}

	private static String getConstraints(Constraints con) {
		String constraints = "";
		if (!con.allowNull()) {
			constraints += " NOT NULL";
		}
		if (con.primayKey()) {
			constraints += " PRIMARY KEY";
		}
		if (con.unique()) {
			constraints += " UNIQUE";
		}
		return constraints;
	}
}
