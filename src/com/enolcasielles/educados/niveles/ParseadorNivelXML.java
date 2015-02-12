package com.enolcasielles.educados.niveles;

import java.io.IOException;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.objetos.ImagenObjeto;
import com.enolcasielles.educados.objetos.Objeto;
import com.enolcasielles.educados.objetos.ObjetosManager;
import com.enolcasielles.educados.objetos.OperacionFraccionObjeto;
import com.enolcasielles.educados.objetos.TextoObjeto;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.GameScene;


/**
 * Clase controlador. Las escenas utilizaran esta clase para parsear el documento 
 * XML correspondiente a su nivel. Esta clase sera la que se encargue de este parseo, recuperando
 * los componentes que forman la vista de la misma, a�adiendolos a la escena e implementando su funcionalidad
 * 
 * @author Enol Casielles
 *
 */
public class ParseadorNivelXML {
	
	
	//Etiquetas texto
	private final String TAG_TEXTO = "texto";
	private final String TAG_OPERACION_FRACCION = "operacionfraccion";
	private final String TAG_IMAGEN = "imagen";
	
	private BaseScene scene;

	
	/**
	 * Recibe una escena de tipo Game y efectua todo el proceso de parseo, dejando la escena totalmente configurada
	 * en cuanto a sus elementos dinamicos
	 * 
	 * @param scene La escena de juego que se quiere formar
	 */
	public ParseadorNivelXML(GameScene scene) {
		this.scene = scene;
		parseNivel(InfoNiveles.getNivel(scene.getMundo(), scene.getNivel()));
	}
	

	
	
	
	private void parseNivel(String xml) {
		//Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);
		
		//Inicio el manejador de objetos
		final ObjetosManager om = new ObjetosManager(scene);
		
		
		//Configuracion global del nivel
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
		{
		        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
		        {
		            final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
		            final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
			            
		            // TODO later we will specify camera BOUNDS and create invisible walls
		            // on the beginning and on the end of the level.

		            return scene;
		        }
		});
		
		
		
		//Preparo el objeto para responder a cada tipo de dato recibido
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_TEXTO) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final Objeto o = new TextoObjeto(pAttributes,scene);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_OPERACION_FRACCION) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final Objeto o = new OperacionFraccionObjeto(pAttributes,scene);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
		
		/*
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_IMAGEN) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final Objeto o = new ImagenObjeto(pAttributes,scene);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
	    */
		
		
		//Preparo la escena para realizar la actualizacion, actualizara 10 veces por segundo
		scene.registerUpdateHandler(new TimerHandler(1f / 10.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	om.update();
            }

        }));
		
		//Cargo el nivel
	    levelLoader.loadLevelFromAsset(scene.activity.getAssets(), xml);
	    
	    //Inicio el manejador de objetos apuntando a su primer objeto
	  	om.init();

	}
	

}