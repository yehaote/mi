package mi.practice.java.base.interfaces;

import static mi.practice.java.base.util.Print.*;
/**
 * 接口很不错,但是很容易过量使用
 * 我们应该首先选择类而不是接口, 但需要的时候再去重构
 */
interface Game {
	boolean move();
}

interface GameFactory {
	Game getGame();
}

class Checkers implements Game {
	private int moves = 0;
	private static final int MOVES = 3;

	public boolean move() {
		print("chechers move " + moves);
		return ++moves != MOVES;
	}
}

class CheckersFactory implements GameFactory {
	public Game getGame() {
		return new Checkers();
	}
}

class Chess implements Game {
	private int moves = 0;
	private static final int MOVES = 4;

	public boolean move() {
		print("chess move " + moves);
		return ++moves != MOVES;
	}
}

class ChessFactory implements GameFactory {
	public Game getGame() {
		return new Chess();
	}
}

public class Games {
	public static void playGame(GameFactory gameFactory) {
		Game game = gameFactory.getGame();
		while (game.move()) {
		}
	}

	public static void main(String[] args) {
		playGame(new CheckersFactory());
		playGame(new ChessFactory());
	}
}
