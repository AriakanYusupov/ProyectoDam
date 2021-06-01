package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase para la pantalla inicial del juego
 */
public class InitScreen extends BaseScreen{

	//instancia Stage de Scene2D
	private Stage stage;

	private Image fondo;
	private Image titulo;

	/**
	 * Crea la pantalla.
	 * @param game
	 */
	public InitScreen(final MainGame game) {
		super(game);

		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA));

		fondo = new Image(game.getManager().get("fondo.png", Texture.class));
		titulo = new Image(game.getManager().get("titulo.png", Texture.class));
		titulo.setPosition((Constantes.ANCHO_PANTALLA-titulo.getWidth())/2, Constantes.ALTO_PANTALLA/2);

		stage.addActor(fondo);
		stage.addActor(titulo);


	}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {

		// Stages son procesadores de Inputs, por lo que pueden manejar los botones
		Gdx.input.setInputProcessor(stage);


	}

	/**
	 * método para que quita la pantalla al salir
	 */
	@Override
	public void hide() {
		// si la pantalla no se ve, hay que quitar el input system de la pantalla
		Gdx.input.setInputProcessor(null);
	}

	/**
	 * método para limpiar la memoria
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}

	/**
	 * método que pinta la pantalla según pasa el tiempo
	 * @param delta es el tiempo (en segundos) entre frames
	 */
	@Override
	public void render(float delta) {
		//color en RGB de 0 a 1
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.justTouched()){
			game.setScreen(game.menuScreen);
		}

		stage.act();
		stage.draw();
	}
}
