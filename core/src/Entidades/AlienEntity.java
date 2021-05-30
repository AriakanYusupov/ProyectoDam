package Entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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
	private boolean alive;

	private String name = "alien";

	private boolean direccion = false;


	public AlienEntity(World world, Texture texture, float x, float y) {
		this.world = world;
		this.texture = texture;
		alive= true;

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
		fixture.setUserData("alien");
		//se destruye la forma que ya no hace falta
		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		setSize(Constantes.PIXEL_A_METRO, Constantes.PIXEL_A_METRO );

		if(MathUtils.random(0,1)==1){
			direccion= true;
		}
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
		if (alive){
			moveAlien();
			if (body.getPosition().y*Constantes.PIXEL_A_METRO < -54) {
				transportAlien();
			}
		}

		if (!isAlive()){
			remove();
			body.destroyFixture(fixture);
			world.destroyBody(body);
		}
	}

	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	private void moveAlien() {

		if (direccion == true) {
			body.setLinearVelocity(4, -1);
			if (body.getPosition().x * Constantes.PIXEL_A_METRO >= 960) {
				direccion = !direccion;
			}
		} else {
			body.setLinearVelocity(-4, -1);
			if (body.getPosition().x * Constantes.PIXEL_A_METRO <= 0) {
				direccion = !direccion;
			}
		}
	}

	private void transportAlien(){
			Vector2 vector = new Vector2(body.getPosition().x,10);
			body.setTransform(vector, body.getAngle());
			body.setAwake(true);

	}



	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public String getName() {
		return name;
	}
	public void setName (Integer i){
		name = name + i.toString();
		fixture.setUserData(name);
	}
}


