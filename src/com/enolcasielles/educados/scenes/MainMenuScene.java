package com.enolcasielles.educados.scenes;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.utiles.SpriteButton;
import com.enolcasielles.educados.utiles.SpriteButton.OnSpriteButtonCallback;
import com.enolcasielles.educados.utiles.SpriteButton.TIPO_ANIMACION;
import com.enolcasielles.educados.ventanas.ContenidoVentana;
import com.enolcasielles.educados.ventanas.TestContenidoVentana;
import com.enolcasielles.educados.ventanas.Ventana;
import com.enolcasielles.educados.ventanas.Ventana.OnVentanaCallback;


/**
 * Clase que controla la escena para el menu principal de la aplicacion
 * 
 * Navegacion:
 * 
 * <ul>
 *  <li> Boton jugar: Dirige a SecondMenuScene </li>
 *  <li> Boton creditos: Despliega una ventana en la que se mostraran los creditos </li>
 *  <li> Boton info: Despliega una ventana que muestra info de la App </li>
 *  <li> Boton ajustes: Despliega una ventana que permite controlar los ajustes de la App </li>
 *  <li> Boton jugador: Despliega una ventana que permite cambiar de jugador o definir nuevos</li> 
 *</ul>
 * 
 * @author Enol Casielles
 *
 */
public class MainMenuScene extends BaseScene implements OnVentanaCallback{
          
		// ===========================================================
        // Constants
        // ===========================================================
		
		//Boton Ajustes
		private final int BOTON_AJUSTES_X = 80;
		private final int BOTON_AJUSTES_Y = 320;
		private final int BOTON_AJUSTES_ANCHO = 80;
		private final int BOTON_AJUSTES_ALTO = 80;
		
		//Boton Registro
		private final int BOTON_REGISTRO_X = 560;
		private final int BOTON_REGISTRO_Y = 80;
		private final int BOTON_REGISTRO_ANCHO = 80;
		private final int BOTON_REGISTRO_ALTO = 80;
		
		//Boton Info
		private final int BOTON_INFO_X = 560;
		private final int BOTON_INFO_Y = 320;
		private final int BOTON_INFO_ANCHO = 80;
		private final int BOTON_INFO_ALTO = 80;
		
		//Boton Creditos
		private final int BOTON_CREDITOS_X = 400;
		private final int BOTON_CREDITOS_Y = 320;
		private final int BOTON_CREDITOS_ANCHO = 80;
		private final int BOTON_CREDITOS_ALTO = 80;
		
		//Boton Estadisticas
		private final int BOTON_ESTADISTICAS_X = 80;
		private final int BOTON_ESTADISTICAS_Y = 80;
		private final int BOTON_ESTADISTICAS_ANCHO = 80;
		private final int BOTON_ESTADISTICAS_ALTO = 80;
		
		//Boton jUGAR
		private final int BOTON_JUGAR_X = 200;
		private final int BOTON_JUGAR_Y = 160;
		private final int BOTON_JUGAR_ANCHO = 320;
		private final int BOTON_JUGAR_ALTO = 160;
				
				
 
        // ===========================================================
        // Fields
        // ===========================================================
		private Sprite  botonAjustes, botonRegistro, 
					botonInfo, botonCreditos, botonEstadisticas, botonJugar;
		private Background background;
		
