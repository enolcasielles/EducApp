package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.jugador.Jugador;


/**
 * Clase que maneja los objetos nivel (NivelObjeto) contenidos en la escena World
 * Sera es clase la que se encargue de reaccionar cuando el usuario toque uno de los objetos
 * 
 * @author Enol Casielles
 *
 */
public class NivelesManager {
	
	private ArrayList<NivelObjeto> objetos;
	private Jugador jugador;
	private BaseScene scene;
	
	
	public NivelesManager(BaseScene scene) {
		//Inicio contendor
		objetos = new ArrayList<NivelObjeto>();
		//Almaceno la escena
		this.scene = scene;
	}
	
	
	/**
	 * Añade un objeto al contenedor
	 * @param o El objeto a añadir
	 */
	public void addNivel(NivelObjeto o) {
		objetos.add(o);
		if (o.getId() == 1) jugador = new Jugador(o, this.scene);   //!!!!CAMBIAR ESTO!!!! NO TIENE POR QUE IR AL PRIMERO
	}

	
	
	/**
	 * Responde ante un evento touch en uno de los objetos.
	 * En funcion de la posicion del jugador respondera de forma distinta
	 * @param objeto
	 */
	public void onTouchIn(NivelObjeto objeto) {
		
		//Si es el objeto en el que se encuentra el jugador entro al nivel
		if (jugador.getId() == objeto.getId()) {
			scene.camera.setZoomFactor(1.0f);   //Devuelvo la camara a su zoom inicial
			SceneManager.getInstance().worldScene_to_gameScene(objeto.getMundo(), jugador.getId());
		}
		
		//Si es el objeto anterior o siguiente muevo el jugador a su posicion
		if (jugador.getId() == objeto.getId() - 1  || jugador.getId() == objeto.getId() + 1 ) {
			jugador.mover(objeto);
		}
	}
	
	
}
