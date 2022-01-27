package game;

public class GameLoop implements Runnable {
   
	public static final int UPDATES_PER_SECOND = 60;

    private Game game;

    private boolean running;
	private final double updateRate = 1d / 60d;

	private long nextStatTime;
	private int ups, fps;

    public GameLoop(Game game) {
        this.game = game;
    }

    public void run() {
        running = true;
		double accumulator = 0;
		long currentTime, lastTime = System.currentTimeMillis();
		nextStatTime = System.currentTimeMillis() + 1000;

		while (running) {
			currentTime = System.currentTimeMillis();
			double lastRenderTimeInSeconds = (currentTime - lastTime) / 1000d;
			accumulator += lastRenderTimeInSeconds;
			lastTime = currentTime;

			if (accumulator >= updateRate) {
				while (accumulator >= updateRate) {
					update();
					accumulator -= updateRate;
				}
			}
			render();
			printStats();
		}
    }

    private void printStats() {
		if (System.currentTimeMillis() > nextStatTime) {
			System.out.println("UPS: " + ups + "/FPS: " + fps);
			ups = 0;
			fps = 0;
			nextStatTime = System.currentTimeMillis() + 1000;
		}
	}

	private void update() {
		ups++;
        game.update();
    }

    private void render() {
		fps++;
        game.render();
    }
}