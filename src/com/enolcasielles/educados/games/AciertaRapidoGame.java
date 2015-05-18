package com.enolcasielles.educados.games;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.input.touch.TouchEvent;

import android.graphics.Rect;
import android.util.Log;

import com.enolcasielles.educados.games.objetosgame.AciertaRapidoObjetos;
import com.enolcasielles.educados.games.objetosgame.GameObjeto;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Implementa la logica de los juegos de tipo Acierta Rapido
 * @author Enol Casielles
 *
 */
public class AciertaRapidoGame extends Game {
	
	//Constantes
	private final String TAG_DEFINICION = "definicion";
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_OPCIONCORRECTA = "opcionCorrecta";
	
	private final String TAG_JUEGOACIERTARAPIDO = "juegoAciertaRapido";
	private final String TAG_ATRIBUTO_XENUNCIADO = "xEnunciado";
	private final String TAG_ATRIBUTO_YENUNCIADO = "yEnunciado";
	private final String TAG_ATRIBUTO_ANCHOENUNCIADO = "anchoEnunciado";
	private final String TAG_ATRIBUTO_ALTOENUNCIADO = "altoEnunciado";
	private final String TAG_ATRIBUTO_ANCHOOPCIONES = "anchoOpciones";
	private final String TAG_ATRIBUTO_ALTOOPCIONES = "altoOpciones";
	
	private final String TAG_POSIBILIDAD = "posibilidad";
	
	
	//Objetos para la definicion del juego
	private GameObjeto definicion;
	private ArrayList<GameObjeto> posibilidades;
	private AciertaRapidoObjetos objetosHandler;

	
	/**
	 * Contructor. 
	 * @param parser  El objeto que almacena la estructura de datos del nivel
	 * @param scene  La escena en la que se ha de formar este juego
	 * @param espacio  El espacion dentro de la escena en la que se ha de formar el juego
	 */
	public AciertaRapidoGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio) {
		super(parser, "juegoAciertaRapido", scene, espacio);
	}

	@Override
	protected void iniciaObjetos() {
		
		scene.setPuntuacion(puntuacion);

		//Recupero los datos necesairo para formar este juego
		
		//Datos comunes al juego
		HashMap<String, String> juegoAtributos = parser.getElementos(TAG_JUEGOACIERTARAPIDO).get(0);
		float xEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_XENUNCIADO));
		float yEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_YENUNCIADO));
		float anchoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOENUNCIADO));
		float altoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOENUNCIADO));
		float anchoPosibilidades = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOOPCIONES));
		float altoPosibilidades = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOOPCIONES));
		
		//Datos de la definicion
		HashMap<String, String> definicionAtributos = parser.getElementos(TAG_DEFINICION).get(0);
		final String idPosibilidadCorrecta = definicionAtributos.get(TAG_ATRIBUTO_OPCIONCORRECTA);
		
		//Objeto para la definicion
		String id = definicionAtributos.get(TAG_ATRIBUTO_ID);
		definicion = new GameObjeto(xEnunciado+espacio.left, yEnunciado+espacio.top, anchoEnunciado, altoEnunciado, 
				EvaluacionScene.getTextura(id), scene, id);
		
		//Datos para las posibilididades
		ArrayList<HashMap<String, String>> posibilidadesElementos = parser.getElementos(TAG_POSIBILIDAD);
		
		//Objetos para las posibilidades
		posibilidades = new ArrayList<GameObjeto>();
		for (HashMap<String, String> posibilidadAtributos : posibilidadesElementos) {
			id = posibilidadAtributos.get(TAG_ATRIBUTO_ID);
			GameObjeto posibilidad = new GameObjeto(0,0, anchoPosibilidades, altoPosibilidades, 
						EvaluacionScene.getTextura(id), scene, id) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (this.isVisible()) {
						if (pSceneTouchEvent.isActionUp()) {
							if (this.id.equals(idPosibilidadCorrecta)) {
								//Ha pinchado en la correcta
								respuestaCorrecta();
							}
							else {
								//Ha pinchado en una incorrecta
								respuestaIncorrecta();
							}
							return true;
						}	
					}
					return false;
				}
			};
			scene.registerTouchArea(posibilidad);
			posibilidades.add(posibilidad);
		}
		
		//Objeto para manejar los objetos
		float yMin = definicion.getY() + definicion.getHeight() + 5;
		float yMax = espacio.bottom - definicion.getHeight()-5;
		float xMin = espacio.left + 5;
		float xMax = espacio.right - definicion.getWidth() - 5;
		objetosHandler = new AciertaRapidoObjetos(posibilidades, scene, xMin, xMax, yMin, yMax);

	}

	@Override
	public void dispose() {
		definicion.dispose();
		for (GameObjeto objeto : posibilidades) {
			scene.unregisterTouchArea(objeto);
			objeto.dispose();
		}
		objetosHandler.dispose();
	}

	@Override
	public void finalizar() {
		definicion.finaliza();
		for (GameObjeto objeto : posibilidades) {
			objeto.finaliza();
		}
	}

	@Override
	public boolean puedeFinalizar() {
		if (!definicion.estaDestruido()) return false;
		for (GameObjeto objeto : posibilidades) {
			if (!objeto.estaDestruido()) return false;
		}
		return true;
	}
	
	
	
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en la opcion correcta
	 */
	private void respuestaCorrecta() {
		scene.juegoFinalizado(puntuacionMax-puntuacion);
	}
	
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en una respuesta no correcta
	 */
	private void respuestaIncorrecta() {
		puntuacion-=puntosFalla;
		scene.setPuntuacion(puntuacion);
		if (puntuacion <= 0) scene.partidaFinalizada();
	}

}
