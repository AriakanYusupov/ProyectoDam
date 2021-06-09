package box2d;


import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Clase auxiliar de Box2DScreen para contrucción de cuerpos
 */
public class BodyDefFactory {

	/**
	 * método para construir el jugador
	 * @return def
	 *
	 * el centro de coordenadas de los objetos es el centro de la caja
	 */
	public static BodyDef createPlayer() {
		BodyDef def = new BodyDef();
		def.position.set(0.5f, 0.5f);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}

	/**
	 * método para los aliens
	 * @return def
	 */
	public static BodyDef createAlien() {
		BodyDef def = new BodyDef();
		def.position.set(0.5f, 0.25f);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}

	/**
	 * método para los aliens
	 * @return def
	 */
	public static BodyDef createAlienShooter() {
		BodyDef def = new BodyDef();
		def.position.set(0.5f, 0.25f);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}

	/**
	 * método para los laser del jugador
	 * @return def
	 */
	public static BodyDef createLaserPlayer() {
		BodyDef def = new BodyDef();
		def.position.set(0,0);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}

	/**
	 * método para los laser de los aliens
	 * @return def
	 */
	public static BodyDef createLaserAlien() {
		BodyDef def = new BodyDef();
		def.position.set(0,0);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}
}