package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.xml.sax.Attributes;

import android.R.integer;
import android.graphics.Canvas;
import android.util.Log;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class OperacionFraccionObjeto extends Objeto {
	
	
	private final String TAG_ATRIBUTO_NUMERADOR1 = "numerador1";
	private final String TAG_ATRIBUTO_NUMERADOR2 = "numerador2";
	private final String TAG_ATRIBUTO_DENOMINADOR1 = "denominador1";
	private final String TAG_ATRIBUTO_DENOMINADOR2 = "denominador2";
	private final String TAG_ATRIBUTO_OPERACION = "operacion";
	
	
	public OperacionFraccionObjeto(final Attributes pAttributes,  BaseScene scene) {
		super (pAttributes, scene);
	}
	
	@Override
	public IEntity setEntidad() {
		
		//Recupero los valores
		int numerador1 = SAXUtils.getIntAttributeOrThrow(attributes, TAG_ATRIBUTO_NUMERADOR1);
		int numerador2 = SAXUtils.getIntAttributeOrThrow(attributes, TAG_ATRIBUTO_NUMERADOR2);
		int denominador1 = SAXUtils.getIntAttributeOrThrow(attributes, TAG_ATRIBUTO_DENOMINADOR1);
		int denominador2 = SAXUtils.getIntAttributeOrThrow(attributes, TAG_ATRIBUTO_DENOMINADOR2);
		String op = SAXUtils.getAttributeOrThrow(attributes, TAG_ATRIBUTO_OPERACION);
		
		//Calculo la solucion
		int resDen = denominador1 * denominador2;
		int resNum = 0;
		if (op.equals("+")) resNum = (resDen/denominador1)*numerador1 + (resDen/denominador2)*numerador2;
		if (op.equals("-")) resNum = (resDen/denominador1)*numerador1 - (resDen/denominador2)*numerador2;
		
		//Escena que agrupara todas las entidades
		Scene tmp = new Scene();
		tmp.setPosition(x, y);
		Text num1 = new Text(25, 70, rm.fuenteGame, ""+numerador1, scene.vbom);
		Line line1 = new Line(0, 50, 50, 50, scene.vbom);
		Text den1 = new Text(25, 30, rm.fuenteGame, ""+denominador1, scene.vbom);
		Text oper = new Text(64, 50, rm.fuenteGame, op, scene.vbom);
		Text num2 = new Text(105, 70, rm.fuenteGame, ""+numerador2, scene.vbom);
		Line line2 = new Line(80, 50, 130, 50, scene.vbom);
		Text den2 = new Text(105, 30, rm.fuenteGame, ""+denominador2, scene.vbom);
		Text signo = new Text(145, 50, rm.fuenteGame, "=", scene.vbom);
		Text num3 = new Text(185, 70, rm.fuenteGame, ""+resNum, scene.vbom);
		Line line3 = new Line(160, 50, 210, 50, scene.vbom);
		Text den3 = new Text(185, 30, rm.fuenteGame, ""+resDen, scene.vbom);
		tmp.attachChild(line1);
		tmp.attachChild(oper);
		tmp.attachChild(line2);
		tmp.attachChild(signo);
		tmp.attachChild(line3);
		tmp.attachChild(num1);
		tmp.attachChild(num2);
		tmp.attachChild(den1);
		tmp.attachChild(den2);
		tmp.attachChild(num3);
		tmp.attachChild(den3);
		tmp.setVisible(false);
		Log.i("OperacionFraccionObjeto","Objeto construido");
		return tmp;
	}

	@Override
	public boolean update() {
		//Hago la entidad visible e indico que ha finalizado
		this.getEntidad().setVisible(true);
		Log.i("OperacionFraccionObjeto","Objeto visualizado");
		return true;
	}
}
