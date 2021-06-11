package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase con la pantalla del menu
 */
public class OptionScreen extends BaseScreen {

	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private Skin skin;

	//checkBox
	private CheckBox musica, sonido;
	//botones
	private TextButton volver, cambiar;

	//Imagen y música de fondo
	private Image fondo;
	private Music backgroundMusic;

	/**crea la pantalla
	 *
	 * @param game juego
	 */
	public OptionScreen(final MainGame game) {
		super(game);

		//creamos la pantalla
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));

		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

		//cargamos el fondo al principio
		fondo = new Image(game.getManager().get("fondo2.png", Texture.class));
		fondo.setBounds(0,0,Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA);
		stage.addActor(fondo);
		backgroundMusic = game.getManager().get("music/Punky.mp3");
		//tabla para recoger los botones
		Table tabla = new Table();
		tabla.setFillParent(true);

		//creamos los checkbox
		musica = new CheckBox("Musica", skin);
		sonido = new CheckBox("Sonidos", skin);

		//creamos los botones. el primer parametro es el texto a mostrar, el segundo la skin a usar
		volver = new TextButton("Volver", skin);
		cambiar = new TextButton("Cambiar Password", skin);

		tabla.add(musica).size(300, 40).pad(10).align(Align.left);
		tabla.row();
		tabla.add(sonido).size(300,40).pad(10).align(Align.left);
		tabla.row();
		tabla.add(cambiar).size(300,60).pad(10);
		tabla.row();
		tabla.add(volver).size(300,60).pad(10);


		musica.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				FileManager.getUserData().setMusica(musica.isChecked());
			}
		});

		sonido.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				FileManager.getUserData().setSonido(sonido.isChecked());
			}
		});

		cambiar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de cambiar la contraseña
				game.setScreen(game.cambioScreen);
			}
		});
		// se añaden los capturadores de eventos.
		volver.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//salva y lleva a la pantalla de menu
				FileManager.salvarUserData();
				game.setScreen(game.menuScreen);
			}
		});

			stage.addActor(tabla);
		}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {
		//se marcan los checkbox según lo guardado en el fichero de usuario
		if (FileManager.getUserData()!= null) {
			musica.setChecked(FileManager.getUserData().isMusica());
			sonido.setChecked(FileManager.getUserData().isSonido());
		}
		// volumen de la música y se activa
		if (FileManager.getUserData().isMusica()) {
			backgroundMusic.setVolume(0.75f);
			backgroundMusic.play();
		} else {
			backgroundMusic.stop();
		}

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


		//color en RGB de 0 a 1
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (FileManager.getUserData().isMusica()) {
			backgroundMusic.setVolume(0.75f);
			backgroundMusic.play();
		} else {
			backgroundMusic.stop();
		}

		stage.act();
		stage.draw();
	}

}
