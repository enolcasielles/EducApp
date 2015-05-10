package com.enolcasielles.educados.games.objetosgame;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que implementa la funcionalidad basica de cualquier objeto del juego. Simplemente se 
 * añadira a la escena en la posicion dada y dispondra de un metodo para destruirlo
 * @author Enol Casielles
 *
 */
public class GameObjeto extends Sprite{
	
	private boolean estaDestruido;
	
	protected BaseScene scene;
	protected String id;
	
	/**
	 * Construye un objeto pregunta
	 * @param x Posicion en la que se quiere colocar (x)
	 * @param y Posicion en la que se quiere colocar (y)
	 * @param textura  La textura asociada a este objeto
	 * @param scene  La escena en la que se ha de añadir
	 */
	public GameObjeto(float x,float y, float ancho, float alto, ITextureRegion textura, BaseScene scene, String id) {
		super(x,y,ancho,alto,textura,scene.vbom);
		this.scene = scene;
		this.id = id;
		this.estaDestruido = false;
		scene.attachChild(this);
	}

	
	
	/**
	 * Elimina el objeto de la escena y lo destruye
	 */
	public void dispose() {
		super.detachSelf();
		super.dispose();
	}
	
	
	/**
	 * Oculta el objeto y cuando finaliza avisa de que el objeto ya puede ser destruido
	 */
	public void finaliza() {
		this.registerEntityModifier(new AlphaModifier(1.0f, this.getAlpha(), 0.0f) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				GameObjeto.this.estaDestruido=true;
			}
		});
	}
	
	
	/**
	 * Devuelve el id correspondiente a este objeto
	 * @return  El String con el id
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Devuelve el estado de destruccion del objeto
	 * @return  true si el objeto ya ha finalizado de destruirse, false si no
	 */
	public boolean estaDestruido() {
		return estaDestruido;
	}


}
