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
	
	//CONSTANTES DE LA CLASE
	private final float CARACTERES_POR_SEC = 10.0f;
	
	//VARIABLES
	private TickerText text;
	private int numCaracteres;
	
	
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
		//Recorto por los saltos de linea y obtengo numero de caracteres
		numCaracteres = 0;
		String[] lineas = texto.split("//n");
		String t = "";
		for (int i=0 ; i<lineas.length ; i++) {
			numCaracteres += lineas[i].length();
			t += lineas[i] + "\n"; 
		}
		
		
		text = new TickerText(x, y, scene.resourcesManager.fuenteGame, t,
				new TickerText.TickerTextOptions(HorizontalAlign.LEFT, CARACTERES_POR_SEC),scene.vbom);
        text.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		return text;
	}
	
	
	/**
	 * Comprueba si el texto ya ha finalizado de escribirse. Si esto ocurre devuelve true indicando al manejador que 
	 * puede pasar a otro objeto, sino devuelve flse
	 * 
	 */
	@Override
	public boolean update() {
		if (text.getCharactersVisible() >= this.numCaracteres) return true;
		return false;
	}

}
