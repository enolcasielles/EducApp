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
		String texto = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_TEXTO);
		
		//Formo la entidad y la devuelvo
		return new Text(x, y, rm.fuenteGame, texto,scene.vbom);
		
	}

}
