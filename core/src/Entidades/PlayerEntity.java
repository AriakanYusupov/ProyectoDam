package Entidades;

import com.badlogic.gdx.Gdx;
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

		Filter filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_PLAYER;
		filter.maskBits= ConstantesFisicas.MASK_PLAYER;
		filter.groupIndex = -2;
		fixture.setFilterData(filter);
		//se destruye la forma que ya no hace falta
		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		setSize(Constantes.PIXEL_A_METRO, Constantes.PIXEL_A_METRO );
	}

	/**
	 * método para pintar
	 * @param batch Batch con las cosas a dibujar
	 * @param parentAlpha alfa padre
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

		//controlamos la entrada para el movimiento de la nave
		if (Gdx.input.isTouched(0) && isAlive()) {
			movePlayer(Gdx.input.getX(0));
		} else stopPlayer();

	}

	/**
	 * método para limpiar memoria
	 */
	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	/**
	 * método para mover la nave
	 * @param x coodenada x de donde se pulsa
	 */
	private void movePlayer(int x) {
		//si se pulsa a la izquierda de la nave, esta se mueve a la izquierda
		if (x < body.getPosition().x * Constantes.PIXEL_A_METRO) {
			body.setLinearVelocity(-6, 0);
			//límite a la izquierda para que no se pueda salir de la pantalla
			if (body.getPosition().x * Constantes.PIXEL_A_METRO < 54f){
				stopPlayer();
			}
		//si se pulsa a la derecha, se mueve a la derecha
		} else if (x > body.getPosition().x * Constantes.PIXEL_A_METRO) {
			body.setLinearVelocity(6, 0);
			//límite por la derecha
			if (body.getPosition().x * Constantes.PIXEL_A_METRO > 906f){
				stopPlayer();
			}
		}
	}

	/**
	 * método para detener el movimiento de la nave
	 */
	private void stopPlayer() {
		body.setLinearVelocity(0,0);

	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Vector2 getPlayerPosition(){
		return new Vector2(body.getPosition().x, body.getPosition().y);
	}

}
