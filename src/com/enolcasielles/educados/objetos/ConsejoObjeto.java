package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.TeoriaScene;


/**
 * Clase para mostrar objetos de tipo imagen
 * 
 * @author Enol Casielles
 *
 */
public class ConsejoObjeto extends Objeto{
	
	
	//Atributos
	private final String TAG_ATRIBUTO_TEXTO = "texto";
	
	
	//Tiempo durante el que se ha de mostrar y tiempo referencia sobre el que se cuenta
	private int tiempo;
	private long tiempoIni;

	
	
	/**
	 * Construye el objeto
	 * @param pAttributes Los atributos que lo definen
	 * @param scene La escena sobre la que se ha de añadir
	 * @param contenido  El sprite referencia sobre el que se ha de localizar
	 * @param atlas  El Atlas en el que se alamcena la imagen asociada
	 */
	public ConsejoObjeto(final Attributes pAttributes,  BaseScene scene, Sprite contenido) {
		super (pAttributes, scene, contenido);
	}

	
	@Override
	public IEntity setEntidad() {
		
		//Recupero ancho, alto y ruta de la imagen
		final String textoLoro = SAXUtils.getAttributeOrThrow(attributes,TAG_ATRIBUTO_TEXTO);
		
		
		//Finalmente creo la entidad
		Sprite sprt = new Sprite(x, y, scene.resourcesManager.texturasTiledTeoria[ResourcesManager.TEORIA_BOTONMENU_ID], scene.vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (scene instanceof TeoriaScene) ((TeoriaScene)scene).speakLoro(textoLoro);
				return true;
			}
		};
		sprt.setScale(0.4f);   //****************BORRAR ESTO*******************//
		sprt.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(0.5f, 0.4f, 0.5f)));
		scene.registerTouchArea(sprt);
		return sprt;
	}
	
	
	@Override
	public boolean update() {
		if (estado == ESTADO.PRIMERA) {  //Inicio el tiempo
			tiempoIni = System.currentTimeMillis();
			this.show();
			estado = ESTADO.YA_MOSTRADO;  //Para que las siguientes veces no entre aqui
			return false;
		}
		//Comprobamos si ya ha pasado el tiempo necesario
		if (System.currentTimeMillis() - tiempoIni >= tiempo) return true;
		return false;
	}
	
}
