package com.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import box2d.Box2DScreen;

/**
 * Clase que contiene los metodos para crear las pantallas
 */
public class MainGame extends Game {


	/**
	 * objeto para controlar los recursos de manera centralizada
	 *
	 */
	private AssetManager manager;

	/**
	 * Pantallas del juego
	 */
	public BaseScreen loadingScreen, initScreen, logInScreen, menuScreen, gameScreen, gameOverScreen, scoreScreen;

	/**
	 * método para inicializar el controlador de recursos.
	 * se añaden todos a loadingScreen para mejorar la carga.
	 */
	@Override
	public void create() {

		manager = new AssetManager();
		//los parámentros son primero el nombre del archivo y segundo el tipo de archivo
		manager.load("Defensor.png", Texture.class);
		manager.load("Alien_1.png", Texture.class);
		manager.load("Alien_2.png", Texture.class);
		manager.load("Laser.png",Texture.class);

		manager.load("Sound/Cuenta_Atras.ogg", Sound.class);
		manager.load("Sound/Explosion_Corta.ogg", Sound.class);
		manager.load("Sound/Explosion_Larga.ogg", Sound.class);
		manager.load("Sound/Fin_Nivel.ogg", Sound.class);
		manager.load("Sound/Laser_Alien.ogg", Sound.class);
		manager.load("Sound/Laser_Defensor.ogg", Sound.class);
		manager.load("Sound/Lose.ogg", Sound.class);
		manager.load("Music/Punky.mp3", Music.class);

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
		scoreScreen= new ScoreScreen(this);

		setScreen(menuScreen);
	}

	public AssetManager getManager() {
		return manager;
	}

}