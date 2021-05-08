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
		Texture playerTexture = manager.get("Defensor.png");
		return new PlayerEntity(world, playerTexture, position);
	}
	/**
	 * Crea la entidad alien
	 * @param world     mundo
	 * @param position  posición inicial (metros,metros).
	 * @return          alien.
	 */
	public AlienEntity createAlien(World world, Vector2 position) {
		Texture playerTexture = manager.get("Alien_1.png");
		return new AlienEntity(world, playerTexture, position);
	}


}
