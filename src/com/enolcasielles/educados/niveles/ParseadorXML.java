package com.enolcasielles.educados.niveles;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.GameScene;
import com.enolcasielles.educados.scenes.WorldScene;


/**
 * Clase controlador. Las escenas utilizaran esta clase para parsear el documento 
 * XML correspondiente a su nivel. Esta clase sera la que se encargue de este parseo, recuperando
 * los componentes que forman la vista de la misma, añadiendolos a la escena e implementando su funcionalidad
 * 
 * @author Enol Casielles
 *
 */
public class ParseadorXML {
	
	//CONSTANTS
	private final String TAG_NIVEL = "nivel";
	
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_X = "x";
	private final String TAG_ATRIBUTO_Y = "y";
	
	
	private BaseScene scene;

	
	/**
	 * 
	 * @param scene La escena de juego que se quiere formar
	 */
	public ParseadorXML(GameScene scene) {
		this.scene = scene;
		parseNivel(InfoNiveles.getNivel(scene.getMundo(), scene.getNivel()));
	}
	
	
	
	/**
	 * 
	 * @param scene La escena del mundo que se quiere formar
	 */
	public ParseadorXML(WorldScene scene) {
		this.scene = scene;
		//Parseo el xml con el mundo
		parseWorld(InfoNiveles.getMundo(scene.getMundo()));
	}
	
	
	
	
	
	
	private void parseWorld(String xml) {
	    //Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);

	    //Configuro el objeto, creando las entidades con los datos que me va enviando
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_NIVEL) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	            //Recupero los atributos de la etiqueta
	        	final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ATRIBUTO_X);
	            final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ATRIBUTO_Y);
	            final int nivel = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ATRIBUTO_ID);
	            
	            //Configuro el sprite
	            final Sprite levelObject = new Sprite(x, y, scene.resourcesManager.texturaBotonAccesoNivel, scene.vbom)
                {
                    @Override
                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                    		float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    	// TODO Auto-generated method stub
                    	return super
                    			.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                };
                //levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));

	            //levelObject.setCullingEnabled(true);

                //Y lo devuelvo
	            return levelObject;
	        }
	    });

	    //Cargo el nivel
	    levelLoader.loadLevelFromAsset(scene.activity.getAssets(), xml);
	}
	
	
	
	private void parseNivel(String xml) {
		
	}
	

}
