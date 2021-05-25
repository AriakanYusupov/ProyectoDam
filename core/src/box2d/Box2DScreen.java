package box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.spacegame.BaseScreen;
import com.spacegame.Constantes;
import com.spacegame.MainGame;

/**
 * Clase del motor Box2D
 */

public class Box2DScreen extends BaseScreen {
	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private Body playerBody, alienBody, laserBody;
	private Fixture playerFixture, alienFixture, laserFixture;

	private boolean isAlive= true;



	public Box2DScreen(MainGame game){
	super(game);
	}

	/**
	 * método para mostrar el inicio del juego
	 */
	@Override
	public void show() {
		//creamos el mundo, sin gravedades
		world = new World(new Vector2(0,0), true);

		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//creamos los body
		playerBody = world.createBody(BodyDefFactory.createPlayer());
		alienBody = world.createBody(BodyDefFactory.createAlien(0,0));
		laserBody = world.createBody(BodyDefFactory.createLaser(0,0));

		//creamos las fixtures
		playerFixture = FixtureFactory.createPlayerFixture(playerBody);
		alienFixture = FixtureFactory.createAlienFixture(alienBody);
		laserFixture = FixtureFactory.createLaserFixture(laserBody);

		// le damos nombre para poder manejar colisciones.
		playerFixture.setUserData("player");
		alienFixture.setUserData("alien");
		laserFixture.setUserData("laser");

		// indicamos el contact Listener para controlar colisiones
		world.setContactListener(new Box2DScreenContactListener());

	}

	@Override
	public void dispose() {
		playerBody.destroyFixture(playerFixture);
		alienBody.destroyFixture(alienFixture);
		laserBody.destroyFixture(laserFixture);

		world.destroyBody(playerBody);
		world.destroyBody(alienBody);
		world.destroyBody(laserBody);

		world.dispose();
		renderer.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Movimiento del jugador, la nave se mueve hacia donde se ha tocado

		if (Gdx.input.isTouched()){
			movePlayer(Gdx.input.getX());
		} else {
			stopPlayer();
		}


		world.step(delta, 6, 2);
		camera.update();

		renderer.render(world, camera.combined);
	}


	@Override
	public void hide() {

	}

	private void movePlayer(int x) {
		System.out.println("box2d");
		Vector2 posicion = playerBody.getPosition();
		if (x< playerBody.getPosition().x*Constantes.PIXEL_A_METRO) {

			playerBody.setLinearVelocity(-6,0);
		} else if (x> playerBody.getPosition().x* Constantes.PIXEL_A_METRO) {

			playerBody.setLinearVelocity(6,0);
		}
	}
	private void stopPlayer() {
		playerBody.setLinearVelocity(0,0);
	}
	/**
	 * clase interna para majerar colisiones
	 */
	private class Box2DScreenContactListener implements ContactListener {

		/**
		 * método para controlar colisiones
		 * @param contact Contacto
		 */
		@Override
		public void beginContact(Contact contact) {
			// Get the fixtures.
			Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

			//control para evitar nulos
			if (fixtureA.getUserData() == null || fixtureB.getUserData() == null) {
				return;
			}

			// no sabemos de antermano que fixtures asigna como A o B, por lo que hay que comprobarlo

			if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("alien")) ||
					    (fixtureA.getUserData().equals("alien") && fixtureB.getUserData().equals("player"))) {

				//el jugador choca con un alien, muerte
				isAlive= false;
			}

			//jugador y laser
			if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("laser")) ||
					    (fixtureA.getUserData().equals("laser") && fixtureB.getUserData().equals("player"))) {
				isAlive = false;
			}

			//alien y laser
			if ((fixtureA.getUserData().equals("alien") && fixtureB.getUserData().equals("laser")) ||
					    (fixtureA.getUserData().equals("laser") && fixtureB.getUserData().equals("alien"))) {

			}
		}

		@Override
		public void endContact(Contact contact) {

		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {

		}

	}


}
