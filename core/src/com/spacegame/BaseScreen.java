/**
 * autor Pablo Ruiz Álvarez
 * proyecto fin de ciclo DAM
 */


package com.spacegame;

import com.badlogic.gdx.Screen;

/**
 * clase abstracta con los métodos que usan las pantallas del juego.
 * Hereda de la clase Screen de LidGDX
 */
public abstract class BaseScreen implements Screen {

 /**
  * instancia de Game. Al hacer el constructor protected
  * todas las pantallas se pueden conectar al juego.
  */
   protected   MainGame game;

   public BaseScreen(MainGame game){
     this.game = game;

 }
   /**
   *este método se usa cuando se entra en la pantalla para mostrar lo necesario
    */
    @Override
    public void show() {

    }
   /**
   *este método es el que dibuja en la pantalla
   *se repita unas muchas veces por minuto
   *@param delta es el tiempo (en segundos) entre frames
    */
    @Override
    public void render(float delta) {

    }
   /**
       * Este método se usa para redimensionar la pantalla
    * @param width int ancho
    * @param height int altura
    */
    @Override
    public void resize(int width, int height) {

    }

    /**
    /método para pausar el juego
     */
    @Override
    public void pause() {

    }

    /**
    *método para volver al juego después de pausarlo
     */
    @Override
    public void resume() {

    }

    /**
    *esté metodo se usa cuando se cambia de pantalla para ocultar los objetos
    */
    @Override
    public void hide() {

    }
    /**
    *este método limpia la memoria de los objetos que ya no se usan
     */
    @Override
    public void dispose() {

    }
}
