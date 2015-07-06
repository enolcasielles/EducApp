package com.enolcasielles.educados.games.objetosgame;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.enolcasielles.educados.Constants;
import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;




/**
 * Clase que maneja un teclado. Se encarga de dibujarlo, y de atender a sus eventos.
 * Contiene un delegado que sera al que le avise cuando se produzca un evento (una pulsacion en una tecla)
 */
public class Teclado extends Sprite {
	
	//Constantes
	private static final float ANCHO = 720;
	private static final float ALTO = 288;
	private static final float ANCHO_CARACTER = 72;
	private static final float ALTO_CARACTER = 72;
	private static final int CARACTERES_POR_FILA = 10;
	
	private OnTeclaPulsada delegado;
	private BaseScene scene;
	
	private char[] caracteres = new char[] {
		'1','2','3','4','5','6','7','8','9','0',
		'Q','W','E','R','T','Y','U','I','O','P',
		'A','S','D','F','G','H','J','K','L','Ñ',
		' ','X','C','V','B','N','M','8',' ',' '
	};
	
	
	/**
	 * Constructor
	 * @param scene  La escena en la que se ha de añadir el teclado
	 * @param delegado  El objeto delegado al que se le notificara de los eventos producidos
	 */
	public Teclado(BaseScene scene,OnTeclaPulsada delegado) {
		//super(0,Constants.ALTO_CAMARA-ALTO,ResourcesManager.getInstance().texturasEvaluacion[ResourcesManager.TECLADO_ID],scene.vbom);
		super(0,Constants.ALTO_CAMARA-ALTO,ResourcesManager.getInstance().texturasEvaluacion[0],scene.vbom);
		this.delegado = delegado;
		scene.attachChild(this);
		scene.registerTouchArea(this);
	}
	
	
	/**
	 * Destruye el teclado
	 */
	public void dispose() {
		super.dispose();
		this.detachSelf();
		scene.unregisterTouchArea(this);
	}
	
	/**
	 * Interface que define el metod que ha de implementar quien quiera ser el responsable (delegado) de esta clase
	 */
	public interface OnTeclaPulsada {
		abstract void teclaPulsada(char tecla);
	}

	
	
	//------------------------------------------
	//  SUPERCLASS METHODS
	//------------------------------------------
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
		//Recupero la tecla en la que se pulso a artir de su posicion
		if (pSceneTouchEvent.isActionDown()) {
			int columna = (int)(pTouchAreaLocalX/ANCHO_CARACTER);
			int fila = (int)(pTouchAreaLocalY/ALTO_CARACTER);
			int posicion = CARACTERES_POR_FILA*fila + columna;
			char caracter = caracteres[posicion];
			if (caracter != ' ') delegado.teclaPulsada(caracter);
			return true;
		}
		return false;
	}

}
