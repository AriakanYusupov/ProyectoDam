package scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AlienShooterActor extends Actor {



	//textura de alien
	private Texture alienDos;

	//boolean para saber si está vivo
	private boolean alive;

	/**
	 * constructor
	 * @param alien
	 */
	public AlienShooterActor(Texture alien) {
		this.alienDos = alien;
		setSize(alien.getWidth(), alien.getHeight());
		this.alive = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(alienDos, getX(), getY(), getWidth(), getHeight());
	}



	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
