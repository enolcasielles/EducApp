package com.enolcasielles.educados.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.enolcasielles.educados.GameActivity;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.niveles.InfoNiveles;

public class MainMenuScene extends BaseScene {
          
		// ===========================================================
        // Constants
        // ===========================================================

	
	
 
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite botonMundo1;
		private Sprite botonMundo2;
		private Sprite botonMundo3;

   
        
        // ===========================================================
        // Methods for Superclass
        // ===========================================================
	
	
		@Override
		public void createScene() {
			iniatalizeVariables();
			createBackground(); 
		}


		@Override
		public void onBackKeyPressed() {
			SceneManager.getInstance().menuScene_to_exit();
		}

		@Override
		public void disposeScene() {
			this.detachChild(botonMundo1);
			botonMundo1.dispose();
			this.detachChild(botonMundo2);
			botonMundo2.dispose();
			this.detachChild(botonMundo3);
			botonMundo3.dispose();
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {
			botonMundo2 = new Sprite(GameActivity.ANCHO_CAMARA/2, GameActivity.ALTO_CAMARA/2, resourcesManager.texturaMundo2, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionUp()) {
						SceneManager.getInstance().menuScene_to_worldScene(InfoNiveles.MUNDO_2);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			botonMundo1 = new Sprite(GameActivity.ANCHO_CAMARA/2-botonMundo2.getWidth()-10, GameActivity.ALTO_CAMARA/2, resourcesManager.texturaMundo1, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionUp()) {
						SceneManager.getInstance().menuScene_to_worldScene(InfoNiveles.MUNDO_1);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			botonMundo3 = new Sprite(GameActivity.ANCHO_CAMARA/2+botonMundo2.getWidth()+10, GameActivity.ALTO_CAMARA/2, resourcesManager.texturaMundo3, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionUp()) {
						SceneManager.getInstance().menuScene_to_worldScene(InfoNiveles.MUNDO_3);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
		}
		

		private void createBackground() {
			this.setBackground(new Background(0.81f, 0.81f, 0.81f));
			
			//Añado los sprites 
			this.attachChild(botonMundo1);
			this.attachChild(botonMundo2);
			this.attachChild(botonMundo3);
			
			//REgistro los listener
			this.registerTouchArea(botonMundo1);
			this.registerTouchArea(botonMundo2);
			this.registerTouchArea(botonMundo3);
		}


}