package scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AlienActor extends Actor {



	//textura de alien
	private TextureRegion alien;

	//boolean para saber si está vivo
	private boolean alive;

	/**
	 * constructor
	 * @param alien
	 */
	public AlienActor(TextureRegion alien) {
		this.alien = alien;
		this.alive = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(alien, getX(), getY(), getWidth(), getHeight());
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
