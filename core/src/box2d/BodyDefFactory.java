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
		def.position.set(0, 0);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}

	/**
	 * método para los laser
	 * @return def
	 */
	public static BodyDef createLaser() {
		BodyDef def = new BodyDef();
		//def.position.set(x, y);
		def.position.set(0,0);
		def.type = BodyDef.BodyType.DynamicBody;
		return def;
	}
}