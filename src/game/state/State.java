package game.state;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import core.Position;
import core.Size;
import display.Camera;
import entities.GameObject;
import game.Time;
import input.Input;
import map.GameMap;

public abstract class State {
   
	protected GameMap gameMap;
	protected List<GameObject> gameObjects;
	protected Input input;
	protected Camera camera;
	protected Time time;

	public Camera getCamera() {
		return this.camera;
	}

	public State(Size windowSize, Input input) {
		this.input = input;
		gameObjects = new ArrayList<>();
		camera = new Camera(windowSize);
		time = new Time();
	}

	public GameMap getGameMap() {
		return this.gameMap;
	}

	public void update() {
		sortObjectsByPosition();
		gameObjects.forEach(gameObject -> gameObject.update(this));
		camera.update(this);
	}

	private void sortObjectsByPosition() {
		gameObjects.sort(Comparator.comparing(gameObject -> gameObject.getPosition().getY()));
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public Time getTime() {
		return this.time;
	}

    public Position getRandomPosition() {
        return gameMap.getRandomPosition();
    }

    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {
		return gameObjects.stream().filter(other -> other.collididesWith(gameObject)).collect(Collectors.toList());
	}
}