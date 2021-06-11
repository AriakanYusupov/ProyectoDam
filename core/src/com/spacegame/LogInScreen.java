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

/**
 * Clase para la pantalla de LogIn
 */
public class LogInScreen extends BaseScreen {
	//instancia Stage de Scene2D
	private Stage stage;

	//Skin para los botones
	private final Skin skin;

	//interfaz de usuario
	//botón
	private TextButton entrar, registrar;
	//textfield
	private TextField usuarioNombre, password;
	//imagebuton invisible
	private ImageButton invisible;

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

		//tabla para recoger los botones
		Table tabla = new Table();
		tabla.setFillParent(true);

		//boton para quitar focus a los demás actores
		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		style.up = null;
		style.down = null;
		invisible = new ImageButton(style);

		//creamos el boton. el primer parametro es el texto a mostrar, el segundo la skin a usar
		entrar = new TextButton("Entrar", skin);
		registrar = new TextButton("Registrar nuevo usuario", skin);

		//creamos los textFields
		usuarioNombre = new TextField("Usuario", skin);
		usuarioNombre.setMaxLength(30);

		password = new TextField("Password", skin);
		password.setMaxLength(30);

		//se añaden los elementos a la tabla
		tabla.add(usuarioNombre).size(300,80).pad(10);
		tabla.row();
		tabla.add(password).size(300,80).pad(10);
		tabla.row();
		tabla.add(entrar).size(300,80).pad(10);
		tabla.row();
		tabla.add(registrar).size(300,80).pad(10);
		tabla.row();

		//se apilan los elementos para que ocupen el mismo espacio
		Stack apilar = new Stack();
		apilar.addActor(invisible);
		apilar.addActor(tabla);
		apilar.setBounds(0,0,Constantes.ANCHO_PANTALLA, Constantes.ALTO_PANTALLA);

		// se añaden los capturadores de eventos.
		//botón entrar al menú del juego
		entrar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//lleva a la pantalla de juego
				FileManager.cargarUserList();
				if (FileManager.recuperarUserPassWord(usuarioNombre.getText(), password.getText())){
					if (!FileManager.existeFile(FileManager.setNombreFicheroUser(usuarioNombre.getText()))) {
						FileManager.inicioUser(usuarioNombre.getText());
						System.out.println(FileManager.getUserData().getNombreUsuario());
					} else{
						FileManager.cargarUserData(FileManager.setNombreFicheroUser(usuarioNombre.getText()));
					}
					game.setScreen(game.menuScreen);
				} else {
					Dialog ventana = new Dialog("Error", skin);
					ventana.text("Usurio o Password incorrecto").pad(40);
					ventana.button("Aceptar").pad(20);
					ventana.show(stage);
				}
			}
		});

		//botón para ir a la pantalla de registro de usuarios
		registrar.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				usuarioNombre.setText("Usuario");
				password.setText("Password");
				password.setPasswordMode(false);
				stage.unfocusAll();
				//lleva a la pantalla de juego
				game.setScreen(game.registroScreen);
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
		if (usuarioNombre.hasKeyboardFocus()){
			if("Usuario".equals(usuarioNombre.getText())){
				usuarioNombre.setText("");
			}
		}

		//como son passwords no se puede ver lo que se escribe
		if (password.hasKeyboardFocus()){
			if("Password".equals(password.getText())) {
				password.setText("");
			}
			password.setPasswordCharacter('*');
			password.setPasswordMode(true);
		}


		stage.act();
		stage.draw();
	}

}
