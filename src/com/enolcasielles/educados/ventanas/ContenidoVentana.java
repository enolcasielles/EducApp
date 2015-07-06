package com.enolcasielles.educados.ventanas;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que define la parte interna de una ventana. Definira los elementos que hay en la
 * misma y la logica que ha de experimentar cuando se pulse en uno de ellos o se cargue la misma.
 * 
 * Clase abstracta que define la estructura que cualquier subclase ha de implementar para su correcto 
 * funcionamiento. 
 * 
 * Contiene un delegado al que informa de lso posibles evnetos
 * 
 * @author Enol Casielles
 *
 */
public abstract class ContenidoVentana extends Rectangle {

	
	//Constantes
	protected final static float ANCHO_CONTENIDO = 350;
	protected final static float ALTO_CONTENIDO = 330;
	
	//Objeto delegado
	protected OnContenidoCallback delegado;
	
	//La escena sobre la que se añade
	protected BaseScene scene;
	
	//Objeto al que se añadiran todos los objetos del contenido
	private Entity capa; 
	
	/**
	 * Contructor
	 * @param scene La escena sobre la que estara. Ha de ser la misma que la ventana
	 */
	public ContenidoVentana(BaseScene scene) {
		
		//Iniciamos la superclase
		super(0, 0, ANCHO_CONTENIDO, ALTO_CONTENIDO, scene.vbom);
		
		//Registramos el delegado y la escena
		this.scene = scene;
		
		//Inicio la capa
		capa = new Entity();
		
		//Definimos la logica del objeto
		onCreate(capa);
		
		this.attachChild(capa);

	}
	
	
	
	/**
	 * Registra el objeto delegado
	 * @param delegado El objeto delegado
	 */
	public void setDelegado(OnContenidoCallback delegado) {
		this.delegado = delegado;
	}
	
	

	
	
	//-------------------------------------------------------------//
	//------------METODOS A IMPLEMENTAR POR LAS SUBCLASES----------//
	//-------------------------------------------------------------//
	
	/**
	 * Metodo en el que se han de cargar los objetos del contenido y añadirlos a la capa que se envia.
	 * No añadir los objetos directamente al contenido!!
	 * @param capa La capa en la que se ha de añadir los objetos
	 */
	protected abstract void onCreate(Entity capa);
	
	/**
	 * Activa el control en los elementos del contenido
	 */
	protected abstract void activaControl();
	
	/**
	 * Desactiva el control en los elementos del contenido
	 */
	protected abstract void desactivaControl();
	
	/**
	 * Oculta los elementos del contenido 
	 */
	protected abstract void ocultar() ;
	
	/**
	 * Muestra los elementos del contenido
	 */
	protected abstract void mostrar();

	
	@Override
	public void dispose() {
		super.dispose();
		capa.dispose();
		capa.detachSelf();
	}
	
	
	
	/**
	 * 
	 * Interface con el protocolo a implemtnar por el delegado
	 * 
	 * @author Enol Casielles
	 *
	 */
	public interface OnContenidoCallback {
		
		/**
		 * Se ejecutara cuando se desee pasar al siguiente contenido
		 */
		public abstract void avanzaPagina();
		
		/**
		 * Se ejecutara cuando se desee pasar al anterior contenido
		 */
		public abstract void retrocedePagina();
		
		/**
		 * Se ejecutara cuando se finalice de ocultar el contenido
		 */
		public abstract void contenidoOcultado();
		
	}
	
	

}
