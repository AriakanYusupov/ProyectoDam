package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
	 * fichero para la skin de botones y componentes
	 */
	private Skin skin;

	//etiqueta de game over
	private Image gameOver;

	//boton para volver al menu
	private TextButton menu, jugar;

	private Table tabla;

	private final Sound lose;

	/**
	 * Crea la pantalla.
	 * @param game juego
	 */
	public GameOverScreen(final MainGame game) {
		super(game);

		// nuevo escenario y skin
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//tabla para recoger los botones
		Table tabla = new Table();
		tabla.setFillParent(true);

		lose = game.getManager().get("sound/Lose.ogg");

		// texto usando la skin
		gameOver = new Image(game.getManager().get("gameover.png", Texture.class));
		menu = new TextButton("Menu", skin);
		jugar = new TextButton("Volver a jugar", skin);

		tabla.add(gameOver).fill().expand();
		tabla.row();
		tabla.add(jugar).size(300,80).pad(10);
		tabla.row();
		tabla.add(menu).size(300,80).pad(10);
		tabla.row();

		// se añaden los capturadores de eventos.
		jugar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.gameScreen);
			}
		});

		menu.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de puntuaciones
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

		if (FileManager.getUserData().isSonido()){
		lose.play();}

		if (FileManager.getUserData()!= null) {
			FileManager.getUserData().cambiarListaPuntos(FileManager.getUserData().getPuntosObtenidos(), FileManager.getUserData().getNivelAlcanzado());
			FileManager.salvarUserData();
			GameScreen.setNivelStatic(0);
			}

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

		Gdx.gl.glClearColor(0.0f, 0.06f, 0.21f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}


