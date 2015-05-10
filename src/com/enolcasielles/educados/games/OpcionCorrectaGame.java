package com.enolcasielles.educados.games;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Rect;

import com.enolcasielles.educados.games.objetosgame.GameObjeto;
import com.enolcasielles.educados.games.objetosgame.OpcionObjeto;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Implementa la logica de los juegos de tipo 'Opcion Correcta'. 
 * @author Enol Casielles
 *
 */
public class OpcionCorrectaGame extends Game {
	
	//Consntantes
	private final String TAG_ENUNCIADO = "enunciado";
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_OPCIONCORRECTA = "opcionCorrecta";
	
	private final String TAG_JUEGOOPCIONCORRECTA = "juegoOpcionCorrecta";
	private final String TAG_ATRIBUTO_XENUNCIADO = "xEnunciado";
	private final String TAG_ATRIBUTO_YENUNCIADO = "yEnunciado";
	private final String TAG_ATRIBUTO_ANCHOENUNCIADO = "anchoEnunciado";
	private final String TAG_ATRIBUTO_ALTOENUNCIADO = "altoEnunciado";
	private final String TAG_ATRIBUTO_XOPCIONES = "xOpciones";
	private final String TAG_ATRIBUTO_YOPCIONES = "yOpciones";
	private final String TAG_ATRIBUTO_ANCHOOPCIONES = "anchoOpciones";
	private final String TAG_ATRIBUTO_ALTOOPCIONES = "altoOpciones";
	private final String TAG_ATRIBUTO_SEPARACIONVERTICAL = "separacionVertical";
	
	private final String TAG_OPCION = "opcion";
	
	
	private GameObjeto enunciado;
	private ArrayList<OpcionObjeto> opciones;

	
	/**
	 * Contructor. 
	 * @param parser  El objeto que almacena la estructura de datos del nivel
	 * @param scene  La escena en la que se ha de formar este juego
	 * @param espacio  El espacion dentro de la escena en la que se ha de formar el juego
	 */
	public OpcionCorrectaGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio ) {
		super(parser, "juegoOpcionCorrecta", scene, espacio);
	}

	
	@Override
	public void dispose() {
		enunciado.dispose();
		for (OpcionObjeto objeto : opciones) {
			objeto.dispose();
		}
	}
	
	@Override
	public void finalizar() {
		enunciado.finaliza();
		for (OpcionObjeto objeto : opciones) {
			objeto.finaliza();
		}
	}
	
	
	@Override
	public boolean puedeFinalizar() {
		if (!enunciado.estaDestruido()) return false;
		for (OpcionObjeto objeto : opciones) {
			if (!objeto.estaDestruido()) return false;
		}
		return true;
	}
	
	
	@Override
	protected void iniciaObjetos() {
		
		scene.setPuntuacion(puntuacion);

		//Recupero los datos necesairo para formar este juego
		
		//Datos comunes al juego
		HashMap<String, String> juegoAtributos = parser.getElementos(TAG_JUEGOOPCIONCORRECTA).get(0);
		float xEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_XENUNCIADO));
		float yEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_YENUNCIADO));
		float anchoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOENUNCIADO));
		float altoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOENUNCIADO));
		float xOpciones = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_XOPCIONES));
		float yOpciones = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_YOPCIONES));
		float anchoOpciones = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOOPCIONES));
		float altoOpciones = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOOPCIONES));
		float separacionVertical = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_SEPARACIONVERTICAL));
		
		//Datos del enuciado
		HashMap<String, String> enunciadoAtributos = parser.getElementos(TAG_ENUNCIADO).get(0);
		String idOpcionCorrecta = enunciadoAtributos.get(TAG_ATRIBUTO_OPCIONCORRECTA);
		
		//Objeto para el enunciado
		String id = enunciadoAtributos.get(TAG_ATRIBUTO_ID);
		enunciado = new GameObjeto(xEnunciado, yEnunciado, anchoEnunciado, altoEnunciado, 
				EvaluacionScene.getTextura(id), scene, id);
		
		//Datos para las opciones
		ArrayList<HashMap<String, String>> opcionesElementos = parser.getElementos(TAG_OPCION);
		
		//Objetos para las opciones
		opciones = new ArrayList<OpcionObjeto>();
		float yActual = yOpciones;
		for (HashMap<String, String> opcionAtributos : opcionesElementos) {
			id = opcionAtributos.get(TAG_ATRIBUTO_ID);
			boolean correcta = id.equals(idOpcionCorrecta);
			OpcionObjeto opcion = new OpcionObjeto(xOpciones, yActual, anchoOpciones, altoOpciones, 
						EvaluacionScene.getTextura(id), scene, id, correcta,this);
			opciones.add(opcion);
			//Actualizo la posicon para el siguiente elemento
			yActual += (altoOpciones + separacionVertical);
		}
		
	}
	
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en la opcion correcta
	 */
	public void respuestaCorrecta() {
		scene.juegoFinalizado(puntuacionMax-puntuacion);
	}
	
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en una respuesta no correcta
	 */
	public void respuestaIncorrecta() {
		puntuacion-=puntosFalla;
		scene.setPuntuacion(puntuacion);
		if (puntuacion <= 0) scene.partidaFinalizada();
	}
}
