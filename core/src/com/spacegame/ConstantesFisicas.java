package com.spacegame;

/**
 * Class para manejar mascaras de fisicas para evitar colisiones indeseadas
 */
public class ConstantesFisicas {

	//Categorias
    public static final short CAT_PLAYER = 1;
	public static final short CAT_ALIEN = 2;
	public static final short CAT_LASER_PLAYER = 4;
	public static final short CAT_LASER_ALIEN = 8;

	//mascaras de colisión
	public static final short MASK_PLAYER = CAT_ALIEN | CAT_LASER_ALIEN; //el player puede colisionar con alien y laser_alien
	public static final short MASK_ALIEN = CAT_LASER_PLAYER | CAT_PLAYER; //el alien puede colisionar con player y laser_player
	public static final short MASK_LASER_PLAYER = CAT_ALIEN;
	public static final short MASK_LASER_ALIEN = CAT_PLAYER;

}
