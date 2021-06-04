package com.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que contiene los metodos para crear las pantallas
 */
public class MainGame extends Game {


	/**
	 * objeto para controlar los recursos de manera centralizada
	 */
	private AssetManager manager;

	/**
	 * Pantallas del juego
	 */
	public BaseScreen loadingScreen, initScreen, logInScreen, menuScreen, gameScreen, gameOverScreen, scoreScreen, registroScreen;

	/**
	 * método para inicializar el controlador de recursos.
	 * se añaden todos a loadingScreen para mejorar la carga.
	 */
	@Override
	public void create() {

		manager = new AssetManager();
		//los parámentros son primero el nombre del archivo y segundo el tipo de archivo
		manager.load("defensor.png", Texture.class);
		manager.load("alien1.png", Texture.class);
		//manager.load("alien2.png", Texture.class);
		manager.load("laser.png", Texture.class);
		manager.load("laseralien.png", Texture.class);
		manager.load("gameover.png", Texture.class);
		manager.load("fondo.png", Texture.class);
		manager.load("fondo2.png", Texture.class);
		manager.load("titulo.png", Texture.class);
		manager.load("highscores.png", Texture.class);

		manager.load("sound/Cuenta_Atras.ogg", Sound.class);
		manager.load("sound/Explosion_Corta.ogg", Sound.class);
		manager.load("sound/Explosion_Larga.ogg", Sound.class);
		manager.load("sound/Fin_Nivel.ogg", Sound.class);
		manager.load("sound/Laser_Alien.ogg", Sound.class);
		manager.load("sound/Laser_Defensor.ogg", Sound.class);
		manager.load("sound/Lose.ogg", Sound.class);
		manager.load("music/Punky.mp3", Music.class);

		// Enter the loading screen to load the assets.
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

	}

	/**
	 * método que llama a LoadingScreen cuando se han cargado todos los recursos
	 * Se cargan las demás pantallas y va a la pantalla principal cuando está listo.
	 */
	public void finishLoading() {

		initScreen = new InitScreen(this);
		logInScreen = new LogInScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		scoreScreen = new ScoreScreen(this);
		registroScreen = new RegistroScreen(this);

		setScreen(initScreen);
	}

	public AssetManager getManager() {
		return manager;
	}

}