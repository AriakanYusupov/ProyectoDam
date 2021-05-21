package com.spacegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase para la pantalla de LogIn
 */
public class LogInScreen extends BaseScreen {
	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private Skin skin;

	//interfaz de usuario
	//botón
	private TextButton entrar, registrar;
	//textfield
	private TextField usuario, password;
	//



	/**
	 * Crea la pantalla de logarse
	 * @param game juego
	 */
	public LogInScreen(final MainGame game) {
		super(game);
		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//creamos el boton. el primer parametro es el texto a mostrar, el segundo la skin a usar
		entrar = new TextButton("Entrar", skin);
		registrar = new TextButton("Registrar nuevo usuario", skin);

		//creamos los textFields
		usuario= new TextField("Usuario", skin);
		usuario.setMaxLength(15);


		password = new TextField("Password", skin);
		password.setPasswordMode(true);
		password.setMaxLength(12);

		// se añaden los capturadores de eventos.
		//botón entrar
		entrar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.menuScreen);
			}
		});

		// se añaden los capturadores de eventos.
		registrar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.registroScreen);
			}
		});


		// tamaño y posicion de los botones
		// el origen de coordenadas 0, 0 es la esquina inferior izquierda
		entrar.setSize(100, 40);
		entrar.setPosition(Constantes.ANCHO_PANTALLA/2-50,Constantes.ALTO_PANTALLA/2-150);
		usuario.setSize(300, 40);
		usuario.setPosition(Constantes.ANCHO_PANTALLA/2-150,Constantes.ALTO_PANTALLA/2+80);
		password.setSize(300, 40);
		password.setPosition(Constantes.ANCHO_PANTALLA/2-150,Constantes.ALTO_PANTALLA/2);
		registrar.setSize(100,40);
		registrar.setPosition(Constantes.ANCHO_PANTALLA/2-50,Constantes.ALTO_PANTALLA/2-250);

		// se añaden los actores al stage para que se vean
		stage.addActor(entrar);
		stage.addActor(usuario);
		stage.addActor(password);
		stage.addActor(registrar);
	}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {
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
