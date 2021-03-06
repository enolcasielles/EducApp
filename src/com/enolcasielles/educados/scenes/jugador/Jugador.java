package com.enolcasielles.educados.scenes.jugador;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.objetos.NivelObjeto;
import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que controla el movimiento del jugador por la escena del menu.
 * Sera la que se encargue de almacenar la posicion del jugador en la escena y de 
 * actualizar su posicion cuando se pulse en un item de la escena
 * 
 * @author Enol Casielles
 *
 */
public class Jugador extends Sprite {
	

	private NivelObjeto posicionActual;	//La entidad en la que se encuentra el jugador
	
	private enum ESTADO {
		PARADO,
		MOVIENDO
	};
	private ESTADO estado;
	
	/**
	 * Construye el jugador en la posicion de la entidad que recibe y lo ata a la escena que recibe
	 * @param posicionActual El nivel al que se tendra que pegar el jugador
	 * @param scene La escena en la que lo incluira
	 */
	public Jugador(NivelObjeto posicionActual, BaseScene scene) {
		super(0.0f, 0.0f, scene.resourcesManager.texturasWorld[ResourcesManager.WORLD_BARCO_ID], scene.vbom);
		this.setPosition(posicionActual.getEntidad().getX(), posicionActual.getEntidad().getY());
		this.posicionActual = posicionActual;
		scene.camera.setChaseEntity(this);   //Indico a la camara que ha de seguir al jugador
		scene.attachChild(this);
		this.estado = ESTADO.PARADO;
	}
	
	
	
	/**
	 * Devuelve el id del objeto en el que esta el jugador
	 * @return
	 */
	public String getId() {
		return posicionActual.getId();
	}
	
	
	/**
	 * Devuelve el estado del jugador
	 * @return  True si se esta moviendo, false si esta parado
	 */
	public boolean isMoviendo() {
		return (estado == ESTADO.MOVIENDO);
	}
	
	/**
	 * Mueve el jugador a la posicion del objeto que se le pasa
	 * @param o
	 */
	public void mover(NivelObjeto o) {
		//Apunto la nueva posicion
		posicionActual = o;
		this.registerEntityModifier(new MoveModifier(1.5f, this.getX(),  o.getEntidad().getX(), this.getY(), o.getEntidad().getY()){
			@Override
			protected void onModifierStarted(IEntity pItem) {
				super.onModifierStarted(pItem);
				Jugador.this.estado = ESTADO.MOVIENDO;
			}
			@Override
			protected void onModifierFinished(IEntity pItem) {
				// TODO Auto-generated method stub
				super.onModifierFinished(pItem);
				Jugador.this.estado = ESTADO.PARADO;
			}
		});
	}
}
