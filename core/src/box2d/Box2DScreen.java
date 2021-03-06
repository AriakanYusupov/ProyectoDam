package box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spacegame.BaseScreen;
import com.spacegame.MainGame;

/**
 * Clase del motor Box2D
 */

public class Box2DScreen extends BaseScreen {
	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private Body playerBody, alienBody, alienShooterBody, laserPlayerBody, laserAlienBody;
	private Fixture playerFixture, alienFixture, alienShooterFixture, laserPlayerFixture, laserAlienFixture;

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
		alienBody = world.createBody(BodyDefFactory.createAlien());
		alienShooterBody =  world.createBody(BodyDefFactory.createAlienShooter());
		laserPlayerBody = world.createBody(BodyDefFactory.createLaserPlayer());
		laserAlienBody = world.createBody(BodyDefFactory.createLaserAlien());


		//creamos las fixtures
		playerFixture = FixtureFactory.createPlayerFixture(playerBody);
		alienFixture = FixtureFactory.createAlienFixture(alienBody);
		alienShooterFixture = FixtureFactory.createAlienFixture(alienShooterBody);
		laserPlayerFixture = FixtureFactory.createLaserPlayerFixture(laserPlayerBody);
		laserAlienFixture = FixtureFactory.createLaserAlienFixture(laserAlienBody);

		// le damos nombre para poder manejar colisciones.
		playerFixture.setUserData("player");
		alienFixture.setUserData("alien");
		alienShooterFixture.setUserData("alienShooter");
		laserPlayerFixture.setUserData("laserPlayer");
		laserAlienFixture.setUserData("laserAlien");

	}

	@Override
	public void dispose() {
		playerBody.destroyFixture(playerFixture);
		alienBody.destroyFixture(alienFixture);
		alienShooterBody.destroyFixture(alienShooterFixture);
		laserPlayerBody.destroyFixture(laserPlayerFixture);
		laserAlienBody.destroyFixture(laserAlienFixture);

		world.destroyBody(playerBody);
		world.destroyBody(alienBody);
		world.destroyBody(alienShooterBody);
		world.destroyBody(laserPlayerBody);
		world.destroyBody(laserAlienBody);

		world.dispose();
		renderer.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		world.step(delta, 6, 2);
		camera.update();

		renderer.render(world, camera.combined);
	}


	@Override
	public void hide() {

	}

}
