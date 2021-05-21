package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase con la pantalla de la puntuación
 */
public class ScoreScreen extends BaseScreen{

	//instancia Stage de Scene2D
	private Stage stage;
	//Skin para los botones
	private Skin skin;
	//botón
	private TextButton volver;

	// Musica de fondo
	private Music backgroundMusic;

	/**
	 * Crea la pantalla.
	 * @param game
	 */
	public ScoreScreen(final MainGame game) {
		super(game);
		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//creamos el boton. el primer parametro es el texto a mostrar, el segundo la skin a usar
		volver = new TextButton("Volver", skin);
		backgroundMusic = game.getManager().get("music/Punky.mp3");

		// se añaden los capturadores de eventos.
		volver.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.menuScreen);
			}
		});

		// tamaño y posicion de los botones
		// el origen de coordenadas 0, 0 es la esquina inferior izquierda
		volver.setSize(100, 40);
		volver.setPosition(Constantes.ANCHO_PANTALLA/2-50,80);

		stage.addActor(volver);
	}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {
		// volumen de la música y se activa
		backgroundMusic.setVolume(1f);
		backgroundMusic.play();
		// hacemos que el Input Systen maneje el Stage.
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
		skin.dispose();
	}

	/**
	 * método que pinta la pantalla según pasa el tiempo
	 * @param delta es el tiempo (en segundos) entre frames
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}


}
