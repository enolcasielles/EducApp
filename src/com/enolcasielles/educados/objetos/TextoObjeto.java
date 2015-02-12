package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.text.Text;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.R.integer;
import android.util.Log;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class TextoObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private final String TAG_ATRIBUTO_VALOR = "valor";
	private Text entidad;
	private int contador;
	
	//Array almacenando todos los objetos Text que precise 
	private ArrayList<Text> textos;
	private ArrayList<String> trozosTexto;
	private Text textoActual;
	
	/**
	 * Construye un objeto de tipo texto
	 * 
	 * @param pAttributes Los atributos extraidos del xml que permiten la configuracion del objeto
	 * @param rm  El manejador de recursos para acceder a ellos
	 * @param scene  La escena en la que se establecera este obeto
	 */
	public TextoObjeto(final Attributes pAttributes,  BaseScene scene) {
		super (pAttributes, scene);
		textos = new ArrayList<Text>();
		trozosTexto = new ArrayList<String>();
	}
	
	
	
	@Override
	public IEntity setEntidad() {
		
		//Recuperacion de los atributos 
		String texto = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_VALOR);
		contador = 1;
		
		//Separo el string en los correspondientes 
		String trozos[] = texto.split("\n");  //Rompo por el salto de linea
		for (int i=0 ; i<trozos.length ; i++) {
			trozosTexto.add(trozos[i]);
		}
		
		
		//Formo la entidad y la devuelvo
		textoActual = new Text(0, 0, rm.fuenteGame, texto.substring(0, contador),scene.vbom);
		textoActual.setPosition(x+textoActual.getWidth()/2, y+textoActual.getHeight()/2);
		entidad = textoActual;
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
		if (contador!=0) {
			scene.detachChild(textoActual);
			textoActual.dispose(); 
		}
		
		
		//Genero el nuevo texto 
		String txt = trozosTexto.get(textos.size());
		contador++; 
		textoActual = new Text(0, 0, rm.fuenteGame,
				txt.substring(0, contador),scene.vbom);
		textoActual.setPosition(x+textoActual.getWidth()/2, y+textoActual.getHeight()/2);
		entidad = textoActual;
		
		//Lo añado a a escena
		scene.attachChild(textoActual);
		
		
		if (contador == txt.length()) { //Empiezo nueva linea
			contador=0;  //Reinicio el contador
			textos.add(textoActual);   //Añado el texto ya pintado al array
			y = y-textoActual.getHeight()*2;   //Actualizo la posicion vertical
		}
		return false;
		
	}

}
