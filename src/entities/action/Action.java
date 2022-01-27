package entities.action;

import entities.MovingEntity;
import game.state.State;

public abstract class Action {
	public abstract void update(State state, MovingEntity entity);
	public abstract boolean isDone();
	public abstract String getAnimationName();
}