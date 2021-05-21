package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase con la pantalla del menu
 */
public class MenuScreen extends BaseScreen {

	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private Skin skin;

	//imagen
	private Image logo;

	//botones
	private TextButton jugar, puntuacion, salir;

	// Musica de fondo
	private Music backgroundMusic;

	/**crea la pantalla
	 *
	 * @param game juego
	 */
	public MenuScreen(final MainGame game) {
		super(game);

		//creamos la pantalla
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));

		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//tabla para recoger los botones
		Table tabla = new Table();
		tabla.setFillParent(true);



		//creamos los botones. el primer parametro es el texto a mostrar, el segundo la skin a usar
		jugar = new TextButton("Jugar", skin);
		puntuacion = new TextButton("Mejores Puntuaciones", skin);
		salir = new TextButton("Salir", skin);
		backgroundMusic = game.getManager().get("music/Punky.mp3");

		tabla.add(jugar).size(300,80).pad(10);
		tabla.row();
		tabla.add(puntuacion).size(300,80).pad(10);
		tabla.row();
		tabla.add(salir).size(300,80).pad(10);


		// si hay que cargar una imagen, se hace así
		logo = new Image(game.getManager().get("defensor.png", Texture.class));
		logo.rotateBy(-90);
		logo.setPosition(logo.getHeight(), (Constantes.ALTO_PANTALLA+logo.getHeight())/2);


		// se añaden los capturadores de eventos.
		jugar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.gameScreen);
			}
		});

		puntuacion.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de puntuaciones
				game.setScreen(game.scoreScreen);
			}
		});

		salir.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//cierra el juego
				Gdx.app.exit();
			}
		});

			stage.addActor(tabla);
			stage.addActor(logo);
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
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

}
