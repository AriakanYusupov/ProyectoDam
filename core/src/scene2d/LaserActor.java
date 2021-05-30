package scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LaserActor extends Actor {



	//textura de alien
	private Texture laser;

	//boolean para saber si está vivo
	private boolean alive;

	private float timer;

	/**
	 * constructor
	 * @param laser textura
	 */
	public LaserActor(Texture laser) {
		this.laser = laser;
		this.alive = true;
		setSize(laser.getWidth(), laser.getHeight());
		timer = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(laser, getX(), getY(), getWidth(), getHeight());
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}
}
