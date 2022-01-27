package entities;

import controller.Controller;

public class Player extends MovingEntity {

	public Player(Controller controller) {
		super(controller, "matt");
	}

	@Override
	protected void handleCollision(GameObject other) {
		if (other instanceof NPC) {
			NPC npc = (NPC) other;
			npc.clearEffects();
		}
	}
}