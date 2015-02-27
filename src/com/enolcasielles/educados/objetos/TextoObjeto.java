package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import com.enolcasielles.educados.scenes.BaseScene;

public class TextoObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private final String TAG_ATRIBUTO_VALOR = "valor";
	private String texto;
	private int contador;
	
	private Scene sceneChild;
	private Text t;
	
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
		texto = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_VALOR);
		contador = 1;
		
		//sceneChild = new Scene();
		//sceneChild.setPosition(x, y);
		
		//Formo la entidad y la devuelvo
		t = new Text(0, 0, rm.fuenteGame, texto.substring(0, contador),scene.vbom);
		t.setPosition(0+t.getWidth()/2, 0+t.getHeight()/2);
		//sceneChild.attachChild(t);
		//return sceneChild;
		return t;
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
		//sceneChild.detachChild(t);
		//scene.det
		t.dispose(); 
		
		//Genero el nuevo texto 
		contador++;
		t = new Text(0, 0, rm.fuenteGame, texto.substring(0, contador),scene.vbom);
		t.setPosition(0+t.getWidth()/2, 0+t.getHeight()/2);
		
		//Lo añado a la escena
		sceneChild.attachChild(t);
		
		//Si he acabado de mostrar el texto devuelvo true
		if (contador == texto.length()) return true;
		return false;
		
	}

}
