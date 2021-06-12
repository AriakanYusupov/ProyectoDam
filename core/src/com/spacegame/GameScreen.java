package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import Entidades.AlienEntity;
import Entidades.AlienShooterEntity;
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
		private AlienShooterEntity alienShooter;

		//Entidad del laser
		private LaserEntity laser;

		//Lista de Aliens
		private ArrayList<AlienEntity> listaAliens;
		private ArrayList<AlienShooterEntity> listaAlienShooter;

		//Listas de laser
		private ArrayList<LaserEntity> listaLaser;
		private ArrayList<LaserAlienEntity> listaLaserAlien;

		//posicion de la cámara
		private Vector3 position;

		//imagen del fondo
		private Image fondo, fondo2;

		// Musica de fondo
		private final Music backgroundMusic;

		//efectos de sonido
		private final Sound expCorta;
		private final Sound expLarga;
		private final Sound nuevoNivel;
		private final Sound laserAlien;
		private final Sound laserDefensor;
		//private final Sound lose;

		//tablas para insertar puntos y vidas
		private final Table tablaPuntos = new Table();
		private final Table tablaNivel = new Table();

		//labels
		private final Label labelPuntos, labelNivel, puntos, nivelTexto;
		//puntos y vidas
		private Integer points= 0, nivel = 0;
		private static int nivelStatic= 0;

		//booleano para cambiar de fase
		private boolean nuevaFase, otrofondo;

		//factory para poder crear los lasers
		EntityFactory factory = new EntityFactory(game.getManager());

		private int numeroAlienShooters = 0;
		private float timer = 0;

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
			skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

			// carga de sonidos.
			expCorta = game.getManager().get("sound/Explosion_Corta.ogg");
			expLarga = game.getManager().get("sound/Explosion_Larga.ogg");
			laserAlien = game.getManager().get("sound/Laser_Alien.ogg");
			laserDefensor = game.getManager().get("sound/Laser_Defensor.ogg");
			nuevoNivel = game.getManager().get("sound/Fin_Nivel.ogg");
			//lose = game.getManager().get("sound/Lose.ogg");
			backgroundMusic = game.getManager().get("music/Punky.mp3");

			//labels
			labelPuntos = new Label("Puntos", skin);
			labelNivel = new Label ("Nivel:", skin);
			puntos = new Label(points.toString(), skin);
			puntos.setAlignment(Align.right);
			nivelTexto = new Label(nivel.toString(), skin);
			nivelTexto.setAlignment(Align.right);

			tablaPuntos.add(labelPuntos).left();
			tablaPuntos.add(puntos).right();
			tablaPuntos.setPosition(20,Constantes.ALTO_PANTALLA-20);
			tablaPuntos.setSize(150,8);
			tablaNivel.add(labelNivel).left();
			tablaNivel.add(nivelTexto).right();
			tablaNivel.setPosition(Constantes.ANCHO_PANTALLA-100,Constantes.ALTO_PANTALLA-20);
			tablaNivel.setSize(100,8);

			nuevaFase = false;
			otrofondo = false;

			//carga del fondo
			fondo = new Image(game.getManager().get("fondo.png", Texture.class));
			fondo2 = new Image(game.getManager().get("fondo2.png", Texture.class));
		}



	/**
		 * método que se ejecuta justo antes de mostrar la pantalla
		 * se pone el estado inicial del juego
		 */
		@Override
		public void show() {

			// volumen de la música y se activa si el usuario asi lo quiere
			if (FileManager.getUserData().isMusica()) {
				backgroundMusic.setVolume(0.75f);
				backgroundMusic.play();
			} else {
				backgroundMusic.stop();
			}


			nivel = nivelStatic;
			//reseteamos los puntos
			if (nivel == 0){
				points = 0;
			}

			//calculamos el número de aliens que disparan
			//salen a partir del cuarto nivel y se añade uno cada 3 niveles
			if (nivel == 4 || nivel>4 && (nivel-4) %3 ==0 ){
				numeroAlienShooters +=1;
			}

			listaAliens = new ArrayList<>();
			listaAlienShooter = new ArrayList<>();
			listaLaserAlien= new ArrayList<>();
			listaLaser = new ArrayList<>();

			//cargamos el fondo lo primero para que salga detras
			//alteran según nivel

			if (nivel > 1 && nivel%5 == 0) {
				otrofondo = !otrofondo;
			}
			if (otrofondo) {
				stage.addActor(fondo2);
				fondo.remove();
			}else {
				stage.addActor(fondo);
				fondo2.remove();
			}



			// Crea al jugador y lo pone en su posición inicial
			//como la escena está en metros hay que calcular el valor central de la pantalla usando la constante de conversión
			player = factory.createPlayer(world, new Vector2(Constantes.ANCHO_PANTALLA/(Constantes.PIXEL_A_METRO*2), 1f));

			//Crea aliens
			//el número de aliens aumenta cada nivel, pero el maximo depende de los aliens que disparan.
			for (int j= 0; j< MathUtils.random(3+nivel,6+nivel)-numeroAlienShooters; j++){
				listaAliens.add(factory.createAlien(world,(Constantes.ANCHO_PANTALLA* MathUtils.random(0.5f,1.5f)/(Constantes.PIXEL_A_METRO*2)),
					(Constantes.ALTO_PANTALLA*MathUtils.random(1f,1.6f)/(Constantes.PIXEL_A_METRO*2))));
				listaAliens.get(j).setName(j);
				//cada 3 niveles se sube la velocidad de los aliens
				if (nivel > 0 && nivel % 3== 0) {
					listaAliens.get(j).increaseAlienSpeed();
				}
				//cada 2 niveles se sube un poco la altura a la que aparecen para que haya más diversidad y sea más facil
				if (nivel >0 && nivel % 4 == 0){
					listaAliens.get(j).setPosition(listaAliens.get(j).getX(), (listaAliens.get(j).getY())*1.1f);
				}
				// se añaden los aliens a la escena
				stage.addActor(listaAliens.get(j));
			}

			//creamos el segundo tipo de aliens, estos disparan
			for (int j=0; j < numeroAlienShooters; j++){
				listaAlienShooter.add(factory.createAlienShooter(world,(Constantes.ANCHO_PANTALLA* MathUtils.random(0.5f,1.5f)/(Constantes.PIXEL_A_METRO*2)),
						(Constantes.ALTO_PANTALLA*MathUtils.random(1.7f,2f)/(Constantes.PIXEL_A_METRO*2))));
				listaAlienShooter.get(j).setName(j);
				//se añaden a la escena
				stage.addActor(listaAlienShooter.get(j));
			}


			stage.addActor(player);
			stage.addActor(tablaPuntos);
			stage.addActor(tablaNivel);

			//se coloca la camara
			stage.getCamera().position.set(Constantes.ANCHO_PANTALLA/2, Constantes.ALTO_PANTALLA/2,0);
			stage.getCamera().update();

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
			for (AlienShooterEntity alien : listaAlienShooter)
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

			//aquí hacemos que cuando el jugador pulsa el botón la nave dispare
			//el 62 es el código de la barra espacio
			if (Gdx.input.isKeyJustPressed(62)){
				disparalaser();
			}
			//para disparar se tiene en cuenta la segunda pulsación en los androids
			if (Gdx.input.isTouched(1) ){
				if (Gdx.input.justTouched()){
					disparalaser();
				}
			}
			//actualizamos los puntos
			if (player.isAlive()){
				puntos.setText(points.toString());
				nivelTexto.setText(nivel.toString());
			}
			//el jugador muere, se va a GAME OVER
			if (!player.isAlive()){
				//se para la música si mueres
				backgroundMusic.stop();
				//paramos los aliens
				stopAliens();
				numeroAlienShooters = 0;
				//pasamos los puntos para que se puedan guardar
				if (FileManager.getUserData()!= null) {
					FileManager.getUserData().setPuntosObtenidos(points);
					FileManager.getUserData().setNivelAlcanzado(nivel);
					}

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

			//disparos de los aliens
			if(!listaAlienShooter.isEmpty()){
				timer = timer + delta;
				if (timer > 0.5f) {
					if (listaLaserAlien.size()-1 < (nivel - 3)) {
						if (listaAlienShooter.size() == 1) {
							//posicion del laser depende del alien que dispara
							Vector2 posicionLaser = new Vector2(listaAlienShooter.get(0).getAlienPosition());
							listaLaserAlien.add(factory.createLaserAlien(world, posicionLaser));
						} else {
							//si hay más de un alien se elige al azar
							int random = MathUtils.random(0, listaAlienShooter.size() - 1);
							Vector2 posicionLaser = new Vector2(listaAlienShooter.get(random).getAlienPosition());
							listaLaserAlien.add(factory.createLaserAlien(world, posicionLaser));
						}
						if (FileManager.getUserData().isSonido()) {
							laserAlien.play();
						}
					}
					//comprobamos si deben desaparecer
					for (int i = 0; i < listaLaserAlien.size(); i++) {
						stage.addActor(listaLaserAlien.get(i));
						listaLaserAlien.get(i).vidaLaser();
						//se quitan de la lista para que se puedan disparar más
						if (listaLaserAlien.get(i) != null && !listaLaserAlien.get(i).isAlive()) {
							listaLaserAlien.remove(i);
							i--;
						}
					}
					timer =- 0.5f;
				}

			}

			//cambio de nivel
			if (player.isAlive() && nuevaFase){
				backgroundMusic.pause();
				if (FileManager.getUserData().isSonido()) {
					nuevoNivel.play();
				}
				nuevaFase = false;

				//cada nivel sube la posibilidad de más enemigos en la pantalla
				nivelStatic += 1;
				stage.addAction(Actions.sequence(
						//esperamos un segundo para cambiar de pantalla
						Actions.delay(2f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								//cambiamos de pantalla
								game.setScreen(game.gameScreen);
								if (FileManager.getUserData().isMusica()) {
									backgroundMusic.play();
								}
							}
						})
				));
			}

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
		 * método para dispara el lasser
		 */
		private void disparalaser(){
			if (player.isAlive() && listaLaser.size() < 5) {
				Vector2 posicionLaser = new Vector2(player.getPlayerPosition().x,
						player.getPlayerPosition().y/*+player.getHeight()/Constantes.PIXEL_A_METRO*/);
				listaLaser.add(factory.createLaser(world, posicionLaser));
				//sonido de disparo
				if (FileManager.getUserData().isSonido()) {
					laserDefensor.play();
				}
			}
			//se comprueba que los lasers desaparecen solos al salir de la pantalla
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
		 * método para parar todos los aliens
		 */
		private void stopAliens(){
			for (int i= 0; i < listaAliens.size();i++)
				listaAliens.get(i).setStop(true);
			if (!listaAlienShooter.isEmpty()){
			for (int i= 0; i < listaAlienShooter.size();i++)
				listaAlienShooter.get(i).setStop(true);}
			if (!listaLaserAlien.isEmpty()){
			for (int i= 0; i < listaLaserAlien.size();i++)
				listaLaserAlien.get(i).setStop(true);}
		}

	public static void setNivelStatic(int nivelStatic) {
		GameScreen.nivelStatic = nivelStatic;
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
				//colision de laser enviado por el jugador contra un alien normal
				for (int i = 0; i < listaLaser.size(); i++){
					listaLaser.get(i).setName(i);
					for (int j = 0; j < listaAliens.size();j++){
						if (hayContacto(contact, listaLaser.get(i).getName(), listaAliens.get(j).getName())){
							//muere el laser
							listaLaser.get(i).setAlive(false);
							listaLaser.get(i).cambiaGrupo();
							listaLaser.get(i).remove();
							//muere el alien y se quita de la lista
							listaAliens.get(j).setAlive(false);
							listaAliens.remove(j);
							//efecto de sonido
							if (FileManager.getUserData().isSonido()) {
								expCorta.play();
							}
							//sumamos puntos
							points+= 100;
							//renombramos los aliens para que no de error por nulo
							for (int x = 0; x < listaAliens.size(); x++) {
								listaAliens.get(x).setName(x);
							}
							if (listaAliens.isEmpty() && listaAlienShooter.isEmpty()){
								nuevaFase = true;
							}
							break;
						}
					}
				}
				//colision de laser enviado por el jugador contra un alien shooter
				for (int i = 0; i < listaLaser.size(); i++) {
					listaLaser.get(i).setName(i);
					for (int j = 0; j < listaAlienShooter.size(); j++) {
						if (hayContacto(contact, listaLaser.get(i).getName(), listaAlienShooter.get(j).getName())) {
							//muere el laser
							listaLaser.get(i).setAlive(false);
							listaLaser.get(i).cambiaGrupo();
							listaLaser.get(i).remove();
							//muere el alien y se quita de la lista
							listaAlienShooter.get(j).setAlive(false);
							listaAlienShooter.remove(j);
							//efecto de sonido
							if (FileManager.getUserData().isSonido()) {
								expCorta.play();
							}
							//sumamos puntos
							points += 150;
							//renombramos los aliens para que no de error por nulo
							for (int x = 0; x < listaAlienShooter.size(); x++) {
								listaAlienShooter.get(x).setName(x);
							}
							if (listaAliens.isEmpty() && listaAlienShooter.isEmpty()) {
								nuevaFase = true;
							}
							break;
						}
					}
				}
					//colision de laser enviado por el jugador contra un laser alien
					for (int i = 0; i < listaLaser.size(); i++){
						listaLaser.get(i).setName(i);
						for (int j = 0; j < listaLaserAlien.size();j++){
							if (hayContacto(contact, listaLaser.get(i).getName(), listaLaserAlien.get(j).getName())){
								//muere los lasers
								listaLaserAlien.get(j).setAlive(false);
								listaLaserAlien.get(j).cambiaGrupo();
								listaLaserAlien.remove(j);
								listaLaser.get(i).setAlive(false);
								listaLaser.get(i).cambiaGrupo();
								listaLaser.get(i).remove();
								//efecto de sonido
								if (FileManager.getUserData().isSonido()) {
									expCorta.play();
								}								//renombramos los lasers para que no de error por nulo
								for (int x = 0; x < listaLaserAlien.size(); x++) {
									listaLaserAlien.get(x).setName(x);
								}
								//sumamos puntos
								points+= 10;

								break;
							}
						}
					}

				//colisión de la nave del jugador con un alien normal
				for (int i = 0; i < listaAliens.size();i++){
					if(hayContacto(contact, "player", listaAliens.get(i).getName())) {
						player.setAlive(false);
						player.stopPlayer();
						if (FileManager.getUserData().isSonido()) {
							expLarga.play();
						}
					}
				}

				//colisión de la nave del jugador con un alien laser
				for (int i = 0; i < listaLaserAlien.size();i++){
					if(hayContacto(contact, "player", listaLaserAlien.get(i).getName())) {
						player.setAlive(false);
						player.stopPlayer();
						if (FileManager.getUserData().isSonido()) {
							expLarga.play();
						}
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

}
