package Entidades;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Clase para crear entidades usando metodos de Factory
 */
public class EntityFactory {

	private AssetManager manager;

	/**
	 * Crea una entidad factory usando el manager de recursos.
	 * @param manager   manejador de recursos.
	 */
	public EntityFactory(AssetManager manager) {
		this.manager = manager;
	}

	/**
	 * Crea la entidad Jugador
	 * @param world     mundo
	 * @param position  posición inicial (metros,metros).
	 * @return          jugador.
	 */
	public PlayerEntity createPlayer(World world, Vector2 position) {
		Texture playerTexture = manager.get("defensor.png");
		return new PlayerEntity(world, playerTexture, position);
	}
	/**
	 * Crea la entidad alien
	 * @param world     mundo
	 * @param x  posición X inicial (metros)
	 * @param y  posición Y inicial (metros)
	 * @return alien.
	 */
	public AlienEntity createAlien(World world, float x, float y) {
		Texture alienTexture = manager.get("alien1.png");
		return new AlienEntity(world, alienTexture, x, y);
	}

	/**
	 * Crea la entidad alien
	 * @param world     mundo
	 * @param posicion vector con la posición de inicio del laser (en metros)
	 * @return laser.
	 */
	public LaserEntity createLaser(World world, Vector2 posicion) {
		Texture laserTexture = manager.get("laser.png");
		return new LaserEntity(world, laserTexture, posicion);
	}


}
