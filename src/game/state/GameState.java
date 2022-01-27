package game.state;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entities.NPC;
import entities.Player;
import entities.effect.Sick;
import input.Input;
import map.GameMap;

public class GameState extends State {

	public GameState(Size windowSize, Input input) {
		super(windowSize, input);
		gameMap = new GameMap(new Size(26, 14));
		initializeCharacters();
	}

	private void initializeCharacters() {
		Player player = new Player(new PlayerController(input));
		
		gameObjects.add(player);
		camera.focusOn(player);

		initializeNPCs(200);
	}

	private void initializeNPCs(int numberOfNPCs) {
		for (int i = 0; i < numberOfNPCs; i++) {
			NPC npc = new NPC(new NPCController(), "dave");
			npc.setPosition(gameMap.getRandomPosition());
			npc.addEffect(new Sick());
			gameObjects.add(npc);
		}
	}
}