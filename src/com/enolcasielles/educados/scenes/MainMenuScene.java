package com.enolcasielles.educados.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;

public class MainMenuScene extends BaseScene {
          
		// ===========================================================
        // Constants
        // ===========================================================

	
	
 
        // ===========================================================
        // Fields
        // ===========================================================


   
        
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

		}
		

		private void createBackground() {
		   
		}


}