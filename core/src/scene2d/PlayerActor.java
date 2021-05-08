package scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Clase con el actor del jugador usando para Scene2D
  */

public class PlayerActor extends Actor{


	//textura del jugador
	private Texture player;

	//boolean para saber si está vivo
	private boolean alive;

	/**
	 * constructor
	 * @param player
	 */
	public PlayerActor(Texture player) {
		this.player = player;
		this.alive = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(player, getX(), getY(), getWidth(), getHeight());
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}