package entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.Controller;
import core.CollisionBox;
import core.Direction;
import core.Motion;
import core.Position;
import core.Size;
import entities.action.Action;
import entities.effect.Effect;
import game.state.State;
import gfx.AnimationManager;

public abstract class MovingEntity extends GameObject {
 
    protected Controller controller;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected List<Effect> effects;
    protected Optional<Action> action;

    protected Size collisionBoxSize;
    
    public MovingEntity(Controller controller, String name) {
        super();
        this.controller = controller;
        motion = new Motion(2);
        direction = Direction.S;
        animationManager = new AnimationManager(name, "stand");
        effects = new ArrayList<>();
        action = Optional.empty();
        collisionBoxSize = new Size(16, 28);
    }    

    @Override
    public void update(State state) {
        handleAction(state);
        handleMotion();
        animationManager.update(direction);
        effects.forEach(effect -> effect.update(state, this));
        
        handleCollisions(state);
        manageDirection();
        decideAnimation();
        
        position.apply(motion);

        cleanup();
    }

    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    protected abstract void handleCollision(GameObject other);

    private void handleMotion() {
        if (!action.isPresent()) {
            motion.update(controller);
        } else {
            motion.stop(true, true);
        }
    }

    private void handleAction(State state) {
        if (action.isPresent()) action.get().update(state, this);
    }

    private void cleanup() {
        List.copyOf(effects).stream().filter(Effect::shouldDelete).forEach(effects::remove);
        if (action.isPresent() && action.get().isDone()) action = Optional.empty(); 
    }

    private void decideAnimation() {
        if (action.isPresent()) {
            animationManager.playAnimation(action.get().getAnimationName());
        } else if (motion.isMoving()) {
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    private void manageDirection() {
        if (motion.isMoving()) {
            direction = Direction.fromMotion(motion);
        }
    }

    @Override
    public boolean collididesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    @Override
    public CollisionBox getCollisionBox() {
        Position positionWithMotion = Position.copyOf(position);
        positionWithMotion.apply(motion);

        return new CollisionBox(
            new Rectangle(
                positionWithMotion.intX(),
                positionWithMotion.intY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight()
            )
        );
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public Controller getController() {
        return controller;
    }

    public void multiplySpeed(double multiplier) {
        motion.multiply(multiplier);
    }

    public void perform(Action action) {
        this.action = Optional.of(action);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void clearEffects() {
        effects.clear();
    }

    public boolean willCollideX(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(motion);

        return CollisionBox.of(positionWithXApplied, collisionBoxSize).collidesWith(otherBox);
    }
    
    public boolean willCollideY(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(motion);

        return CollisionBox.of(positionWithYApplied, collisionBoxSize).collidesWith(otherBox);
    }
}