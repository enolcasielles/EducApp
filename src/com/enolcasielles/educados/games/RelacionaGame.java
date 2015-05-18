package com.enolcasielles.educados.games;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import android.graphics.Rect;

import com.enolcasielles.educados.games.objetosgame.RelacionaObjeto;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Clase que define la logica de los juegos de tipo 'Relaciona', que son aquellos juegos en los que el usuario
 * ha de relacionar las definidiones o pregntas de la izquierda con las de la derecha o viceversa.
 * @author Enol Casielles
 *
 */
public class RelacionaGame extends Game implements IOnSceneTouchListener {
	
	//CONSTANTES
	private final String TAG_IZQUIERDA = "izquierda";
	private final String TAG_DERECHA = "derecha";
	private final String TAG_JUEGO_RELACIONA = "juegoRelaciona";
	private final String TAG_ATRIBUTO_XINI = "xIni";
	private final String TAG_ATRIBUTO_YINI = "yIni";
	private final String TAG_ATRIBUTO_ANCHO = "ancho";
	private final String TAG_ATRIBUTO_ALTO = "alto";
	private final String TAG_ATRIBUTO_SEPARACIONVERTICAL = "separacionVertical";
	private final String TAG_ATRIBUTO_SEPARACIONHOR = "separacionHor";
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_RESPUESTA = "respuesta";
	
	
	//Variables para almacenar y controlar los objetos
	private ArrayList<RelacionaObjeto> objetos;
	private RelacionaObjeto objetoSeleccionado;
	
