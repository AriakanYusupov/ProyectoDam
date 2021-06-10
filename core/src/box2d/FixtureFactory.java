package box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Clase auxiliar de Box2DScreen para contrucción de fixtures
 * indica las formas de los cuerpos para las colisiones
 */

public class FixtureFactory {

	//fixture de la nave del jugador
	public static Fixture createPlayerFixture(Body playerBody) {
		Vector2[] vertices = new Vector2[3];
		vertices[0] = new Vector2(-0.5f, -0.5f);
		vertices[1] = new Vector2(0.5f, -0.5f);
		vertices[2] = new Vector2(0, 0.5f);

		PolygonShape playerShape = new PolygonShape();
		playerShape.set(vertices);
		Fixture fixture = playerBody.createFixture(playerShape, 3);
		playerShape.dispose();
		return fixture;
	}

	public static Fixture createAlienFixture(Body alienBody) {
		PolygonShape alienShape = new PolygonShape();
		alienShape.setAsBox(0.5f, 0.25f);
		Fixture fixture = alienBody.createFixture(alienShape,3);
		alienShape.dispose();
		return fixture;
	}

	public static Fixture createAlienShooterFixture(Body alienBody) {
		PolygonShape alienShape = new PolygonShape();
		alienShape.setAsBox(0.5f, 0.25f);
		Fixture fixture = alienBody.createFixture(alienShape,3);
		alienShape.dispose();
		return fixture;
	}

	public static Fixture createLaserPlayerFixture(Body laserBody) {
		PolygonShape laserShape = new PolygonShape();
		laserShape.setAsBox(0.05f, 0.1f);
		Fixture fixture = laserBody.createFixture(laserShape, 3);
		laserShape.dispose();
		return fixture;
	}

	public static Fixture createLaserAlienFixture(Body laserBody) {
		PolygonShape laserShape = new PolygonShape();
		laserShape.setAsBox(0.05f, 0.1f);
		Fixture fixture = laserBody.createFixture(laserShape, 3);
		laserShape.dispose();
		return fixture;
	}

}

