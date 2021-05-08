package box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spacegame.MainGame;
import com.spacegame.BaseScreen;

/**
 * Clase del motor Box2D
 */

public class Box2DScreen extends BaseScreen {
	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private Body defensorBody;
	private Fixture defensorFixture;



	public Box2DScreen(MainGame game){
	super(game);
	}

	/**
	 * método para mostrar el inicio del juego
	 */
	@Override
	public void show() {
		world = new World(new Vector2(0,0), true);
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		defensorBody = world.createBody(createDefensorBodyDef());

		PolygonShape defensorShape = new PolygonShape();
		defensorShape.setAsBox(5,5);
		defensorFixture=defensorBody.createFixture(defensorShape,1);
		defensorShape.dispose();

	}

	private BodyDef createDefensorBodyDef() {
		BodyDef def = new BodyDef();
		def.position.set(0,0);
		def.type= BodyDef.BodyType.DynamicBody;
		return def;

	}

	@Override
	public void dispose() {
		defensorBody.destroyFixture(defensorFixture);
		world.destroyBody(defensorBody);
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