	//Variable para dibujar la linea de union
	private Line linea;
	private float xIni, yIni;
	
	
	/**
	 * Constructor
	 * @param parser La escturctura de datos del nivel 
	 * @param scene  La escena en la que se ha de formar el juego
	 * @param espacio  El espacio dentre de la escena
	 */
	public RelacionaGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio) {
		super(parser,"juegoRelaciona",scene,espacio);
	}
	
	
	@Override
	public void dispose() {
		for (RelacionaObjeto objeto : objetos) {
			objeto.dispose();
		}
		objetos.clear();
		linea.dispose();
		scene.setOnSceneTouchListener(null);
	}
	
	
	@Override
	public void finalizar() {
		for (RelacionaObjeto objeto : objetos) {
			objeto.finaliza();
		}
	}
	
	@Override
	public boolean puedeFinalizar() {
		for (RelacionaObjeto objeto : objetos) {
			if(!objeto.estaDestruido()) return false;
		}
		return true;
	}
	
	@Override
	protected void iniciaObjetos() {
		
		scene.setPuntuacion(puntuacion);
		
		//Recuèrp los elementos necesarios
		ArrayList<HashMap<String, String>> izquierdaElementos = parser.getElementos(TAG_IZQUIERDA);
		ArrayList<HashMap<String, String>> derechaElementos = parser.getElementos(TAG_DERECHA);
		HashMap<String, String> juegoRelacionaAtributos = parser.getElementos(TAG_JUEGO_RELACIONA).get(0);
		
		//Defino los objetos del juego a partir de los datos anteriores
		float xIni = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_XINI));
		float yIni = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_YINI));
		float ancho = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_ANCHO));
		float alto = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_ALTO));
		float sepVer = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_SEPARACIONVERTICAL));
		float sepHor = Float.parseFloat(juegoRelacionaAtributos.get(TAG_ATRIBUTO_SEPARACIONHOR));
		
		objetos = new ArrayList<RelacionaObjeto>();
		
		float yActual = yIni;
		for (HashMap<String, String> izquierdaAtributos : izquierdaElementos) {
			objetos.add(new RelacionaObjeto(xIni+espacio.left, yActual+espacio.top, ancho, alto, EvaluacionScene.getTextura(izquierdaAtributos.get(TAG_ATRIBUTO_ID)),
					scene, izquierdaAtributos.get(TAG_ATRIBUTO_ID),true, izquierdaAtributos.get(TAG_ATRIBUTO_RESPUESTA)));
			yActual+=(alto+sepVer);
		}
		
		xIni+=(ancho+sepHor);
		yActual = yIni;
		for (HashMap<String, String> derechaAtributos : derechaElementos) {
			objetos.add(new RelacionaObjeto(xIni+espacio.left, yActual+espacio.top, ancho, alto, EvaluacionScene.getTextura(derechaAtributos.get(TAG_ATRIBUTO_ID)),
					scene, derechaAtributos.get(TAG_ATRIBUTO_ID),false, ""));
			yActual+=(alto+sepVer);
		}
		
		
		//Inicio la linea
		linea = new Line(0, 0, 0, 0, scene.vbom);
		linea.setColor(Color.RED);
		linea.setVisible(false);
		scene.attachChild(linea);
		
		scene.setOnSceneTouchListener(this);

		
	}
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		
		if (pSceneTouchEvent.isActionDown()) {
			//Compruebo si esta dentro del contenido
			float x = pSceneTouchEvent.getX();
			float y = pSceneTouchEvent.getY();
			if(espacio.contains((int)x, (int)y)) {
				//Ahora compruebo si se ha pulsado sobre algun objeto
				for (RelacionaObjeto objeto : objetos) {
					if (objeto.contains(x, y) && !objeto.getMarcado()) {
						objetoSeleccionado = objeto;
						xIni = pSceneTouchEvent.getX();
						yIni = pSceneTouchEvent.getY();
						linea.setPosition(xIni, yIni, xIni, yIni);
						linea.setVisible(true);
						return true;
					}
				}
			}
		}
		
		if (pSceneTouchEvent.isActionUp()) {
			if (objetoSeleccionado != null) {
				//Compruebo si se ha soltado en un objeto que no estuviese marcado
				for (RelacionaObjeto objeto : objetos) {
					if (!objeto.getMarcado() && objeto.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
						//Compruebo si pertenece al lador contrario
						if (objeto.getIzquierda() != objetoSeleccionado.getIzquierda()) {
							//Compurebo que esten relacionados
							if (checkRelacionObjetos(objeto, objetoSeleccionado)) {
								//El usuario ha acertado. Marco ambos objetos como finalizados
								objeto.setMarcado();
								objetoSeleccionado.setMarcado();
								//Compruebo si ya se ha finalizado con todas
								if (checkJuegoFinalizado()) {
									scene.juegoFinalizado(puntuacionMax-puntuacion);
								}
							}
							else {
								//El usuario no ha acertado. Resto puntuacion
								puntuacion-=puntosFalla;
								scene.setPuntuacion(puntuacion);
								if(puntuacion<=0) scene.partidaFinalizada();
							}
						}
						objetoSeleccionado = null; //Restablezco para preparar siguiente evento
						linea.setVisible(false);
						return true;
					}
				}
				objetoSeleccionado = null; //Restablezco para preparar siguiente evento
				linea.setVisible(false);
			}
		}
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if (objetoSeleccionado != null) {
				linea.setPosition(xIni, yIni, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * Comprueba si los dos objetos que se la pasan estan relacionados entre si.
	 * @param objeto1  El primero objeto a comparar
	 * @param objeto2  El segundo objeto
	 * @return  True si lo estan o false si no.
	 */
	private boolean checkRelacionObjetos(RelacionaObjeto objeto1, RelacionaObjeto objeto2) {
		if (objeto1.getIzquierda()) {  //El objeto 1 es el de la izquierda y por tanto el que almacena con cual se relaciona
			if (objeto1.getIdRelacionado().equals(objeto2.getId())) return true;
			else return false;
		}
		else {  //El objeto 2 sera el de la izquierda y por tanto el que almacena con cual se relaciona
			if (objeto2.getIdRelacionado().equals(objeto1.getId())) return true;
			else return false;
		}
	}
	
	
	/**
	 * Comprueba si todos los objetos estan marcados
	 * @return True si todos lo estan o false si no
	 */
	private boolean checkJuegoFinalizado() {
		for (RelacionaObjeto objeto : objetos) {
			if (!objeto.getMarcado()) return false;
		}
		return true;
	}

}
