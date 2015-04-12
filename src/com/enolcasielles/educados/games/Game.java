package com.enolcasielles.educados.games;

import android.graphics.Rect;

import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;

/**
 * Clase abstracta que encapsulara la funcionalidad basica de cualqueir clase Game
 * @author Enol Casielles
 *
 */
public abstract class Game {
	
	private final String TAG_ATRIBUTO_PUNTUACIONMAX = "puntuacionMax";
	private final String TAG_ATRIBUTO_PUNTOSFALLA = "puntosFalla";
	
	
	protected ParseadorXML parser;
	protected String etiqueta;
	protected EvaluacionScene scene;
	protected Rect espacio;
	
	protected int puntuacion;
	protected int puntuacionMax;
	protected int puntosFalla;
	
	/**
	 * Constructor
	 * @param parser El objeto que almacena la estructura de datos
	 * @param etiqueta  El nombre de la etiqueta que en el xml define este juego
	 * @param scene La escena en la que se formara este juego
	 * @param espacio  La zona de la escena en la que se ha de dibujar este juego
	 */
	public Game(ParseadorXML parser, String etiqueta, EvaluacionScene scene, Rect espacio) {
		this.parser = parser;
		this.etiqueta = etiqueta;
		this.scene = scene;
		this.espacio = espacio;
		definePuntuaciones();
		iniciaObjetos();
	}
	
	
	/**
	 * Este metodo deinira la logica en cada uno de los juegos
	 */
	protected abstract void iniciaObjetos();
	
	
	/**
	 * Definira la logica de destruccion del juego
	 */
	public abstract void dispose();
	
	
	
	private void definePuntuaciones() {
		//Por ultimo recupero la puntuacion maxima posible para este uego y los puntos que se restan cuando falla
		puntuacionMax = Integer.parseInt(parser.getElementos(etiqueta).get(0).get(TAG_ATRIBUTO_PUNTUACIONMAX));
		puntosFalla = Integer.parseInt(parser.getElementos(etiqueta).get(0).get(TAG_ATRIBUTO_PUNTOSFALLA));
		puntuacion = puntuacionMax;
	}

}
