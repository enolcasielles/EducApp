package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.R.integer;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class NivelObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private String TAG_ATRIBUTO_ID = "id";
	
	
	//VARIABLES
	private int mundo;
	
	
	public NivelObjeto(final Attributes pAttributes, BaseScene scene, int mundo) {
		super(pAttributes, scene);
		this.mundo = mundo;
	}
	
	
	@Override
	public IEntity setEntidad() {
		
		//Recupero atributos de este objeto
		final int nivel = SAXUtils.getIntAttributeOrThrow(attributes, TAG_ATRIBUTO_ID);
		
		//Configuro el sprite
	    final Sprite entidad = new Sprite(x, y, rm.texturaBotonAccesoNivel, scene.vbom)
	    {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
	        		float pTouchAreaLocalX, float pTouchAreaLocalY) {
	        	//Efectuo el cambio de escena de world a game y pasandole el nivel a entrar
	        	SceneManager.getInstance().worldScene_to_gameScene(mundo, nivel);
	        	return super
	        			.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	        }
	    };
		
	    //Devuelvo la entidad configurada
		return entidad;
	}
	

}
