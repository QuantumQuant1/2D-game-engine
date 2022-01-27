package entities;

import ai.AIManager;
import controller.Controller;
import game.state.State;

public class NPC extends MovingEntity {

	private AIManager aiManager;

	public NPC(Controller controller, String name) {
		super(controller, name);
		aiManager = new AIManager();
	}

	@Override
	public void update(State state) {
		super.update(state);
		aiManager.update(state, this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		if (other instanceof Player) {
			motion.stop(willCollideX(other), willCollideY(other));
		}
	}
}