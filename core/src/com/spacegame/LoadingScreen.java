package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase pantalla que se ejecuta al entrar y vale para cargar los recursos.
 * Muestra la pantalla de carga con el progreso en porcentaje
 */

public class LoadingScreen extends BaseScreen {

	/**
	 * las etiquetas se consideran antores en Scene2D
	 */
	private Stage stage;

	/**
	 * fichero para la skin de botones y componentes
	 */
	private Skin skin;

	/**
	 * etiqueta para mostrar texto en la pantalla
	 */
	private Label loading;

	public LoadingScreen(MainGame game) {
		super(game);

		// se generan el stage y la skin.
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		// texto usando la skin
		loading = new Label("Cargando...", skin);
		//posición del texto
		loading.setPosition(Constantes.ANCHO_PANTALLA/2 - loading.getWidth(), Constantes.ALTO_PANTALLA /2 - loading.getHeight());
		stage.addActor(loading);
	}

	/**
	 * método para pintar la pantalla
	 *
	 * @param delta es el tiempo (en segundos) entre frames
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// método para cargar los recursos
		if (game.getManager().update()) {
			//cuando termina de cargar avisa
			game.finishLoading();
		} else {
			// mientras carga se muesra el texto con el porcentaje
			int progress = (int) (game.getManager().getProgress() * 100);
			loading.setText("Cargando... " + progress + "%");
		}

		stage.act();
		stage.draw();
	}

	/**
	 * método para eliminar de la memoria lo que ya no se usa
	 */
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}