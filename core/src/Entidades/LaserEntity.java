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

public class LaserEntity extends Actor {
	//textura del laser
	private Texture texture;

	//instancia del mundo
	private World world;

	//cuerpo del laser
	private Body body;

	//fixture del laser
	private Fixture fixture;

	//boolean para saber si el laser está vivo
	private boolean alive = true;

	public LaserEntity(World world, Texture texture, Vector2 position) {
		this.world = world;
		this.texture = texture;

		//Creación del cuerpo del laser
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
		box.setAsBox(0.1f, 2.0f);
		//crea la fixture
		fixture = body.createFixture(box, 3);
		//nombre de la fixture para ser usada en maingame
		fixture.setUserData("laser");
		//se destruye la forma que ya no hace falta
		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		//setSize();
	}

	/**
	 * método para pintar
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// hay que pintar el cuerpo de la nave según se mueva usando las constantes
		//setPosition(x, y);
		//batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * metodo para que actualice es escenario
	 * @param delta en segundos
	 */
	@Override
	public void act(float delta) {
	}

}
