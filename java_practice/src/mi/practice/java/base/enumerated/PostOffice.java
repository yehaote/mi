package mi.practice.java.base.enumerated;

import java.util.Iterator;

import mi.practice.java.base.util.Enums;
/**
 * enum的另一种使用方法, 可以把它作为一种处理链来使用.
 * 定义Mail以及Mail的各种属性,
 * 添加随机生产的方法.
 * 定义MailHandler用来处理Mail,
 * 注意mailHandler的处理顺序是有要求的,
 * 这个顺序是通过enum的定义顺序来决定的.
 * 根据不同的处理结果去执行不同的.
 */
class Mail {
	// 定义五个枚举类型
	enum GeneralDelivery {
		YES, NO1, NO2, NO3, NO4, NO5
	}

	enum Scannability {
		UNSCANNABLE, YES1, YES2, YES3, YES4, YES5
	}

	enum Readability {
		ILLEGIBLE, YES1, YES2, YES3, YES4, YES5
	}

	enum Address {
		INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6
	}

	enum ReturnAddress {
		MISSING, OK1, OK2, OK3, OK4, OK5
	}

	// 分别包含枚举的引用
	GeneralDelivery generalDelivery;
	Scannability scannability;
	Readability readability;
	Address address;
	ReturnAddress returnAddress;
	static long counter = 0;
	final long id = counter++;

	@Override
	public String toString() {
		return "Mail " + id;
	}

	public String Details() {
		return toString() + ", General Delivery: " + generalDelivery
				+ ", Address Scanability: " + scannability
				+ ", Address Readability: " + readability
				+ ", Address Address: " + address + ", Return address: "
				+ returnAddress;
	}

	public static Mail randomMail() {
		Mail m = new Mail();
		m.generalDelivery = Enums.random(GeneralDelivery.class);
		m.scannability = Enums.random(Scannability.class);
		m.readability = Enums.random(Readability.class);
		m.address = Enums.random(Address.class);
		m.returnAddress = Enums.random(ReturnAddress.class);
		return m;
	}

	public static Iterable<Mail> generator(final int count) {
		return new Iterable<Mail>() {
			int n = count;

			@Override
			public Iterator<Mail> iterator() {
				return new Iterator<Mail>() {
					@Override
					public boolean hasNext() {
						return n-- > 0;
					}

					@Override
					public Mail next() {
						return randomMail();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}

public class PostOffice {
	enum MailHandler {
		GENERAL_DELIVERY {
			@Override
			boolean handle(Mail m) {
				switch (m.generalDelivery) {
				case YES:
					System.out.println("Using general delivery for " + m);
					return true;
				default:
					return false;
				}
			}
		},
		MACHINE_SCAN {
			@Override
			boolean handle(Mail m) {
				switch (m.scannability) {
				case UNSCANNABLE:
					return false;
				default:
					switch (m.address) {
					case INCORRECT:
						return false;
					default:
						System.out
								.println("Delivering " + m + " automatically");
						return true;
					}
				}
			}
		},
		VISUAL_INSPECTION {
			@Override
			boolean handle(Mail m) {
				switch (m.readability) {
				case ILLEGIBLE:
					return false;
				default:
					switch (m.address) {
					case INCORRECT:
						return false;
					default:
						System.out.println("Delivering " + m + " normally");
						return true;
					}
				}
			}
		},
		RETURN_TO_SENDER {
			@Override
			boolean handle(Mail m) {
				switch (m.returnAddress) {
				case MISSING:
					return false;
				default:
					System.out.println("Returning " + m + "to sender");
					return true;
				}
			}
		};
		abstract boolean handle(Mail m);
	}

	static void handle(Mail m) {
		for (MailHandler handler : MailHandler.values()) {
			if (handler.handle(m)) {
				return;
			}
		}
		System.out.println(m + " is a dead letter");
	}

	public static void main(String[] args) {
		for (Mail mail : Mail.generator(10)) {
			System.out.println(mail.Details());
			handle(mail);
			System.out.println("******************");
		}
	}
}
