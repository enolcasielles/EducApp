package com.enolcasielles.educados.games.objetosgame;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que representa cada una de las zonas en las que estaran las cajas donde se podra soltar las respuestas
 * @author Enol Casielles
 *
 */
public class Caja extends Rectangle{
	
	private float x,y,ancho,alto;
	
	private String idRespuestaCorrecta;
	
	private boolean estaMarcada, estaDestruida;
	
	
	/**
	 * Contructor
	 * @param x Vertice superior izquierdo de la caja (x)
	 * @param y Vertice superior izquierdo de la caja (y)
	 * @param ancho Ancho de la caja
	 * @param alto Alto de la caja
	 * @param idRespuestaCorrecta   String con el id que tenga la respuesta que se ha de soltar en esta caja
	 */
	public Caja(float x, float y, float ancho, float alto, BaseScene scene, String idRespuestaCorrecta) {
		super(x, y, ancho, alto, scene.vbom);
		this.setColor(1.0f, 0.0f, 0.0f);
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.idRespuestaCorrecta = idRespuestaCorrecta;
		this.estaMarcada = false;
		this.estaDestruida=false;
		scene.attachChild(this);
	}
	
	
	/**
	 * Elimina el objeto
	 */
	public void dispose() {
		this.detachSelf();
		super.dispose();
	}
	
	
	/**
	 * Finaliza la caja
	 */
	public void finaliza() {
		this.registerEntityModifier(new AlphaModifier(1.0f, this.getAlpha(), 0.0f){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				Caja.this.estaDestruida=true;
			}
		});
	}
	
	
	/**
	 * Desvuelve el estado de destruccion del objeto
	 * @return true si ya ha sido destruida o false sino
	 */
	public boolean estaDestruida() {
		return estaDestruida;
	}
	
	
	/**
	 * Informa si una posicion esta dentro de la caja
	 * @param x  El x de la posicion a evaluar
	 * @param y  El y de la posicion a evaluar
	 * @return  true si ese punto esta dentro o false si no lo esta
	 */
	public boolean sueltaDentro(float x, float y) {
		if (this.estaMarcada) return false;
		if (x > this.x && x < this.x + this.ancho) {  
			if (y > this.y && y < this.y + this.alto) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Informa si el id es el que corresponde a esta caja
	 * @param id  El id a comproar
	 * @return true si es el mimo, false si no lo es
	 */
	public boolean esCorrecta(String id) {
		if (id.equals(idRespuestaCorrecta)) {
			this.estaMarcada = true;
			return true;
		}
		return false;
	}

	
	
	//------------------------------------------------------
	//----------------GETTERS & SETTERS---------------------
	//------------------------------------------------------
	
	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getAncho() {
		return ancho;
	}


	public void setAncho(float ancho) {
		this.ancho = ancho;
	}


	public float getAlto() {
		return alto;
	}


	public void setAlto(float alto) {
		this.alto = alto;
	}


	public String getIdRespuestaCorrecta() {
		return idRespuestaCorrecta;
	}


	public void setIdRespuestaCorrecta(String idRespuestaCorrecta) {
		this.idRespuestaCorrecta = idRespuestaCorrecta;
	}

	
	public boolean estaMarcada() {
		return this.estaMarcada;
	}
	
	
	
	
}
