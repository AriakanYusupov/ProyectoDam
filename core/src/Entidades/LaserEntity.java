package Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.spacegame.Constantes;
import com.spacegame.ConstantesFisicas;

public class LaserEntity extends Actor {
	//textura del laser
	private Texture texture;

	//instancia del mundo
	private World world;

	//cuerpo del laser
	private Body body;

	//fixture del laser
	private Fixture fixture;

	//filtro para las colisiones
	private Filter filter;

	//boolean para saber si el laser está vivo
	private boolean alive;

	//contador para eliminar el laser por tiempo
	private float timer;
	//nombre
	private String name = "laser";


	public LaserEntity(World world, Texture texture, Vector2 posicion) {
		this.world = world;
		this.texture = texture;
		timer = 0;
		alive = true;

		//Creación del cuerpo del laser
		//defición del body
		BodyDef def = new BodyDef();
		//posición inicial
		def.position.set(posicion.x, posicion.y);
		//tipo de body
		def.type = BodyDef.BodyType.DynamicBody;
		//creamos el body
		body = world.createBody(def);
		body.setLinearVelocity(0,6);

		//Caja para las físicas
		//forma
		PolygonShape box = new PolygonShape();
		//tamaño caja en metros
		box.setAsBox(0.05f, 0.1f);
		//crea la fixture
		fixture = body.createFixture(box, 3);
		//nombre de la fixture para ser usada en maingame
		fixture.setUserData("laser");

		filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_LASER_PLAYER;
		filter.maskBits= ConstantesFisicas.MASK_LASER_PLAYER;
		filter.groupIndex= -2;
		fixture.setFilterData(filter);
		//se destruye la forma que ya no hace falta
		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		setSize(Constantes.PIXEL_A_METRO/3, Constantes.PIXEL_A_METRO/3 );

	}

	/**
	 * método para pintar
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// hay que pintar el cuerpo de la nave según se mueva usando las constantes
		setPosition((body.getPosition().x - 0.05f) * Constantes.PIXEL_A_METRO,
				(body.getPosition().y - 0.1f) * Constantes.PIXEL_A_METRO);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * metodo para que actualice es escenario
	 * @param delta en segundos
	 */
	@Override
	public void act(float delta) {
		//se cuenta el tiempo de vida
		if (isAlive()) {
			vidaLaser();
		}
		//se elimina si no tiene que estar
		if (!isAlive()){
			filter.groupIndex= -1;
			fixture.setFilterData(filter);
			remove();

		}

	}

	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	/**
	 * método para que el laser desaparezca solo al cabe de un tiempo
	 */
	public void vidaLaser(){
		if (body.getPosition().y * Constantes.PIXEL_A_METRO > Constantes.ALTO_PANTALLA ){
			alive = false;
		}
	}



	@Override
	public String getName() {
		return name;
	}

	public void setName (Integer i){
		name = name + i.toString();
		fixture.setUserData(name);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
