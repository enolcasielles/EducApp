package com.enolcasielles.educados.objetos;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.R.integer;

import com.enolcasielles.educados.scenes.BaseScene;

public class TextoObjeto extends Objeto {
	
	//CONSTANTES. ATRIBUTOS PARA UN OBJETO DE ESTE TIPO
	private final String TAG_ATRIBUTO_VALOR = "valor";
	private String texto;
	
	
	/**
	 * Construye un objeto de tipo texto
	 * 
	 * @param pAttributes Los atributos extraidos del xml que permiten la configuracion del objeto
	 * @param rm  El manejador de recursos para acceder a ellos
	 * @param scene  La escena en la que se establecera este obeto
	 */
	public TextoObjeto(final Attributes pAttributes,  BaseScene scene, Sprite contenido) {
		super (pAttributes, scene, contenido);
	}
	
	
	
	@Override
	public IEntity setEntidad() {
		
		//Recuperacion de los atributos 
		texto = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_VALOR);
		
		//Opero el string.
		//Recorto por los saltos de linea
		String[] lineas = texto.split("//n");
		String t = "";
		for (int i=0 ; i<lineas.length ; i++) {
			t += lineas[i] + "\n"; 
		}
		
		//sceneChild = new Scene();
		//sceneChild.setPosition(x, y);
		
		final Text text = new TickerText(x, y, scene.resourcesManager.fuenteGame, t,
				new TickerText.TickerTextOptions(HorizontalAlign.LEFT, 10.f),scene.vbom);
        text.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		return text;
	}
	
	
	/**
	 * Actualiza los objetos de tipo texto. Elimina el anterior texto de la escena, genera el nuevo añadiendo un
	 * caracter y se lo agrega a la escena. Si ya se han mostrado todos los caracteres devuelve true, sino false.
	 * 
	 * COSAS A ARREGLAR: Controlar el tiempo de actualizacion y el salto de linea cuando se ocupe el ancho
	 */
	@Override
	public boolean update() {
		return false;
		
	}

}
