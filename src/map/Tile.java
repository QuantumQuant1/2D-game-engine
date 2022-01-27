package map;

import java.awt.*;

import gfx.ImageUtils;

public class Tile {

    private Image sprite;

    public Tile() {
        sprite = ImageUtils.loadImage("res/sprites/tiles/woodfloor.png");
    }

    public Image getSprite() {
        return sprite;
    }
}