package entities.action;

import entities.MovingEntity;
import game.GameLoop;
import game.state.State;

public class Cough extends Action {

    private int lifeSpanInSeconds;


    public Cough() {
        lifeSpanInSeconds = GameLoop.UPDATES_PER_SECOND;
    }

    @Override
    public void update(State state, MovingEntity entity) {
        lifeSpanInSeconds--;
    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}