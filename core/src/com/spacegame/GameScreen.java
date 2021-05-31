package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import Entidades.AlienEntity;
import Entidades.EntityFactory;
import Entidades.LaserAlienEntity;
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
		private ArrayList<AlienEntity> listaAliens;

		//Listas de laser
		private ArrayList<LaserEntity> listaLaser;
		private ArrayList<LaserAlienEntity> listaLaserAlien;

		//posicion de la cámara
		private Vector3 position;

		//imagen del fondo
		private SpriteBatch batch;
		private Texture fondo;

		// Musica de fondo
		private final Music backgroundMusic;

		//efectos de sonido
		private final Sound expCorta;
		private final Sound expLarga;
		private final Sound laserAlien;
		private final Sound laserDefensor;
		//private final Sound lose;

		//tablas para insertar puntos y vidas
		private Table tablaPuntos = new Table();
		private Table tablaVidas = new Table();
		//labels
		private Label labelPuntos, labelVidas, puntos, vidas;
		//puntos y vidas
		private Integer points, lifes;

		//booleano para cambiar de fase
		private boolean nuevaFase;

		//factory para poder crear los lasers
		EntityFactory factory = new EntityFactory(game.getManager());

		/**
		 * Crea la pantalla.
		 * @param game juego
		 */
		public GameScreen(MainGame game) {
			super(game);

			// nuevo escenario
			stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));

			// nuevo World
			world = new World(new Vector2(0, 0), true);
			world.setContactListener(new GameContactListener());

			// cargamos el fichero con la skin
			skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

			// carga de sonidos.
			expCorta = game.getManager().get("sound/Explosion_Corta.ogg");
			expLarga = game.getManager().get("sound/Explosion_Larga.ogg");
			laserAlien = game.getManager().get("sound/Laser_Alien.ogg");
			laserDefensor = game.getManager().get("sound/Laser_Defensor.ogg");
			//lose = game.getManager().get("sound/Lose.ogg");
			backgroundMusic = game.getManager().get("music/Punky.mp3");

			//labels
			points= 0;
			labelPuntos = new Label("Puntos", skin);
			labelVidas = new Label ("Vidas:", skin);
			puntos = new Label(points.toString(), skin);
			vidas = new Label("3", skin);

			tablaPuntos.add(labelPuntos).left();
			tablaPuntos.add(puntos).right();
			tablaPuntos.setPosition(20,Constantes.ALTO_PANTALLA-20);
			tablaPuntos.setSize(150,8);
			tablaVidas.add(labelVidas).left();
			tablaVidas.add(vidas).right();
			tablaVidas.setPosition(Constantes.ANCHO_PANTALLA-100,Constantes.ALTO_PANTALLA-20);
			tablaVidas.setSize(100,8);

			nuevaFase = false;

			//carga del fondo
			batch = new SpriteBatch();
			fondo = new Texture("fondo.png");

		}

		/**
		 * método que se ejecuta justo antes de mostrar la pantalla
		 * se pone el estado inicial del juego
		 */
		@Override
		public void show() {

			listaAliens = new ArrayList<>();
			listaLaserAlien= new ArrayList<>();
			listaLaser = new ArrayList<>();

			stage.setDebugAll(true);


			// Crea al jugador y lo pone en su posición inicial
			//como la escena está en metros hay que calcular el valor central de la pantalla usando la constante de conversión
			player = factory.createPlayer(world, new Vector2(Constantes.ANCHO_PANTALLA/(Constantes.PIXEL_A_METRO*2), 1f));

			//Crea aliens
			for (int j= 0; j< MathUtils.random(3,6); j++){
				listaAliens.add(factory.createAlien(world,(Constantes.ANCHO_PANTALLA* MathUtils.random(0.5f,1.5f)/(Constantes.PIXEL_A_METRO*2)),
					(Constantes.ALTO_PANTALLA*MathUtils.random(0.9f,1.5f)/(Constantes.PIXEL_A_METRO*2))));
				listaAliens.get(j).setName(j);
				// se añaden los aliens a la escena
				stage.addActor(listaAliens.get(j));
			}

			stage.addActor(player);
			stage.addActor(tablaPuntos);
			stage.addActor(tablaVidas);


			stage.setDebugAll(true);

			//se coloca la camara
			stage.getCamera().position.set(Constantes.ANCHO_PANTALLA/2, Constantes.ALTO_PANTALLA/2,0);
			stage.getCamera().update();

			// volumen de la música y se activa
			backgroundMusic.setVolume(0.5f);
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
			for (LaserEntity laser: listaLaser)
				laser.detach();

			// borramos la lista
			listaAliens.clear();
			listaLaser.clear();

		}

		/**
		 * método que pinta y actualiza el juego
		 */
		@Override
		public void render(float delta) {
			// se limpia la pantalla
			Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//ponemos el fondo
			batch.begin();
			batch.draw(fondo, 0, 0, Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA);
			batch.end();

			/**
			 * aquí hacemos que cuando el jugador pulsa el botón la nave dispare
			 */
			if (Gdx.input.isButtonJustPressed(1)){
				//solo se pueden tener 5 lasers en la pantalla
				if (listaLaser.size() < 5) {
					Vector2 posicionLaser = new Vector2(player.getPlayerPosition().x,
							player.getPlayerPosition().y/*+player.getHeight()/Constantes.PIXEL_A_METRO*/);
					listaLaser.add(factory.createLaser(world, posicionLaser));
					//sonido de disparo
					laserDefensor.play();
				}
				//se comprueba que los lasers desaparecen solos al cabo de un tiempo
				for (int i = 0; i < listaLaser.size(); i++) {
					stage.addActor(listaLaser.get(i));
					listaLaser.get(i).vidaLaser();
					//se quitan de la lista para que se puedan disparar más
					if (listaLaser.get(i) != null && !listaLaser.get(i).isAlive()) {
						listaLaser.remove(i);
						i--;
					}
				}
			}
			/**
			 * actualizamos los puntos
			 */
			if (player.isAlive()){
				puntos.setText(points.toString());
			}
			//el jugador muere, se va a GAME OVER
			if (!player.isAlive()){
				backgroundMusic.stop();
				//paramos los aliens
				stopAliens();

				stage.addAction(Actions.sequence(
						//esperamos un segundo para cambiar de pantalla
						Actions.delay(1.5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								//cambiamos de pantalla
								game.setScreen(game.gameOverScreen);
							}
						})
				));
			}

			//cambio de fase
		/*	if (player.isAlive() && nuevaFase){
				System.out.println("cambio de fase");
				game.setScreen(game.gameScreen);
			}*/
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
	 * método para parar todos los aliens
	 */
	private void stopAliens(){
			for (int i= 0; i < listaAliens.size();i++)
			listaAliens.get(i).setStop(true);
		}

		/**
		 * Clase que controla las colisiones.
		 * a implementar.
		 */
		private class GameContactListener implements ContactListener {

			/**
			 *método para controlar las colisiones
			 * @param contact la colisión
			 * @param userA el primer objeto
			 * @param userB el segundo objeto
			 */
			private boolean hayContacto(Contact contact, Object userA, Object userB) {
				Object userDataA = contact.getFixtureA().getUserData();
				Object userDataB = contact.getFixtureB().getUserData();

				//comprobamos que no sea nulo
				if (userDataA == null || userDataB == null) {
					return false;
				}

				//no se sabe que valor asigna a cada objeto que hace el contacto, por lo que hay que comprobar ambos casos
				return (userDataA.equals(userA) && userDataB.equals(userB)) ||
						       (userDataA.equals(userB) && userDataB.equals(userA));

			}

			@Override
			public void beginContact(Contact contact) {
				//colision de laser enviado por el jugador contra un alien
				for (int i = 0; i < listaLaser.size(); i++){
					listaLaser.get(i).setName(i);
					for (int j = 0; j < listaAliens.size();j++){
						if (hayContacto(contact, listaLaser.get(i).getName(), listaAliens.get(j).getName())){
							//muere el laser
							listaLaser.get(i).setAlive(false);
							listaLaser.get(i).remove();
							//muere el alien y se quita de la lista
							listaAliens.get(j).setAlive(false);
							System.out.println("antes " + listaAliens.size());
							listaAliens.remove(j);
							System.out.println("despues " + listaAliens.size());
							//efecto de sonido
							expCorta.play();
							//sumamos puntos
							points+= 100;
							//renombramos los aliens para que no de error por nulo
							for (Integer x = 0; x < listaAliens.size(); x++) {
								listaAliens.get(x).setName(x);
							}
							if (listaAliens!= null && listaAliens.isEmpty()){
								nuevaFase = true;
							}
							break;
						}
					}
				}
				//colisión de la nave del jugador con un alien
				for (int i = 0; i < listaAliens.size();i++){
					if(hayContacto(contact, "player", listaAliens.get(i).getName())) {
						player.setAlive(false);
						expLarga.play();
					}
				}

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

	public LaserEntity getLaser() {
		return laser;
	}

	public void setLaser(LaserEntity laser) {
		this.laser = laser;
	}

}
