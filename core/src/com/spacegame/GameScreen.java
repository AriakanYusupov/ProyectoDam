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
import com.badlogic.gdx.utils.viewport.FitViewport;

import Entidades.PlayerEntity;
import Entidades.AlienEntity;
import Entidades.LaserEntity;


	/**
    * Clase con la pantalla donde se juega
    */
	public class GameScreen extends BaseScreen {

		//instancia Stage de Scene2D

		private Stage stage;

		//Instancia World de Box2D

		private World world;

		//Entidad del jugador

		private PlayerEntity player;

		//posicion de la cámara
		private Vector3 position;



		/**
		 * Crea la pantalla.
		 * @param game
		 */
		public GameScreen(MainGame game) {
			super(game);

			// nuevo escenario
			stage = new Stage(new FitViewport(640, 360));
			position = new Vector3(stage.getCamera().position);

			// nuevo World
			world = new World(new Vector2(0, -10), true);
			world.setContactListener(new GameContactListener());

		}

		/**
		 * método que se ejecuta justo antes de mostrar la pantalla
		 * se pone el estado inicial del juego
		 */
		@Override
		public void show() {

		}

		/**
		 * método que se ejecuta cuando la pantalla ya no se usa
		 * se usa para destruir y limpiar
		 */
		@Override
		public void hide() {
			// Limpia el escenario y borra todos los actores
			stage.clear();

		}

		/**
		 * método que pinta y actualiza el juego
		 */
		@Override
		public void render(float delta) {
			// se limpia la pantalla
			Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
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
