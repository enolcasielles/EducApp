package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.util.Log;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase abstracta de la que heredaran todos los objetos que podra haber en 
 * la GameScene. Esta clase definira los comportamientos y propiedades que seran
 * comunes a todos los objetos. Cada objeto que pueda aparecer en GameScene tendra que
 * heredar de esta clase implementar los metodos abstractos de ella para definir su comportamiento
 * y extender con funcionalidades que pueda tener a mayores
 * 
 * @author Enol Casielles
 *
 */
public abstract class Objeto {
	
	//-----------------------------------------------------------------
	//CONSTANTES. ATRIBUTOS COMUNES A TODO OBJETO
	//-----------------------------------------------------------------
	protected final String TAG_ATRIBUTO_X = "x";
	protected final String TAG_ATRIBUTO_Y = "y";
	
	
	//-----------------------------------------------------------------
	//VARIABLES
	//-----------------------------------------------------------------
	private IEntity entidad;
	protected float x,y;   //La posicion de este objeto en la escena
	protected final Attributes attributes;
	protected final ResourcesManager rm;
	protected final BaseScene scene;
	private final Sprite continente;
	protected boolean firstUpdate;
	
	
	/**
	 * Constructor base para cualquier objeto que se haya de colocar localmente al sprite continente que recibe
	 * @param pAttributes Los atributos extraidos del xml para su construccion
	 * @param rm El objeto Manejador de recursos para poder acceder a los recursos
	 * @param scene La escena a la que hay que añadir este objeto
	 * @param contenido El sprite sobre el que hay que posicionar el objeto, para saber su posicion y dimensiones
	 */
	public Objeto(final Attributes pAttributes, final BaseScene scene, Sprite continente) {
		this.attributes = pAttributes;
		this.rm = scene.resourcesManager;
		this.continente = continente;
		this.scene = scene;
		firstUpdate = true;
		setAtributosComunes();
		entidad = setEntidad();
	}
	
	
	/**
	 * Constructor base para cualquier objeto que se haya de colocar en una posicion global
	 * @param pAttributes Los atributos extraidos del xml para su construccion
	 * @param rm El objeto Manejador de recursos para poder acceder a los recursos
	 * @param scene La escena a la que hay que añadir este objeto
	 */
	public Objeto(final Attributes pAttributes, final BaseScene scene) {
		this.attributes = pAttributes;
		this.rm = scene.resourcesManager;
		this.continente = null;
		this.scene = scene;
		setAtributosComunes();
		entidad = setEntidad();
	}
	
	
	/**
	 * Devuelve la entidad ya totalmente configurada. 
	 * 
	 * @return La entidad formada para este objeto
	 */
	public IEntity getEntidad() {
		return entidad;
	}
	
	
	
	private void setAtributosComunes() {
		float xContinente = (continente == null) ? 0 : continente.getX(); 
		float yContinente = (continente == null) ? 0 : continente.getY(); 
		//Recuperacion de los atributos comunes 
		x = SAXUtils.getFloatAttributeOrThrow(this.attributes, TAG_ATRIBUTO_X) + xContinente;
        y = SAXUtils.getFloatAttributeOrThrow(this.attributes, TAG_ATRIBUTO_Y) + yContinente;
	}
	
	
	
	//-----------------------------------------------------------------
	//METODOS ABSTRACTOS
	//-----------------------------------------------------------------
	
	/**
	 * Metodo encargado de configurar la entidad asociada al objeto
	 * 
	 * @return
	 */
	public abstract IEntity setEntidad();
	
	/**
	 * Actualiza el objeto. El manejador de objetos llamara sucesivamente a este metodo para actualizar el objeto
	 * @return false si el objeto aun no ha finalizado o true cuando ya lo ha hecho y se puede pasar al siguiente
	 */
	public abstract boolean update();
	
	
	
	/**
	 * Metodo encargado de hacer visible el objeto 
	 */
	public abstract void show();
	
	
	/**
	 * Libera la carga de recursos de este objeto
	 */
	public void dispose() {
		scene.detachChild(entidad);
		entidad.dispose();
		entidad = null;
	}
	

}
