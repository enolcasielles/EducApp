package com.enolcasielles.educados.menus;

import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.scenes.BaseScene;

public class BaseMenu extends Rectangle {
	
	private float xIni, yIni, xFin, yFin;
	private BaseScene scene;
	private Sprite botonCerrar;
	
	public BaseMenu(BaseScene scene, float xIni, float yIni, float xFin, float yFin, float ancho, float alto ) {
		super(xIni, yIni, ancho, alto, scene.vbom);
		this.xFin = xFin;
		this.xIni = xIni;
		this.yFin = yFin;
		this.yIni = yIni;
		this.scene = scene;
		this.setColor(Color.BLUE);   //Pongo un color
		botonCerrar = new Sprite(0, 0, scene.resourcesManager.texturasMenu[ResourcesManager.MENU_INFO_ID], scene.vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				hide();
				return false;
			}
		};
		this.attachChild(botonCerrar);
	}
	
	
	
	private void hide() {
		this.registerEntityModifier(new MoveYModifier(0.2f, yFin, yIni));
		scene.unregisterTouchArea(botonCerrar);
	}
	
	
	public void show() {
		this.registerEntityModifier(new MoveYModifier(0.2f, yIni, yFin));
		scene.registerTouchArea(botonCerrar);
	}

}
