package com.enolcasielles.educados.scenes;

import java.util.ArrayList;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

import com.enolcasielles.educados.GameActivity;
import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.niveles.ParseadoMundoXML;

public class WorldScene extends BaseScene {
          
		// ===========================================================
        // Constants
        // ===========================================================
		
		
		
		
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite background;
		private static String mundo;
		
		
		/**
         * Configura el mundo a generar
         * Llamar a este metodo siempre antes de iniciar el objeto para que sepa que mundo generar
         * Esta implementacion se efectua porque el constructor no puede definir el mundo antes de
         * llamar a super y al llamar a super se llevaran a cabo operaciones que precisan de esta informacion
         * Para este caso nos sirve hacerlo de este modo ya que nunca se crearan dos objetos GameScene a la vez.
         * Por tanto el flujo de trabajo sera ir cambiando el valor de esta variable de clase y luego generar el objeto.
         * @param mundo El mundo al que pertence el nivel
         */
        public static void setMundo(String mundo) {
        	WorldScene.mundo = mundo;
        }
		
		
		
        
        // ===========================================================
        // Methods for Superclass
        // ===========================================================
		
		
		@Override
		public void createScene() {
			iniatalizeVariables();
			createBackground();
			ParseadoMundoXML parser = new ParseadoMundoXML(this);   //Configuro la parte dinamica guaradada en su xml
		}


		@Override
		public void onBackKeyPressed() {
			this.camera.setZoomFactor(1.0f);    //Quito zoom y cambio de escena
			SceneManager.getInstance().worldScene_to_secondMenuScene();
		}

		@Override
		public void disposeScene() {
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
			//background = new Sprite(GameActivity.ANCHO_CAMARA/2, GameActivity.ALTO_CAMARA/2,GameActivity.ANCHO_CAMARA,GameActivity.ALTO_CAMARA, resourcesManager.texturaBackground, vbom);
			background = new Sprite(0,0, resourcesManager.texturasWorld[ResourcesManager.WORLD_WORLD_ID], vbom);
			this.camera.setZoomFactor(2.0f);   //Hago zoom
		}
		

		private void createBackground() {
		   this.attachChild(background);
		}


		/**
		 * Devuelve el mundo de esta escena
		 * 
		 * @return El mundo de esta escena
		 */
		public String getMundo() { 
			return mundo;
		}
		
}