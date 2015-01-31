package com.enolcasielles.educados.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;

import android.R.integer;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;


/**
 * Clase que implementa la escena del juego. Cada vez que se quiera iniciar un nuevo
 * nivel se instanciará un objeto de esta clase pasándole como parámetros el mundo y dentro del
 * el nivel que se quiere cargar
 * 
 * @author Enol Casielles
 *
 */

public class GameScene extends BaseScene {

          
		// ===========================================================
        // Constants
        // ===========================================================

	
	
 
        // ===========================================================
        // Fields
        // ===========================================================
        private int mundo;
        private int nivel;
        
        
	    
		/**
		 *  
		 * @param mundo El mundo correspondiente a la escena a formar
		 * @param nivel El nivel dentro del mundo de esta escena
		 */
		public GameScene(int mundo, int nivel) {
			super();
			this.nivel = nivel;
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

		}
		

		private void createBackground() {
		  
		}
		
		
		/**
		 * Devuelve el nivel de la escena
		 * 
		 * @return El nivel asociado a esta escena
		 */
		public int getNivel() {
			return nivel;
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