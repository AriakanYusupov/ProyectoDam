package box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Clase auxiliar de Box2DScreen para contrucción de fixtures
 */

public class FixtureFactory {
	public static Fixture createPlayerFixture(Body playerBody) {
		PolygonShape playerShape = new PolygonShape();
		playerShape.setAsBox(0.5f, 0.5f);
		Fixture fixture = playerBody.createFixture(playerShape, 3);
		playerShape.dispose();
		return fixture;
	}

	public static Fixture createAlienFixture(Body alienBody) {
		PolygonShape alienShape = new PolygonShape();
		alienShape.setAsBox(0.5f, 0.25f);
		Fixture fixture = alienBody.createFixture(alienShape, 3);
		alienShape.dispose();
		return fixture;
	}

	public static Fixture createLaserFixture(Body laserBody) {
		PolygonShape laserShape = new PolygonShape();
		laserShape.setAsBox(0.05f, 0.1f);
		Fixture fixture = laserBody.createFixture(laserShape, 3);
		laserShape.dispose();
		return fixture;
	}
}
