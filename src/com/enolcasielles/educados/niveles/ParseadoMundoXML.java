package com.enolcasielles.educados.niveles;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.constants.LevelConstants;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.loaderdata.EntityLoader;
import com.enolcasielles.educados.loaderdata.SimpleLevelEntityLoaderData;
import com.enolcasielles.educados.loaderdata.SimpleLevelLoader;
import com.enolcasielles.educados.objetos.NivelObjeto;
import com.enolcasielles.educados.objetos.NivelesManager;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.WorldScene;


/**
 * Clase controlador. Las escenas utilizaran esta clase para parsear el documento 
 * XML correspondiente a su nivel. Esta clase sera la que se encargue de este parseo, recuperando
 * los componentes que forman la vista de la misma, aņadiendolos a la escena e implementando su funcionalidad
 * 
 * @author Enol Casielles
 *
 */
public class ParseadoMundoXML {
	
	//CONSTANTS
	
	//Etiqueta nivel, las que apareceran en los archivos que definen un mundo
	private final String TAG_NIVEL = "nivel";
	
	private BaseScene scene;

	
	
	
	/**
	 *  Recibe una escena de tipo World y efectua todo el proceso de parseo, dejando la escena totalmente configurada
	 *  en cuanto a sus elementos dinamicos
	 *  
	 * @param scene La escena del mundo que se quiere formar
	 */
	public ParseadoMundoXML(WorldScene scene) {
		this.scene = scene;
		//Parseo el xml con el mundo
		parseWorld(scene.getMundo());
	}
	
	
	
	
	
	
	private void parseWorld(final String mundo) {
	    //Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);
		
		//Inicio el manejador de niveles
		final NivelesManager controlador = new NivelesManager(scene);
		
		//Configuracion global del nivel
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
		{
		        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
		        {
		            final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
		            final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
		            
		            scene.camera.setBounds(0, 0, width, height);
		            scene.camera.setBoundsEnabled(true);

		            return scene;
		        }
		});
 
		
	    //Configuro el objeto, creando las entidades con los datos que me va enviando
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_NIVEL) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final NivelObjeto nivel =  new NivelObjeto(pAttributes,scene,mundo,controlador);
	        	controlador.addNivel(nivel);
	        	return nivel.getEntidad();
	        }
	    });

	    //Cargo el nivel
	    //String xml = InfoNiveles.getMundo(mundo);
	    String xml = "niveles/mundo" + mundo + "/mundo.xml";
	    levelLoader.loadLevelFromAsset(scene.activity.getAssets(), xml);
	}
	
	
}
