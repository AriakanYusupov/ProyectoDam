package Entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

public class AlienEntity extends Actor{
	//textura del alien
	private Texture texture;

	//instancia de mundo
	private World world;

	//cuerpo de alien
	private Body body;

	//fixture de alien
	private Fixture fixture;

	//boolean para saber si el alien está vivo
	private boolean alive = true;


	public AlienEntity(World world, Texture texture, float x, float y) {
		this.world = world;
		this.texture = texture;

		//Creación del cuerpo del alien
		//definición del body
		BodyDef def = new BodyDef();
		//posición incial
		def.position.set(x,y);
		//tipo de body
		def.type = BodyDef.BodyType.DynamicBody;
		//creamos el body
		body = world.createBody(def);

		//Caja para las físicas
		//forma
		PolygonShape box = new PolygonShape();
		//tamaño caja en metros
		box.setAsBox(0.5f, 0.25f);
		//crea la fixture
		fixture = body.createFixture(box, 3);
		//nombre de la fixture para ser usada en maingame
		fixture.setUserData("Alien");
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
				(body.getPosition().y - 0.25f) * Constantes.PIXEL_A_METRO);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * metodo para que actualice es escenario
	 * @param delta en segundos
	 */
	@Override
	public void act(float delta) {
	}

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


