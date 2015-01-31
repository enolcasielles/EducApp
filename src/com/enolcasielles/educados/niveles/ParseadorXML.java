package com.enolcasielles.educados.niveles;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.objetos.NivelObjeto;
import com.enolcasielles.educados.objetos.TextoObjeto;
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
	
	//Etiqueta nivel, las que apareceran en los archivos que definen un mundo
	private final String TAG_NIVEL = "nivel";
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_X = "x";
	private final String TAG_ATRIBUTO_Y = "y";
	
	//Etiquetas texto
	private final String TAG_TEXTO = "texto";
	
	private BaseScene scene;

	
	/**
	 * Recibe una escena de tipo Game y efectua todo el proceso de parseo, dejando la escena totalmente configurada
	 * en cuanto a sus elementos dinamicos
	 * 
	 * @param scene La escena de juego que se quiere formar
	 */
	public ParseadorXML(GameScene scene) {
		this.scene = scene;
		parseNivel(InfoNiveles.getNivel(scene.getMundo(), scene.getNivel()));
	}
	
	
	
	/**
	 *  Recibe una escena de tipo World y efectua todo el proceso de parseo, dejando la escena totalmente configurada
	 *  en cuanto a sus elementos dinamicos
	 *  
	 * @param scene La escena del mundo que se quiere formar
	 */
	public ParseadorXML(WorldScene scene) {
		this.scene = scene;
		//Parseo el xml con el mundo
		parseWorld(scene.getMundo());
	}
	
	
	
	
	
	
	private void parseWorld(final int mundo) {
	    //Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);

	    //Configuro el objeto, creando las entidades con los datos que me va enviando
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_NIVEL) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	return new NivelObjeto(pAttributes,scene,mundo).getEntidad();
	        }
	    });

	    //Cargo el nivel
	    String xml = InfoNiveles.getMundo(mundo);
	    levelLoader.loadLevelFromAsset(scene.activity.getAssets(), xml);
	}
	
	
	
	private void parseNivel(String xml) {
		//Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);
		
		//Preparo el objeto para responder a cada tipo de dato recibido
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_TEXTO) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	return new TextoObjeto(pAttributes,scene).getEntidad();
	        }
	    });

	}
	

}
