import game.Game;
import game.GameLoop;

public class Launcher {
  
	private static int width = 800;
	private static int height = width / 16 * 9;

	public static void main(String[] args) {
		new Thread(new GameLoop(new Game(width, height))).start();
		//test
	}
}