		private Ventana ventana;  //Objeto para manejar las ventanas, solo podra haber una de cada vez
		
   
        
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
			this.detachChild(botonAjustes);
			botonAjustes.dispose();
			this.detachChild(botonCreditos);
			botonCreditos.dispose();
			this.detachChild(botonInfo);
			botonInfo.dispose();
			this.detachChild(botonRegistro);
			botonRegistro.dispose();
			this.detachChild(botonEstadisticas);
			botonEstadisticas.dispose();
			this.detachChild(botonJugar);
			botonJugar.dispose();
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {
			

			botonAjustes = new SpriteButton(BOTON_AJUSTES_X, BOTON_AJUSTES_Y, BOTON_AJUSTES_ANCHO, BOTON_AJUSTES_ALTO,
					resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_SETTINGS_ID], vbom, 
					new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							ArrayList<ContenidoVentana> contenidos = new ArrayList<ContenidoVentana>();
							contenidos.add(new TestContenidoVentana(MainMenuScene.this, Color.BLUE));
							contenidos.add(new TestContenidoVentana(MainMenuScene.this, Color.RED));
							ventana = new Ventana(MainMenuScene.this, 
									resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_VENTANA_CERRAR_ID], 
									resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_VENTANA_ATRAS_ID], 
									resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_VENTANA_ADELANTE_ID],
									contenidos, true, MainMenuScene.this);
							ventana.show();
						}
					}
			);
			

			
			botonRegistro = new SpriteButton(BOTON_REGISTRO_X, BOTON_REGISTRO_Y, BOTON_REGISTRO_ANCHO, BOTON_REGISTRO_ALTO,
					resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_REGISTRO_ID], vbom,
					new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							botonJugar.registerEntityModifier(new AlphaModifier(2.0f, 0.0f, 1.0f));
						}
					}
			); 

			
			botonInfo = new SpriteButton(BOTON_INFO_X, BOTON_INFO_Y, BOTON_INFO_ANCHO, BOTON_INFO_ALTO,
					resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_AYUDA_ID], 
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							
						}
					}
			);
			
			botonCreditos = new SpriteButton(BOTON_CREDITOS_X, BOTON_CREDITOS_Y,BOTON_CREDITOS_ANCHO,BOTON_CREDITOS_ALTO,
					resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_CREDITOS_ID], 
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							
						}
					}
			);
			
			botonEstadisticas = new SpriteButton(BOTON_ESTADISTICAS_X, BOTON_ESTADISTICAS_Y, BOTON_ESTADISTICAS_ANCHO,BOTON_ESTADISTICAS_ALTO,
					resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_ESTADISTICAS_ID], 
					vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							
						}
					}
			);

			
			botonJugar = new SpriteButton(BOTON_JUGAR_X, BOTON_JUGAR_Y,BOTON_JUGAR_ANCHO,BOTON_JUGAR_ALTO,
				resourcesManager.texturasMainMenu[ResourcesManager.MAIN_MENU_BOTON_JUGAR_ID], 
				vbom, new OnSpriteButtonCallback() {
					@Override
					public void botonPulsado() {
						SceneManager.getInstance().mainMenuScene_to_secondMenuScene();
					}
				}
			);
			
			background = new Background(Color.GREEN);
			
		}
		

		private void createBackground() {
			
			this.setBackground(background);
			
			//Añado los sprites 
			this.attachChild(botonAjustes);
			this.attachChild(botonRegistro);
			this.attachChild(botonInfo);
			this.attachChild(botonCreditos);
			this.attachChild(botonEstadisticas);
			this.attachChild(botonJugar);
			
			//Registrar botones listener
			registrarBotonesListener(true);

		}
		
		
		private void registrarBotonesListener(boolean registrar) {
			//Registro los listener
			if (registrar) {
				this.registerTouchArea(botonAjustes);
				this.registerTouchArea(botonCreditos);
				this.registerTouchArea(botonInfo);
				this.registerTouchArea(botonRegistro);
				this.registerTouchArea(botonEstadisticas);
				this.registerTouchArea(botonJugar);
			}
			else {
				this.unregisterTouchArea(botonAjustes);
				this.unregisterTouchArea(botonCreditos);
				this.unregisterTouchArea(botonInfo);
				this.unregisterTouchArea(botonRegistro);
				this.unregisterTouchArea(botonEstadisticas);
				this.unregisterTouchArea(botonJugar);
			}
		}
		
		
		//------------------------------------------------
		//					INTERFACE METHODS
		//------------------------------------------------
		
		@Override
		public void onOpen() {
			registrarBotonesListener(false);
		};
		
		
		@Override
		public void onClose() {
			if (ventana != null) {
				ventana.hide();
			}
		}
		
		@Override
		public void onFinishClose() {
			if (ventana != null) {
				ventana.dispose();
				ventana = null;
			}
			registrarBotonesListener(true);
		}


}