package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.R.integer;
import android.util.Log;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class NivelObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private final String TAG_ATRIBUTO_ID = "id";
	
	
	//VARIABLES
	private String mundo, nivel;
	private NivelesManager controlador;   //Una referencia al objeto que precede a este
	
	
	/**
	 * 
	 * @param pAttributes  Los atributos de la etiqueta xml de este nivel
	 * @param scene  La escena World en la que se ubicara este objeto
	 * @param mundo  El mundo al que se corresponde
	 * @param nm El controlador de los objetos de este tipo
	 */
	public NivelObjeto(final Attributes pAttributes, final BaseScene scene, String mundo, NivelesManager nm) {
		super(pAttributes, scene);
		this.mundo = mundo;
		this.controlador = nm;
	}
	 
	
	
	@Override  
	public IEntity setEntidad() {
		
		//Recupero atributos de este objeto
		final String nivel = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_ID);
		this.nivel = nivel;
		
		//Configuro el sprite
	    final Sprite entidad = new Sprite(x, y, rm.texturasWorld[ResourcesManager.WORLD_NIVEL_ID], scene.vbom)
	    {
	        @Override 
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	        	if (pSceneTouchEvent.isActionDown()) {
	        		//Delego al controlador la tarea a realizar cuando se pulse en esta entidad
		        	controlador.onTouchIn(NivelObjeto.this);
	        		return true;
	        	}
	        	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	        }
	    };
	    
	    scene.registerTouchArea(entidad);
		
	    //Devuelvo la entidad configurada
		return entidad;
	}
	
	
	
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * Devuelve el id del objeto, es decir, el nivel al que se corresponde
	 * @return El id del nivel
	 */
	public String getId() {
		return nivel;
	}
	
	
	
	/**
	 * Devuelve el mundo en el que se esta
	 * @return El mundo
	 */
	public String getMundo() {
		return this.mundo;
	}

}
