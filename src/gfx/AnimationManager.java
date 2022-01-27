package gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import core.Direction;
import game.Game;

public class AnimationManager {
   
	private String currentAnimationName;
	private BufferedImage currentAnimationSheet;
	private int updatesPerFrame;
	private int currentFrameTime;
	private int frameIndex;
	private int directionIndex;
	private String name;
	private Map<String, Image> animationSheets;

	public AnimationManager(String name, String action) {
		updatesPerFrame = 20;
		currentFrameTime = 0;
		frameIndex = 0;
		directionIndex = 0;
		currentAnimationName = "";
		this.name = name;
		animationSheets = new HashMap<>();
		loadSprites();
		playAnimation(action);
	}

	private void loadSprites() {
		String action = null;
		
		for (int i = 0; i < 3; i++) {
			if (i == 0) action = "cough";
			if (i == 1) action = "stand";
			if (i == 2) action = "walk";
			
			animationSheets.put(action, ImageUtils.loadImage("res/sprites/units/" + name + "/" + action + ".png"));
		}
	}

	public Image getSprite() {
		return currentAnimationSheet.getSubimage(
			frameIndex * Game.SPRITE_SIZE,
			directionIndex * Game.SPRITE_SIZE,
			Game.SPRITE_SIZE,
			Game.SPRITE_SIZE
		);
	}

	public void update(Direction direction) {
		currentFrameTime++;
		directionIndex = direction.getAnimationRow();

		if (currentFrameTime >= updatesPerFrame) {
			currentFrameTime = 0;
			frameIndex++;

			if (frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE) {
				frameIndex = 0;
			}
		}
	}

	public void playAnimation(String action) {
		if (!action.equals(currentAnimationName)) {
			this.currentAnimationSheet = (BufferedImage) animationSheets.get(action);
			currentAnimationName = action;
			frameIndex = 0;
		}
	}
}