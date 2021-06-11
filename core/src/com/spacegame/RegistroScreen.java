package com.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class RegistroScreen extends BaseScreen {

	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private final Skin skin;

	//interfaz de usuario
	//botón
	private TextButton salvar, volver;
	//textfield
	private TextField usuario, password1, password2;
	//imagebuton invisible
	private ImageButton invisible;

	private String user= null, pass1= null, pass2= null;


	/**
	 * Crea la pantalla de logarse
	 * @param game instancia del juego
	 */
	public RegistroScreen(final MainGame game) {
		super(game);
		// nuevo escenario
		stage = new Stage(new FitViewport(Constantes.ANCHO_PANTALLA,Constantes.ALTO_PANTALLA));
		// cargamos el fichero con la skin
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

		//creamos el boton. el primer parametro es el texto a mostrar, el segundo la skin a usar
		salvar = new TextButton("Salvar", skin);
		volver = new TextButton("Volver", skin);

		//creamos los textFields
		usuario= new TextField("Usuario", skin);
		usuario.setMaxLength(30);

		password1 = new TextField("Password", skin);
		password1.setMaxLength(30);

		password2 = new TextField("Repetir Password", skin);
		password2.setMaxLength(30);

		//boton para quitar focus a los demás actores
		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		style.up = null;
		style.down = null;
		invisible = new ImageButton(style);

		Table tabla = new Table();
		tabla.setFillParent(true);

		//se apilan los elementos para que ocupen el mismo espacio
		Stack apilar = new Stack();
		apilar.addActor(invisible);
		apilar.addActor(tabla);
		apilar.setBounds(0,0,Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA);


		// se añaden los capturadores de eventos.
		//botón entrar
		salvar.addCaptureListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//error si las claves para el registro no coinciden
				if (!pass1.equals(pass2)){
					Dialog vent = new Dialog("Error", skin);
					vent.text("Las Contraseñas no coinciden").pad(40);
					vent.button("Aceptar").pad(20);
					vent.setScale(1.25f);
					vent.show(stage);

				} else {
					FileManager.cargarUserList();
					if (FileManager.usuarioRepetido(user)){
						Dialog vent = new Dialog("Error", skin);
						vent.text("Usuario repetido").pad(40);
						vent.button("Aceptar").pad(20);
						vent.setScale(1.25f);
						vent.show(stage);
					} else {
						FileManager.incluirNuevoUser(user, pass1);
						FileManager.salvarUserList();

						//lleva a la pantalla de juego
						game.setScreen(game.logInScreen);
					}
				}
			}
		});

		// se añaden los capturadores de eventos.
		volver.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				usuario.setText("Usuario");
				password1.setText("Password");
				password1.setPasswordMode(false);
				password2.setText("Repetir Password");
				password2.setPasswordMode(false);
				stage.unfocusAll();
				//lleva a la pantalla de juego
				game.setScreen(game.logInScreen);
			}
		});

		//botón para quitar foco a lo demás
		invisible.addCaptureListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				stage.unfocusAll();
				Gdx.input.setOnscreenKeyboardVisible(false);
			}
		});


		// tamaño y posicion de los botones
		// el origen de coordenadas 0, 0 es la esquina inferior izquierda
		tabla.add(usuario).colspan(2).size(600, 40);
		tabla.row();
		tabla.add(password1).colspan(2).size(600, 40);
		tabla.row();
		tabla.add(password2).colspan(2).size(600, 40);
		tabla.row();
		tabla.row();
		tabla.add(salvar).size(300,80).pad(10);
		tabla.add(volver).size(300,80).pad(10);

		// se añaden los actores al stage para que se vean

		stage.addActor(apilar);
	}

	/**
	 * método para que muestre la pantalla al entrar
	 */
	@Override
	public void show() {
		// hacemos que el Input Systen maneje el Stage.
		// Stages son procesadores de Inputs, por lo que pueden manejar los botones

		usuario.setText("Usuario");
		password1.setText("Password");
		password2.setText("Repetir Password");

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

		//hacemos que cuando el foco vaya a un textfield, este se borre
		if (usuario.hasKeyboardFocus()){
			if("Usuario".equals(usuario.getText())){
				usuario.setText("");
			}
		}

		//como son passwords no se puede ver lo que se escribe
		if (password1.hasKeyboardFocus()){
			if("Password".equals(password1.getText())) {
				password1.setText("");
			}
			password1.setPasswordCharacter('*');
			password1.setPasswordMode(true);
		}
		if (password2.hasKeyboardFocus()){
			if( "Repetir Password".equals(password2.getText())){
				password2.setText("");
			}
			password2.setPasswordCharacter('*');
			password2.setPasswordMode(true);
		}

		if (!usuario.hasKeyboardFocus() && !password1.hasKeyboardFocus() && !password2.hasKeyboardFocus()){
			Gdx.input.setOnscreenKeyboardVisible(false);
		}

		user = usuario.getText();
		pass1 = password1.getText();
		pass2 = password2.getText();

		stage.act();
		stage.draw();
	}


}
