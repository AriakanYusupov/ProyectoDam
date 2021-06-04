package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase con la pantalla de la puntuación
 */
public class ScoreScreen extends BaseScreen{

	private final SimpleDateFormat formatoFecha  = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	//instancia Stage de Scene2D
	private Stage stage;
	//Skin para los botones
	private Skin skin;
	//botón
	private TextButton volver;

	//imagen de titulo
	Image titulo, fondo;
	//tabla para ordenar
	Table tabla;


	// Musica de fondo
	private Music backgroundMusic;

	//datos para imprimir
	UserData userData;

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
		fondo = new Image(game.getManager().get("fondo2.png", Texture.class));
		fondo.setBounds(0,0,Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA);
		titulo = new Image(game.getManager().get("highscores.png", Texture.class));

		volver = new TextButton("Volver", skin);
		stage.addActor(fondo);
		titulo.setPosition(Constantes.ANCHO_PANTALLA/2, Constantes.ALTO_PANTALLA-titulo.getHeight());
		stage.addActor(titulo);
		tabla = new Table(skin);
		tabla.setFillParent(true);
		tabla.row();
		tabla.add(volver).size(300, 20);

		backgroundMusic = game.getManager().get("music/Punky.mp3");

		stage.addActor(tabla);


		// se añaden los capturadores de eventos.
		volver.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				//setNull();
				game.setScreen(game.menuScreen);
			}
		});
	}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {

		userData= null;
		tabla.clear();

		if (FileManager.userData!= null) {

			//tabla.add(titulo).size(600,200).colspan(2);
			userData = FileManager.cargarUserData(FileManager.getUserData().getNombreUsuario());

			for (int i = 0; i < Constantes.LISTADO_PUNTUACION_MAXIMA; i++) {
				Date fechainit = new Date(1L);
				//miramos que la fecha sea nueva, si no es actual no se pone en el listado
				if (userData.getFechasPuntosMax()[i].after(fechainit)) {
						String puntuacion = userData.getPuntuacion(userData.getPuntosMax()[i]);
						Label puntos = new Label(puntuacion, skin);
						DateFormat Date = DateFormat.getDateInstance();
						Label fechaTabla = new Label(Date.format(userData.getFechasPuntosMax()[i]), skin);
						fechaTabla.setAlignment(Align.right);
						tabla.add(puntos).size(150, 20);
						tabla.add(fechaTabla).size(150, 20);
						tabla.row();
				}
			}
			tabla.row();
			tabla.add(volver).fill().colspan(2);
		}

		stage.addActor(tabla);

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

	/**
	 * vacía el userdata
	 */
	public void setNull(){
		this.userData= null;
	}


}
