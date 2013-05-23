package mi.practice.java.base.exceptions;
import static mi.practice.java.base.util.Print.*;
/**
 * 异常链接, 可以把几个异常链接在一起
 * 可以使用构造器进行链接, 
 * 不过只有三个最基本类型类可以用构造其进行链接,
 * Error(系统错误), Exception, RuntimeException
 * 如果是其他的异常类, 可以通过initCause()来进行异常链接
 * 如果你把null值添加到列表中, 则抛出自定义异常
 * 如果客户端编程人员在调用getField(),而不存在这样的Field的时候抛出NoSuchFieldException
 * 但是在类里边调用getField()的时候, 如果也抛出异常说明是程序设计问题, 需要转换成RuntimeException
 */
@SuppressWarnings("serial")
class DynamicFieldsException extends Exception{}
public class DynamicFields {
	private Object[][] fields;

	public DynamicFields(int initialSize) {
		fields = new Object[initialSize][2];
		for (int i = 0; i < initialSize; i++) {
			fields[i] = new Object[] { null, null };
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Object[] object : fields) {
			result.append(object[0]);
			result.append(": ");
			result.append(object[1]);
			result.append("\n");
		}
		return result.toString();
	}

	private int hasField(String id) {
		for (int i = 0; i < fields.length; i++) {
			if (id.equals(fields[i][0])) {
				return i;
			}
		}
		return -1;
	}

	private int getFieldNumber(String id) throws NoSuchFieldException {
		int fieldNum = hasField(id);
		if (fieldNum == -1) {
			throw new NoSuchFieldException();
		}
		return fieldNum;
	}

	private int makeField(String id) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i][0] == null) {
				fields[i][0] = id;
				return i;
			}
		}
		//如果没有空间了, 重新建立数组
		Object[][] tmp = new Object[fields.length + 1][2];
		for (int i = 0; i < fields.length; i++) {
			tmp[i] = fields[i];
		}
		for (int i = fields.length; i < tmp.length; i++) {
			tmp[i] = new Object[] { null, null };
		}
		fields = tmp;
		//递归调用
		return makeField(id);
	}
	public Object getField(String id) throws NoSuchFieldException{
		return fields[getFieldNumber(id)][1];
	}
	public Object setField(String id, Object value) throws DynamicFieldsException{
		if(value == null){
			DynamicFieldsException dfe = new DynamicFieldsException();
			dfe.initCause(new NullPointerException());
			throw dfe;
		}
		int fieldNumber=hasField(id);
		if(fieldNumber == -1){
			fieldNumber=makeField(id);
		}
		Object result = null;
		try{
			result = getField(id);
		}catch(NoSuchFieldException e){
			throw new RuntimeException(e);
		}
		fields[fieldNumber][1]=value;
		return result;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		DynamicFields df = new DynamicFields(3);
		print(df);
		try{
			df.setField("d", "A value for d");
			df.setField("number", 47);
			df.setField("number2", 48);
			print(df);
			df.setField("d", "A new value for d");
			df.setField("number3", 11);
			print("df: "+df);
			print("df.getField(\"d\" : "+df.getField("d"));
			Object field = df.setField("d", null);
		}catch(NoSuchFieldException e){
			e.printStackTrace(System.out);
		}catch(DynamicFieldsException e){
			e.printStackTrace(System.out);
		}
	}
}
