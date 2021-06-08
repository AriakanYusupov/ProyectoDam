package com.spacegame;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {

	//número de elementos a guardar en la lista de máximas puntuaciones
	private int LISTA_PUNTOS = Constantes.LISTADO_PUNTUACION_MAXIMA;
	private String nombreUsuario;

	private int puntosObtenidos= 0, nivelAlcanzado= 0;
	private Date fechaActual;
	private int[] puntosMax = new int[LISTA_PUNTOS];
	private int[] nivelMax = new int[LISTA_PUNTOS];
	private Date[] fechasPuntosMax = new Date[LISTA_PUNTOS];

	/**
	 * constructor
 	 * @param nombreUsuario String
	 */
	public UserData(String nombreUsuario){
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * crea las arrays la primera vez
	 */
	public void inciar(){
		for (int i = 0; i < LISTA_PUNTOS;  i++) {
			puntosMax[i] = 0;
			nivelMax[i] = 0;
			Date fechainit = new Date(0L);
			fechasPuntosMax[i] = fechainit;

		}
	}

	/**
	 * comprueba si la puntuación obtenida debe ser introducida en los arrays
	 * @param puntos int Puntos obtenidos
	 * @return boolean
	 */
	public boolean isMaxScore(int puntos){
		return puntos > puntosMax[LISTA_PUNTOS-1];
	}

	/**
	 * método para incluir una nueva máxima puntuación a la lista
	 * quitando la puntuación más baja
	 * @param score int puntuación obtenida
	 */
	public void cambiarListaPuntos(int score, int nivel){
		if (isMaxScore(puntosObtenidos)){
			long millis=System.currentTimeMillis();
			fechaActual = new Date(millis);
			puntosMax[LISTA_PUNTOS-1]= score;
			nivelMax[LISTA_PUNTOS-1]= nivel;
			fechasPuntosMax[LISTA_PUNTOS-1]= fechaActual;
			ordenarPuntuaciones();
		}
	}

	/**
	 * método para odenar los arrays de más a menos
	 * se usa el Bubble Sort Method
	 */
	public void ordenarPuntuaciones(){
		for (int i = 0; i < LISTA_PUNTOS; i++){
			int puntuacion = puntosMax[i];
			int nivel = nivelMax[i];
			Date fecha = fechasPuntosMax[i];
			int j;
			for (j = i-1; j >= 0 && puntosMax[j] < puntuacion; j--) {
				puntosMax[j + 1] = puntosMax[j];
				nivelMax[j + 1] = nivelMax[j];
				fechasPuntosMax[j + 1] = fechasPuntosMax[j];
			}
			puntosMax[j+1] = puntuacion;
			nivelMax[j+1]= nivel;
			fechasPuntosMax[j+1] = fecha;
		}
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int[] getPuntosMax() {
		return puntosMax;
	}

	public Date[] getFechasPuntosMax() {
		return fechasPuntosMax;
	}

	public int[] getNivelMax (){
		return nivelMax;
	}

	public void setPuntosMax(int[] puntosMax) {
		this.puntosMax = puntosMax;
	}

	public int getPuntosObtenidos() {
		return puntosObtenidos;
	}

	public void setPuntosObtenidos(int puntosObtenidos) {
		this.puntosObtenidos = puntosObtenidos;
	}

	public String getPuntuacion(Integer i){
		return i.toString();
	}

	public int getNivelAlcanzado() {
		return nivelAlcanzado;
	}

	public void setNivelAlcanzado(int nivelAlcanzado) {
		this.nivelAlcanzado = nivelAlcanzado;
	}

	public String getNivel(Integer i){
		return i.toString();
	}
}
