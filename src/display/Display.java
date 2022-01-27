package display;

import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

import game.state.State;
import input.Input;

public class Display extends JFrame {

	private Canvas canvas;
	private Renderer renderer;
	private DebugRenderer debugRenderer;

	public Display(int width, int height, Input input) {
		setTitle("2D Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		renderer = new Renderer();
		debugRenderer = new DebugRenderer();

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);
		add(canvas);
		addKeyListener(input);
		pack();

		canvas.createBufferStrategy(2);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void render(State state, boolean debugMode) {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();

		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		renderer.render(state, graphics);
		if (debugMode) {
			debugRenderer.render(state, graphics);
		}
	
		graphics.dispose();
		bufferStrategy.show();
	}
}