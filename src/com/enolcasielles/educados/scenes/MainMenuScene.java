package com.enolcasielles.educados.scenes;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.enolcasielles.educados.Constants;
import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.menus.BaseMenu;
import com.enolcasielles.educados.niveles.InfoNiveles;

public class MainMenuScene extends BaseScene {
          
		// ===========================================================
        // Constants
        // ===========================================================
		//Mundo Mate
		private final int MUNDO_MATE_X = 250;
		private final int MUNDO_MATE_Y = 140;
		private final int MUNDO_MATE_ANCHO = 220;
		private final int MUNDO_MATE_ALTO = 200;
		
		//Mundo Lengua
		private final int MUNDO_LENGUA_X = 15;
		private final int MUNDO_LENGUA_Y = 140;
		private final int MUNDO_LENGUA_ANCHO = 220;
		private final int MUNDO_LENGUA_ALTO = 200;
		
		//Mundo Natu
		private final int MUNDO_NATU_X = 485;
		private final int MUNDO_NATU_Y = 140;
		private final int MUNDO_NATU_ANCHO = 220;
		private final int MUNDO_NATU_ALTO = 200;
		
		//Boton Ajustes
		private final int BOTON_AJUSTES_X = 80;
		private final int BOTON_AJUSTES_Y = 370;
		private final int BOTON_AJUSTES_ANCHO = 80;
		private final int BOTON_AJUSTES_ALTO = 80;
		
		//Boton Registro
		private final int BOTON_REGISTRO_X = 240;
		private final int BOTON_REGISTRO_Y = 370;
		private final int BOTON_REGISTRO_ANCHO = 80;
		private final int BOTON_REGISTRO_ALTO = 80;
		
		//Boton Info
		private final int BOTON_INFO_X = 400;
		private final int BOTON_INFO_Y = 370;
		private final int BOTON_INFO_ANCHO = 80;
		private final int BOTON_INFO_ALTO = 80;
		
		//Boton Creditos
		private final int BOTON_CREDITOS_X = 560;
		private final int BOTON_CREDITOS_Y = 370;
		private final int BOTON_CREDITOS_ANCHO = 80;
		private final int BOTON_CREDITOS_ALTO = 80;
 
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite botonMundoMate, botonMundoLengua, botonMundoNatu, botonAjustes, botonRegistro, 
					botonInfo, botonCreditos, background;
   
		private BaseMenu ventanaAjustes;
        
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
			this.detachChild(botonMundoLengua);
			botonMundoLengua.dispose();
			this.detachChild(botonMundoMate);
			botonMundoMate.dispose();
			this.detachChild(botonMundoNatu);
			botonMundoNatu.dispose();
			this.detachChild(botonAjustes);
			botonAjustes.dispose();
			this.detachChild(botonCreditos);
			botonCreditos.dispose();
			this.detachChild(botonInfo);
			botonInfo.dispose();
			this.detachChild(botonRegistro);
			botonRegistro.dispose();
			this.detachChild(background);
			background.dispose();
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {
			botonMundoLengua = new Sprite(MUNDO_LENGUA_X, MUNDO_LENGUA_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_MUNDO_LENGUA_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.8f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						SceneManager.getInstance().menuScene_to_worldScene(Constants.MUNDO_LENGUA);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonMundoMate = new Sprite(MUNDO_MATE_X, MUNDO_MATE_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_MUNDO_MATE_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.9f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						SceneManager.getInstance().menuScene_to_worldScene(Constants.MUNDO_MATE);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonMundoNatu = new Sprite(MUNDO_NATU_X, MUNDO_NATU_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_MUNDO_NATU_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.9f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						SceneManager.getInstance().menuScene_to_worldScene(Constants.MUNDO_NATU);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonAjustes = new Sprite(BOTON_AJUSTES_X, BOTON_AJUSTES_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_SETTINGS_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.9f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						ventanaAjustes.show();
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonRegistro = new Sprite(BOTON_REGISTRO_X, BOTON_REGISTRO_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_REGISTRO_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.8f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						ventanaAjustes.show();
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonInfo = new Sprite(BOTON_INFO_X, BOTON_INFO_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_INFO_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.8f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						ventanaAjustes.show();
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			botonCreditos = new Sprite(BOTON_CREDITOS_X, BOTON_CREDITOS_Y, resourcesManager.texturasMenu[ResourcesManager.MENU_CREDITOS_ID], vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						this.setScale(0.8f);
					}
					if(pSceneTouchEvent.isActionUp()) {
						this.setScale(1.0f);
						ventanaAjustes.show();
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			
			background = new Sprite(0, 0, resourcesManager.texturasMenu[ResourcesManager.MENU_BACKGROUND_ID], vbom);
			ventanaAjustes = new BaseMenu(this, 60, 500, 60, 40, 600, 400);
		}
		

		private void createBackground() {
			this.setBackground(new SpriteBackground(background));
			
			//Añado los sprites 
			this.attachChild(botonAjustes);
			this.attachChild(botonRegistro);
			this.attachChild(botonInfo);
			this.attachChild(botonCreditos);
			
			botonMundoLengua.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f){
				@Override
				protected void onModifierFinished(IEntity pItem) {
					// TODO Auto-generated method stub
					botonMundoMate.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							botonMundoNatu.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f));
						}
					});
				}
			});
			botonMundoLengua.setAlpha(0.0f);
			botonMundoMate.setAlpha(0.0f);	
			botonMundoNatu.setAlpha(0.0f);	
			this.attachChild(botonMundoLengua);
			this.attachChild(botonMundoMate);
			this.attachChild(botonMundoNatu);

			
			//REgistro los listener
			this.registerTouchArea(botonMundoMate);
			this.registerTouchArea(botonMundoLengua);
			this.registerTouchArea(botonMundoNatu);
			this.registerTouchArea(botonAjustes);
			this.registerTouchArea(botonCreditos);
			this.registerTouchArea(botonInfo);
			this.registerTouchArea(botonRegistro);
			
			
			
			//Creo una nueva capa para las ventanas desplegables
			Entity capaVentanas = new Entity();
			this.attachChild(capaVentanas);
			capaVentanas.attachChild(ventanaAjustes);
		}


}