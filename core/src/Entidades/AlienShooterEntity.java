package Entidades;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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

public class AlienShooterEntity extends Actor{
	//textura del alien
	private Texture texture;

	//instancia de mundo
	private World world;

	//cuerpo de alien
	private Body body;

	//fixture de alien
	private Fixture fixture;

	//booleanos
	private boolean alive, stop, direccion = false;

	//nombre del alien para poder revisar las colisiones
	private String name = "alienShooter";

	public AlienShooterEntity(World world, Texture texture, float x, float y) {
		this.world = world;
		this.texture = texture;
		alive= true;
		stop= false;

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
		fixture.setUserData("alienShooter");
		Filter filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_ALIEN;
		filter.maskBits= ConstantesFisicas.MASK_ALIEN;
		filter.groupIndex= -1;
		fixture.setFilterData(filter);

		//se destruye la forma que ya no hace falta

		box.dispose();

		//se pone en un tamaño para que se vea, hay que usar la clase Constantes
		//el tamaño de la nave es de 1 metro de ancho por medio de alto
		setSize(Constantes.PIXEL_A_METRO, Constantes.PIXEL_A_METRO/2 );

		if(MathUtils.random(0,1)==1){
			direccion= true;
		}
	}

	/**
	 * método para pintar
	 * @param batch Batch
	 * @param parentAlpha canal alfa del padre
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
		//si el alien esta vivo, este se mueve de un lado a otro
		if (alive){
			moveAlien(delta);
			//si se sale de la pantalla por abajo, vuelve a aparecer arriba
			if (body.getPosition().y*Constantes.PIXEL_A_METRO < -54) {
				transportAlien();
			}
		}
		//para para al alien
		if (stop){
			body.setLinearVelocity(0,0);
		}

		//el alien muere
		if (!isAlive()){
			body.setLinearVelocity(0,0);
			remove();
			body.destroyFixture(fixture);
			world.destroyBody(body);
		}
	}

	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	/**
	 * método que mueve los aliens
	 */
	private void moveAlien(float delta) {
		//la dirección en que se mueven al principio se elige al azar
		float timer =0;
		int random = 0;
		timer += delta;
		if (timer >= 1) {
			timer++;
			random = MathUtils.random(0,2);
			timer -= 1;
		}

		//se mueve a la derecha
		if (direccion) {
			body.setLinearVelocity(4.5f, 0f);
			//llega al límite de la pantalla cambia de sentido
			if (body.getPosition().x * Constantes.PIXEL_A_METRO >= 960) {
				direccion = !direccion;
			}
			if (random == 2){
				direccion = !direccion;
			}


		//se mueve a la izquierda
		} else {
			body.setLinearVelocity(-4.5f, 0f);
			//llega al límite y cambia de sentido
			if (body.getPosition().x * Constantes.PIXEL_A_METRO <= 0) {
				direccion = !direccion;
			}
			if (random == 2){
				direccion = !direccion;
			}
		}
	}

	/**
	 * método para trasladar a los aliens que se van por abajo a la parte superior de la pantalla
	 */
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

	/**
	 * método para asigar el nombre según la posición en la lista
	 * @param i Integer con el indice en la lista
	 */
	public void setName (Integer i){
		name = name + i.toString();
		fixture.setUserData(name);
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void increaseAlienSpeed (){
		body.setLinearVelocity(body.getLinearVelocity().x*1.2f, body.getLinearVelocity().y*1.1f);
	}
}


