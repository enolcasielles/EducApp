package com.enolcasielles.educados.scenes;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.enolcasielles.educados.Constants;
import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.utiles.SpriteButton;
import com.enolcasielles.educados.utiles.SpriteButton.OnSpriteButtonCallback;
import com.enolcasielles.educados.utiles.SpriteButton.TIPO_ANIMACION;


/**
 * Clase que controla la escena para el menu secundario de la aplicacion
 * 
 * Permite al usuario navegar hacia uno de los mundos o volver al menu principal.
 * 
 * Los mundos contendran una animacion que hara que se esten moviendo
 * 
 * @author Enol Casielles
 *
 */
public class SecondMenuScene extends BaseScene {
          
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
		
		//Boton atras
		private final int BOTON_ATRAS_X = 15;
		private final int BOTON_ATRAS_Y = 390;
		private final int BOTON_ATRAS_ANCHO = 220;
		private final int BOTON_ATRAS_ALTO = 50;

 
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite background, botonMundoMate, botonMundoLengua, botonMundoNatu, botonAtras;
        
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
			this.detachChild(botonAtras);
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {
			botonMundoLengua = new SpriteButton(MUNDO_LENGUA_X, MUNDO_LENGUA_Y, MUNDO_LENGUA_ANCHO, MUNDO_LENGUA_ALTO,
					resourcesManager.texturasSecondMenu[ResourcesManager.SECOND_MENU_MUNDO_LENGUA_ID],
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							SceneManager.getInstance().secondMenuScene_to_worldScene(Constants.MUNDO_LENGUA);
						}
					}, TIPO_ANIMACION.SCALE
			);
			
			
			
			botonMundoMate = new SpriteButton(MUNDO_MATE_X, MUNDO_MATE_Y, MUNDO_MATE_ANCHO, MUNDO_MATE_ALTO, 
					resourcesManager.texturasSecondMenu[ResourcesManager.SECOND_MENU_MUNDO_MATE_ID], 
					vbom, new  OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							SceneManager.getInstance().secondMenuScene_to_worldScene(Constants.MUNDO_MATE);
						}
					}, TIPO_ANIMACION.SCALE
			); 
			
			
			botonMundoNatu = new SpriteButton(MUNDO_NATU_X, MUNDO_NATU_Y, MUNDO_NATU_ANCHO, MUNDO_NATU_ALTO,
					resourcesManager.texturasSecondMenu[ResourcesManager.SECOND_MENU_MUNDO_NATU_ID], 
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							SceneManager.getInstance().secondMenuScene_to_worldScene(Constants.MUNDO_NATU);
						}
					},  TIPO_ANIMACION.SCALE
			); 
			
			
			
			botonAtras = new SpriteButton(BOTON_ATRAS_X, BOTON_ATRAS_Y, BOTON_ATRAS_ANCHO, BOTON_ATRAS_ALTO,
					resourcesManager.texturasSecondMenu[ResourcesManager.SECOND_MENU_BOTON_ATRAS_ID], 
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							SceneManager.getInstance().secondMenuScene_to_mainMenuScene();
						}
					}, TIPO_ANIMACION.SCALE
			); 
				
			
			//Fondo
			background = new Sprite(0,0,Constants.ANCHO_CAMARA,Constants.ALTO_CAMARA, resourcesManager.texturasSecondMenu[ResourcesManager.SECOND_MENU_BACKGROUND_ID],vbom);

		}
		

		private void createBackground() {
			this.setBackground(new SpriteBackground(background));
			
			
			//Configuro sprites y los añado a la escena
			botonMundoLengua.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f){
				@Override
				protected void onModifierFinished(IEntity pItem) {
					// TODO Auto-generated method stub
					botonMundoMate.setVisible(true);
					botonMundoMate.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							botonMundoNatu.setVisible(true);
							botonMundoNatu.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f) {
								@Override
								protected void onModifierFinished(IEntity pItem) {
									botonAtras.setVisible(true);
									botonAtras.registerEntityModifier(new AlphaModifier(0.5f, 0.0f, 1.0f));
								}
							});
						}
					});
				}
			});
			botonMundoNatu.setVisible(false);
			botonMundoLengua.setVisible(true);
			botonMundoMate.setVisible(false);
			botonAtras.setVisible(false);
			this.attachChild(botonMundoLengua);
			this.attachChild(botonMundoMate);
			this.attachChild(botonMundoNatu);
			this.attachChild(botonAtras);

			
			//REgistro los listener
			this.registerTouchArea(botonMundoMate);
			this.registerTouchArea(botonMundoLengua);
			this.registerTouchArea(botonMundoNatu);
			this.registerTouchArea(botonAtras);
		
		}


}