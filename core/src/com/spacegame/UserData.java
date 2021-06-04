package com.spacegame;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {

	private static final int LISTA_PUNTOS = 15;

	private String nombre;

	private int actualScore;
	private Date fechaActual;
	private int[]maxScores;
	private Date[] fechasMaxScores;

	public UserData(){
		for (int i = 0; i < LISTA_PUNTOS;  i++) {
			maxScores[i] = 0;
			fechasMaxScores[1] = null;
		}
	}

	public boolean isMaxScore(int score){
		return score > maxScores[LISTA_PUNTOS-1];
	}

	/**
	 * método para incluir una nueva máxima puntuación a la lista
	 * quitando la puntuación más baja
	 * @param score int puntuación obtenida
	 * @param fechaActual Date Fecha de la puntuación
	 */
	public void cambiarListaPuntos(int score, Date fechaActual){
		if (isMaxScore(actualScore)){
			maxScores[LISTA_PUNTOS-1]= score;
			fechasMaxScores[LISTA_PUNTOS-1]= fechaActual;
			ordenarPuntuaciones();
		}
	}

	/**
	 * método para odenar los arrays de más a menos
	 * se usa el Bubble Sort Method
	 */
	public void ordenarPuntuaciones(){
		for (int i = 0; i < LISTA_PUNTOS; i++){
			int puntuacion = maxScores[i];
			Date fecha = fechasMaxScores[i];
			int j;
			for (j = i-1; j >= 0 && maxScores[j] < puntuacion; j++) {
				maxScores[j + 1] = maxScores[j];
				fechasMaxScores[j + 1] = fechasMaxScores[j];
			}
			maxScores[j+1] = puntuacion;
			fechasMaxScores[j+1] = fecha;
		}
	}


	public int[] getMaxScores() {
		return maxScores;
	}

	public void setMaxScores(int[] maxScores) {
		this.maxScores = maxScores;
	}

	public Date[] getFechasMaxScores() {
		return fechasMaxScores;
	}

	public void setFechasMaxScores(Date[] fechasMaxScores) {
		this.fechasMaxScores = fechasMaxScores;
	}

	public int getActualScore() {
		return actualScore;
	}

	public void setActualScore(int actualScore) {
		this.actualScore = actualScore;
	}
}
