package Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.spacegame.Constantes;

/**
 * Clase Entidad del jugador
 */

public class PlayerEntity extends Actor{
	//textura del jugador
	private Texture texture;

	//instancia del mundo
	private World world;

	//cuerpo del jugador
	private Body body;

	//fixture del jugador
	private Fixture fixture;

	//boolean para saber si el jugador está vivo
	private boolean alive = true;


	public PlayerEntity(World world, Texture texture, Vector2 position) {
		this.world = world;
		this.texture = texture;

		//Creación del cuerpo del jugador
		//defición del body
		BodyDef def = new BodyDef();
		//posición inicial
		def.position.set(position);
		//tipo de body
		def.type = BodyDef.BodyType.DynamicBody;
		//creamos el body
		body = world.createBody(def);

		//Caja para las físicas
		//forma
		PolygonShape box = new PolygonShape();
		//tamaño caja en metros
		box.setAsBox(0.5f, 0.5f);
		//crea la fixture
		fixture = body.createFixture(box, 3);
		//nombre de la fixture para ser usada en maingame
		fixture.setUserData("player");
		//se destruye la forma que ya no hace falta
		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		setSize(Constantes.PIXEL_A_METRO, Constantes.PIXEL_A_METRO );
	}

	/**
	 * método para pintar
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// hay que pintar el cuerpo de la nave según se mueva usando las constantes
		setPosition((body.getPosition().x - 0.5f) * Constantes.PIXEL_A_METRO,
				(body.getPosition().y - 0.5f) * Constantes.PIXEL_A_METRO);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * método para que actualice es escenario
	 * @param delta en segundos
	 */
	@Override
	public void act(float delta) {
	}

	/**
	 * método para limpiar memoria
	 */
	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
