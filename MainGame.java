package com.spacegame;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

	@Override
	public void create (){
	setScreen(new Box2DPantalla(this));
	}

}
