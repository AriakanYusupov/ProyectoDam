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

public class RegistroScreen extends BaseScreen{

	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private Skin skin;

	//interfaz de usuario
	//botón
	private TextButton salvar, volver;
	//textfield
	private TextField usuario, password1, password2;
	//



	/**
	 * Crea la pantalla de logarse
	 * @param game
	 */
	public RegistroScreen(final MainGame game) {
		super(game);
		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		//creamos el boton. el primer parametro es el texto a mostrar, el segundo la skin a usar
		salvar = new TextButton("Salvar", skin);
		volver = new TextButton("Volver", skin);

		//creamos los textFields
		usuario= new TextField("Usuario", skin);
		usuario.setMaxLength(15);

		password1 = new TextField("Password", skin);
		password1.setPasswordMode(true);
		password1.setMaxLength(12);

		password2 = new TextField("Password", skin);
		password2.setPasswordMode(true);
		password2.setMaxLength(12);

		// se añaden los capturadores de eventos.
		//botón entrar
		salvar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.menuScreen);
			}
		});

		// se añaden los capturadores de eventos.
		volver.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				game.setScreen(game.logInScreen);
			}
		});


		// tamaño y posicion de los botones
		// el origen de coordenadas 0, 0 es la esquina inferior izquierda

		usuario.setSize(300, 40);
		usuario.setPosition(Constantes.ANCHO_PANTALLA/2-150,Constantes.ALTO_PANTALLA/2+80);
		password1.setSize(300, 40);
		password1.setPosition(Constantes.ANCHO_PANTALLA/2-150,Constantes.ALTO_PANTALLA/2);
		password2.setSize(300, 40);
		password2.setPosition(Constantes.ANCHO_PANTALLA/2-150,Constantes.ALTO_PANTALLA/2-80);
		volver.setSize(100,40);
		volver.setPosition(Constantes.ANCHO_PANTALLA/2-50,Constantes.ALTO_PANTALLA/2-250);
		salvar.setSize(100, 40);
		salvar.setPosition(Constantes.ANCHO_PANTALLA/2-50,Constantes.ALTO_PANTALLA/2-150);

		// se añaden los actores al stage para que se vean
		stage.addActor(usuario);
		stage.addActor(password1);
		stage.addActor(password2);
		stage.addActor(salvar);
		stage.addActor(volver);
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
