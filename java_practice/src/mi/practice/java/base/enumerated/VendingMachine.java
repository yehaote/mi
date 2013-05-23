package mi.practice.java.base.enumerated;

import static mi.practice.java.base.enumerated.Input.*;

import java.util.EnumMap;
import java.util.Iterator;

import mi.practice.java.base.util.Generator;
import mi.practice.java.base.util.TextFile;
/**
 * enum还可以用来实现状态机
 */
enum Category {
	MONEY(NICKEL, DIME, QUARTER, DOLLAR), ITEM_SELECTION(TOOTHPASTE, CHIPS,
			SODA, SOAP), QUIT_TRANSCATION(ABORT_TRANSACTION), SHUT_DOWN(STOP);
	private Input[] values;

	Category(Input... values) {
		this.values = values;
	}

	private static EnumMap<Input, Category> categories = new EnumMap<Input, Category>(
			Input.class);
	static {
		for (Category c : Category.class.getEnumConstants()) {
			for (Input type : c.values) {
				categories.put(type, c);
			}
		}
	}

	public static Category categorize(Input input) {
		return categories.get(input);
	}
}

public class VendingMachine {
	private static State state = State.RESTING;
	private static int amount = 0;
	private static Input selection = null;

	enum StateDuration {
		TRANSIENT
	}

	enum State {
		// 休息
		RESTING {
			@Override
			void next(Input input) {
				switch (Category.categorize(input)) {
				case MONEY:
					amount += input.amount();
					state = ADDING_MONEY;
					break;
				case SHUT_DOWN:
					state = TERMINAL;
				default:
				}
			}
		},
		// 添加钱
		ADDING_MONEY {
			@Override
			void next(Input input) {
				switch (Category.categorize(input)) {
				case MONEY:
					amount += input.amount();
					break;
				case ITEM_SELECTION:
					selection = input;
					if (amount < selection.amount()) {
						System.out.println("Insufficient money for "
								+ selection);
					} else {
						state = DISPENSING;
					}
					break;
				case QUIT_TRANSCATION:
					state = GIVING_CHANGE;
					break;
				case SHUT_DOWN:
					state = TERMINAL;
				default:
				}
			}
		},
		DISPENSING(StateDuration.TRANSIENT) {
			@Override
			void next() {
				System.out.println("here is your " + selection);
				amount -= selection.amount();
				state = GIVING_CHANGE;
			}
		},
		GIVING_CHANGE (StateDuration.TRANSIENT){
			@Override
			void next() {
				if (amount > 0) {
					// give change to user
					System.out.println("Your change: " + amount);
					amount = 0;
				}
				state = RESTING;
			}
		},
		TERMINAL {
			void output() {
				System.out.println("Halted");
			}
		};
		private boolean isTransient = false;

		State() {
		}

		State(StateDuration trans) {
			isTransient = true;
		}

		void next(Input input) {
			System.out.println(this);
			throw new RuntimeException(
					"Only call new(Input input) for non-transient states");
		}

		void next() {
			System.out.println(this);
			throw new RuntimeException(
					"Only call next() for StateDurantion.TRANSIENT states");
		}

		void output() {
			System.out.println(amount);
		}
	}

	static void run(Generator<Input> gen) {
		while (state != State.TERMINAL) {
			state.next(gen.next());
			while (state.isTransient) {
				state.next();
			}
			state.output();
		}
	}

	public static void main(String[] args) {
		Generator<Input> gen = new RandomInputGenerator();
		if (args.length == 1) {
			gen = new FileInputGenerator(args[0]);
		}
		run(gen);
	}
}

class RandomInputGenerator implements Generator<Input> {
	@Override
	public Input next() {
		return Input.randomSelection();
	}
}

class FileInputGenerator implements Generator<Input> {
	private Iterator<String> input;

	public FileInputGenerator(String fileName) {
		input = new TextFile(fileName, ";").iterator();
	}

	@Override
	public Input next() {
		if (!input.hasNext()) {
			return null;
		}
		return Enum.valueOf(Input.class, input.next().trim());
	}
}
