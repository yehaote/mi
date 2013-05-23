package mi.practice.java.base.exceptions;

@SuppressWarnings("serial")
class VeryImportantException extends Exception {
	@Override
	public String toString() {
		return "A very important exception!";
	}
}

@SuppressWarnings("serial")
class HoHumException extends Exception {
	@Override
	public String toString() {
		return "A trivial exception";
	}
}

public class LostMessage {
	void f() throws VeryImportantException {
		throw new VeryImportantException();
	}

	void dispose() throws HoHumException {
		throw new HoHumException();
	}

	public static void main(String[] args) {
		try {
			LostMessage lm = new LostMessage();
			try {
				lm.f();
			} finally {
				lm.dispose();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
