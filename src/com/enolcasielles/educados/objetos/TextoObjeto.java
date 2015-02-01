package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.text.Text;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class TextoObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private String TAG_ATRIBUTO_TEXTO = "texto";
	private String texto;
	private Text entidad;
	private int contador;
	
	/**
	 * Construye un objeto de tipo texto
	 * 
	 * @param pAttributes Los atributos extraidos del xml que permiten la configuracion del objeto
	 * @param rm  El manejador de recursos para acceder a ellos
	 * @param scene  La escena en la que se establecera este obeto
	 */
	public TextoObjeto(final Attributes pAttributes,  BaseScene scene) {
		super (pAttributes, scene);
	}
	
	
	
	@Override
	public IEntity setEntidad() {
		
		//Recuperacion de los atributos 
		texto = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_TEXTO);
		
		contador = 1;
		
		//Formo la entidad y la devuelvo
		entidad =  new Text(x, y, rm.fuenteGame, texto.substring(0, contador),scene.vbom);
		return entidad;
		
	}
	
	
	/**
	 * Actualiza los objetos de tipo texto. Elimina el anterior texto de la escena, genera el nuevo añadiendo un
	 * caracter y se lo agrega a la escena. Si ya se han mostrado todos los caracteres devuelve true, sino false.
	 * 
	 * COSAS A ARREGLAR: Controlar el tiempo de actualizacion y el salto de linea cuando se ocupe el ancho
	 */
	@Override
	public boolean update() {
		
		//Elimino el texto anterior
		scene.detachChild(entidad);
		entidad.dispose();
		
		//Genero el nuevo texto
		contador++;
		entidad = new Text(x, y, rm.fuenteGame, texto.substring(0, contador),scene.vbom);
		
		//Lo añado a a escena
		scene.attachChild(entidad);
		
		//Si he acabado de mostrar el texto devuelvo true
		if (contador == texto.length()) return true;
		return false;
	}

}
