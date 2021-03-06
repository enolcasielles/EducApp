package com.enolcasielles.educados.niveles;

import java.io.IOException;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.constants.LevelConstants;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.loaderdata.EntityLoader;
import com.enolcasielles.educados.loaderdata.SimpleLevelEntityLoaderData;
import com.enolcasielles.educados.loaderdata.SimpleLevelLoader;
import com.enolcasielles.educados.objetos.ConsejoObjeto;
import com.enolcasielles.educados.objetos.ImagenObjeto;
import com.enolcasielles.educados.objetos.Objeto;
import com.enolcasielles.educados.objetos.ObjetosManager;
import com.enolcasielles.educados.objetos.ObjetosManager.OnLoadFinished;
import com.enolcasielles.educados.objetos.TextoObjeto;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.TeoriaScene;


/**
 * Clase controlador. Las escenas utilizaran esta clase para parsear el documento 
 * XML correspondiente a su nivel. Esta clase sera la que se encargue de este parseo, recuperando
 * los componentes que forman la vista de la misma, aņadiendolos a la escena e implementando su funcionalidad
 * 
 * @author Enol Casielles
 *
 */
public class ParseadorNivelXML {
	
	private final String FICHERO_DATOS = "datosTeoria.xml";
	
	
	//Etiquetas texto
	private final String TAG_TEXTO = "texto";
	private final String TAG_IMAGEN = "imagen";
	private final String TAG_CONSEJOS = "consejo";
	private final String TAG_PAGINA = "pagina";
	private final String TAG_ATRIBUTO_TEXTURAS = "texturas";
	private final String TAG_ATRIBUTO_PAGINAS = "paginas";
	private final String TAG_ATRIBUTO_TITULO = "titulo";
	
	private BaseScene scene;
	
	private Sprite contenido;

	
	/**
	 * Recibe una escena de tipo Game y efectua todo el proceso de parseo, dejando la escena totalmente configurada
	 * en cuanto a sus elementos dinamicos
	 * 
	 * @param scene La escena de juego que se quiere formar
	 */
	public ParseadorNivelXML(TeoriaScene scene,Sprite contenido) {
		this.scene = scene;
		this.contenido = contenido;
	}
	

	
	
	
	public ObjetosManager parseNivel(final String rutaFicheros, final OnLoadFinished olf) {
		//Objeto para realizar el parseado
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(scene.vbom);
		
		//Inicio el manejador de objetos
		final ObjetosManager om = new ObjetosManager(scene,olf);
		
		
		//Configuracion global del nivel
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
		{
		        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
		        {
		            final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
		            final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
		            final String texturas = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ATRIBUTO_TEXTURAS);  	
		            final int paginas =  SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ATRIBUTO_PAGINAS);
		            final String titulo = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ATRIBUTO_TITULO); 
		            
		            //Fijo el titulo de la leccion
		            olf.setTitle(titulo);
		            
		            //Fijo el indicador de la pagina
		            olf.setIndicadorPagina("1/"+paginas);
		            
		            //Cargo las texturas ahora que ya tengo identificados sus ids en el String texturas
		            om.loadTexturas(texturas, rutaFicheros);
		            
		            return scene;
		        }
		});
		
		
		
		//Preparo el objeto para responder a cada tipo de dato recibido
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_TEXTO) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
	        {
	        	final Objeto o = new TextoObjeto(pAttributes,scene,contenido);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
		
		
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_CONSEJOS) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final Objeto o = new ConsejoObjeto(pAttributes,scene,contenido);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_IMAGEN) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final Objeto o = new ImagenObjeto(pAttributes,scene,contenido);
	        	om.addObjeto(o);
	        	return o.getEntidad();
	        }
	    });
		
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_PAGINA) {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	return om.addPagina();
	        }
	    });
	    
		
		//Preparo la escena para realizar la actualizacion, actualizara 10 veces por segundo
		scene.registerUpdateHandler(new TimerHandler(1f / 10.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	om.update();
            }

        }));
		
		//Cargo el nivel
	    levelLoader.loadLevelFromAsset(scene.activity.getAssets(), rutaFicheros + FICHERO_DATOS);
	    
	    //Inicio el manejador de objetos apuntando a su primer objeto
	    om.init();
	    
	    return om;

	}
	

}
