package mi.practice.java.base.innerclasses;

import static mi.practice.java.base.util.Print.*;

interface Game {
	boolean move();
}

interface GameFactory {
	Game getGame();
}

class Checkers implements Game {
	private Checkers() {
	}

	private int moves = 0;
	private static final int MOVES = 3;

	@Override
	public boolean move() {
		print("Checkers move " + moves);
		return ++moves != MOVES;
	}

	public static GameFactory factory = new GameFactory() {

		@Override
		public Game getGame() {
			return new Checkers();
		}
	};

}

class Chess implements Game{
	private Chess(){}
	private int moves=0;
	private static final int MOVES=4;
	public boolean move(){
		print("chess move "+moves);
		return ++moves!=MOVES;
	}
	public static GameFactory factory = new GameFactory() {
		
		@Override
		public Game getGame() {
			return new Chess();
		}
	};
}

public class Games {
	public static void playGame(GameFactory factory){
		Game g = factory.getGame();
		while(g.move()){
			
		}
	}
	public static void main(String[] args){
		playGame(Checkers.factory);
		playGame(Chess.factory);
	}
}
