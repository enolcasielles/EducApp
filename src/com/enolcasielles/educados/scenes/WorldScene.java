package com.enolcasielles.educados.scenes;

import java.util.ArrayList;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;

public class WorldScene extends BaseScene {
          
		// ===========================================================
        // Constants
        // ===========================================================
		
		
		
		
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite background;
		private ArrayList<Sprite> puertasNivel;
		private int mundo;
		
		
		
		/**
		 *  
		 * @param mundo El mundo correspondiente a la escena a formar
		 */
		public WorldScene(int mundo) {
			super();
			this.mundo = mundo;
		}
		
        
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
			//SceneManager.getInstance().menuScene_to_exit();
		}

		@Override
		public void disposeScene() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {
			//background = new Sprite(0, 0, resourcesManager.texturaBackground, vbom);
			//puertasNivel = new ArrayList<Sprite>();
			SceneManager.getInstance().worldScene_to_gameScene(0, 0);   //TEST!!!!!!!!!
		}
		

		private void createBackground() {
		   
		   //this.setBackground(new SpriteBackground(background));
		}


		/**
		 * Devuelve el mundo de esta escena
		 * 
		 * @return El mundo de esta escena
		 */
		public int getMundo() {
			return mundo;
		}
		
}