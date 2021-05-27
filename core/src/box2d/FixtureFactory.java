package box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.spacegame.ConstantesFisicas;

/**
 * Clase auxiliar de Box2DScreen para contrucción de fixtures
 */

public class FixtureFactory {
	public static Fixture createPlayerFixture(Body playerBody) {
		PolygonShape playerShape = new PolygonShape();
		playerShape.setAsBox(0.5f, 0.5f);
		Fixture fixture = playerBody.createFixture(playerShape, 3);
		Filter filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_PLAYER;
		filter.maskBits= ConstantesFisicas.MASK_PLAYER;
		fixture.setFilterData(filter);
		playerShape.dispose();
		return fixture;
	}

	public static Fixture createAlienFixture(Body alienBody) {
		PolygonShape alienShape = new PolygonShape();
		alienShape.setAsBox(0.5f, 0.25f);
		Fixture fixture = alienBody.createFixture(alienShape, 3);
		Filter filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_ALIEN;
		filter.maskBits= ConstantesFisicas.MASK_ALIEN;
		fixture.setFilterData(filter);
		alienShape.dispose();
		return fixture;
	}

	public static Fixture createLaserFixture(Body laserBody) {
		PolygonShape laserShape = new PolygonShape();
		laserShape.setAsBox(0.05f, 0.1f);
		Fixture fixture = laserBody.createFixture(laserShape, 3);
		Filter filter = new Filter();
		filter.categoryBits = ConstantesFisicas.CAT_LASER_PLAYER;
		filter.maskBits= ConstantesFisicas.MASK_LASER_PLAYER;
		fixture.setFilterData(filter);

		laserShape.dispose();
		return fixture;
	}
}

