package game.main;

import engine.core.GameContainer;
import engine.util.Logger;

public class Launcher {
	
	public static void main(String[] args) {
		Logger.log("Start test.", System.out);
		GameContainer game_container = new GameContainer();
		Test test = new Test(game_container);
		game_container.setGame(test);
		game_container.init();
		game_container.run();
		Logger.log("End test.", System.out);
	}

}
