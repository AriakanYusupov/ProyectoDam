package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import Entidades.AlienEntity;
import Entidades.EntityFactory;
import Entidades.LaserEntity;
import Entidades.PlayerEntity;


/**
    * Clase con la pantalla donde se juega
    */
	public class GameScreen extends BaseScreen {

		//Skin
		private Skin skin;

		//instancia Stage de Scene2D
		private Stage stage;

		//Instancia World de Box2D
		private World world;

		//Entidad del jugador
		private PlayerEntity player;

		//Entidad Alien
		private AlienEntity alien;

		//Entidad del laser
		private LaserEntity laser;

		//Lista de Aliens
		private List<AlienEntity> listaAliens = new ArrayList<>();

		//posicion de la cámara
		private Vector3 position;

		// Musica de fondo
		private Music backgroundMusic;

		//efectos de sonido
		private Sound expCorta;
		private Sound expLarga;
		private Sound laserAlien;
		private Sound laserDefensor;
		private Sound Lose;

		//tablas para insertar puntos y vidas
		private Table tablaPuntos = new Table();
		private Table tablaVidas = new Table();
		//labels
		private Label labelPuntos, labelVidas, puntos, vidas;
		//private TextField tfPuntos, tfVidas;

		/**
		 * Crea la pantalla.
		 * @param game juego
		 */
		public GameScreen(MainGame game) {
			super(game);

			// nuevo escenario
			stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
			//position = new Vector3(stage.getCamera().position);

			// nuevo World
			world = new World(new Vector2(0, -10), true);
			world.setContactListener(new GameContactListener());

			// cargamos el fichero con la skin
			skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

			// carga de sonidos.
			expCorta = game.getManager().get("sound/Explosion_Corta.ogg");
			expLarga = game.getManager().get("sound/Explosion_Larga.ogg");
			laserAlien = game.getManager().get("sound/Laser_Alien.ogg");
			laserDefensor = game.getManager().get("sound/Laser_Defensor.ogg");
			Lose = game.getManager().get("sound/Lose.ogg");
			backgroundMusic = game.getManager().get("music/Punky.mp3");

			//labels
			labelPuntos = new Label("Puntos:", skin);
			labelVidas = new Label ("Vidas:", skin);
			puntos = new Label("5000", skin);
			vidas = new Label("3", skin);

			// tamaño y posicion
			/*labelPuntos.setSize(50, 8);
			puntos.setSize(100,8);
			labelVidas.setSize(50, 8);
			vidas.setSize(50,8);*/

			tablaPuntos.add(labelPuntos).left();
			tablaPuntos.add(puntos).right();
			tablaPuntos.setPosition(20,Constantes.ALTO_PANTALLA-20);
			tablaPuntos.setSize(150,8);
			tablaVidas.add(labelVidas).left();
			tablaVidas.add(vidas).right();
			tablaVidas.setPosition(Constantes.ANCHO_PANTALLA-100,Constantes.ALTO_PANTALLA-20);
			tablaVidas.setSize(100,8);


		}

		/**
		 * método que se ejecuta justo antes de mostrar la pantalla
		 * se pone el estado inicial del juego
		 */
		@Override
		public void show() {

			EntityFactory factory = new EntityFactory(game.getManager());

			// Crea al jugador y lo pone en su posición inicial
			player = factory.createPlayer(world, new Vector2(Constantes.ANCHO_PANTALLA/2, 100f));

			//Crea aliens
			//listaAliens.add(factory.createAlien(world,Constantes.ANCHO_PANTALLA/2 , 500f));
			alien = factory.createAlien(world,Constantes.ANCHO_PANTALLA/2 , 500f);
			//añadimos aliens y jugador
			/*for (AlienEntity alien : listaAliens)
				stage.addActor(alien);*/
			stage.addActor(alien);
			stage.addActor(player);

			stage.addActor(tablaPuntos);
			stage.addActor(tablaVidas);

			//stage.getCamera().position.set(position);

			stage.getCamera().update();


			// volumen de la música y se activa
			backgroundMusic.setVolume(0.75f);
			backgroundMusic.play();

		}

		/**
		 * método que se ejecuta cuando la pantalla ya no se usa
		 * se usa para destruir y limpiar
		 */
		@Override
		public void hide() {
			// Limpia el escenario y borra todos los actores
			stage.clear();

			// Detach de las entidades
			player.detach();
			for (AlienEntity alien : listaAliens)
				alien.detach();

			// borramos la lista
			listaAliens.clear();

		}

		/**
		 * método que pinta y actualiza el juego
		 */
		@Override
		public void render(float delta) {
			// se limpia la pantalla
			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// actualiza el escenario a lo que necesitamos
			stage.act();

			//actualiza el mundo, delta es tiempo y los otros parámetros vienen indicados en la documentación
			world.step(delta, 6, 2);


			// pinta la pantalla, último paso.
			stage.draw();
		}

		/**
		 * método para limpiar la pantalla y la memoria
		 */
		@Override
		public void dispose() {
			// limpia el escenario
			stage.dispose();

			// limpia el mundo
			world.dispose();
		}

		/**
		 * Clase que controla las colisiones.
		 * a implementar.
		 */
		private class GameContactListener implements ContactListener {


			@Override
			public void beginContact(Contact contact) {

			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		}



}
