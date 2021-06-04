package scene2d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.spacegame.BaseScreen;
import com.spacegame.Constantes;
import com.spacegame.MainGame;

/**
 * Pantalla de Scene2D.
 */
public class Scene2DScreen extends BaseScreen {

	//la escena
	private Stage stage;

	//el jugador
	private PlayerActor player;

	//aliens
	private AlienActor alien;

	//laser
	private LaserActor laser;
	private LaserAlienActor laserAlien;

	//texturas usadas en los elementos
	private Texture playerTexture, alienTexture, laserTexture;

	public Scene2DScreen (MainGame game){
		super(game);

		// carga de texturas y regiones
		playerTexture = new Texture("defensor.png");
		alienTexture = new Texture("alien1.png");
		laserTexture = new Texture("laser.png");
	}

	/**
	 * método que carga al entrar en la pantalla
	 */
	@Override
	public void show() {
		// creamos el stage.
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA));

		//cargamos los actores
		player = new PlayerActor(playerTexture);
		alien = new AlienActor(alienTexture);
		laser = new LaserActor(laserTexture);
		laserAlien = new LaserAlienActor(laserTexture);
		player.setPosition(Constantes.ANCHO_PANTALLA/2, 100);

		// se añaden al stage para que se vean
		stage.addActor(player);
		stage.addActor(alien);
		stage.addActor(laser);
		stage.addActor(laserAlien);
	}

	/**
	 * método cuando se sale de la pantalla
	 */
	@Override
	public void hide() {
		stage.dispose();
	}

	/**
	 * método que pinta la pantalla
	 * @param delta es el tiempo (en segundos) entre frames
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();

		stage.draw();
	}

	/**
	 * método para comprobar cuando chocan los objetos
	 */
	private void checkCollisions() {

	}

	/**
	 * método que elimina de memoria lo que no se usa
	 */
	@Override
	public void dispose() {
		playerTexture.dispose();
		laserTexture.dispose();
		alienTexture.dispose();

		stage.dispose();
	}
}
