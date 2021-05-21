package com.spacegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase para la pantalla de Game Over
 */
public class GameOverScreen extends BaseScreen{

	//instancia Stage de Scene2D
	private Stage stage;

	//Instancia World de Box2D
	private World world;

	/**
	 * Crea la pantalla.
	 * @param game
	 */
	public GameOverScreen(MainGame game) {
		super(game);

		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		// nuevo World
		world = new World(new Vector2(0, -10), true);


	}
}
