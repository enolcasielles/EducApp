package com.enolcasielles.educados;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.R.integer;

import com.enolcasielles.educados.niveles.InfoNiveles;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.GameScene;
import com.enolcasielles.educados.scenes.MainMenuScene;
import com.enolcasielles.educados.scenes.WorldScene;



public class SceneManager
{
	
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    private BaseScene menuScene;
    private BaseScene gameScene;
    public BaseScene worldScene;

    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCIA = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_MENU;
    
    private BaseScene escenaActual;  //Escena actual
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType
    {
        SCENE_MENU,
        SCENE_GAME,
        SCENE_WORLD
    }
    
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    /**
     * Efectua un cambio de escena a la pasada por parametro.
     * No se debe utilizar este metodo directamente ya que se esta saltando todo el
     * proceso de carga/descarga de recursos. En cambio se deben de utilizar los metodos
     * definidos para cada posible cambio de escena o si se generan nuevas escenas definir los cambios
     * siguiendo el mismo criterio
     * @param scene La escena a la que se quiere cambiar
     */
    public void cambiar_a_escena(BaseScene scene)
    {
        engine.setScene(scene);
        escenaActual = scene;
        currentSceneType = scene.getSceneType();
    }
    
    
    /**
     * Equivalente al anterior pero inidciando la escena por su tipo
     * @param sceneType La escena a la que se quiere cambiar definida por su tipo
     */
    public void cambiar_a_escena(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
            	cambiar_a_escena(menuScene);
                break;
            case SCENE_GAME:
            	cambiar_a_escena(gameScene);
                break;
            case SCENE_WORLD:
            	cambiar_a_escena(worldScene);
                break;
            default:
                break;
        }
    }
    
    
    
    
    /**
     * Carga el menu la primera vez que se inicia la app
     * 
     */
    public void init_to_menuScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadMenuResources();
        InfoNiveles.init();
        menuScene = new MainMenuScene();
        escenaActual = menuScene;
        pOnCreateSceneCallback.onCreateSceneFinished(menuScene);
    }
    
     
    /**
     * Efectua el cambio de escena de menu a world
     * @param mundo El mundo que ha de cargar
     */
    public void menuScene_to_worldScene(int mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	WorldScene.setMundo(InfoNiveles.MUNDO_1);
    	worldScene = new WorldScene(mundo);
    	cambiar_a_escena(worldScene);
    	ResourcesManager.getInstance().unloadMenuResources();
    	menuScene.disposeScene();
    	menuScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de world a menu
     */
    public void worldScene_to_menuScene() {
    	ResourcesManager.getInstance().loadMenuResources();
    	menuScene = new MainMenuScene();
    	cambiar_a_escena(menuScene);
    	ResourcesManager.getInstance().unloadWorldResources();
    	worldScene.disposeScene();
    	worldScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de world a game
     * @param mundo El mundo desde el que se esta efectuando el cambio, el mundo que se quiere cargar
     * @param nivel El nivel a cargar
     */
    public void worldScene_to_gameScene(int mundo, int nivel) {
    	ResourcesManager.getInstance().loadGameResources();
    	GameScene.setNivel(mundo, nivel);
    	gameScene = new GameScene();
    	cambiar_a_escena(gameScene);
    	ResourcesManager.getInstance().unloadWorldResources();
    	worldScene.disposeScene();
    	worldScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de game a world
     * @param mundo El mundo en el que se esta para poder formarlo
     */
    public void gameScene_to_worldScene(int mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	worldScene = new WorldScene(mundo);
    	cambiar_a_escena(worldScene);
    	ResourcesManager.getInstance().unloadGameResources();
    	gameScene.disposeScene();
    	gameScene = null;
    }
    

    
    /**
     * Finaliza la aplicacion
     */
    public void menuScene_to_exit() {
    	System.exit(0);
    }
    
    
      
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCIA;
    }

    
    public BaseScene getCurrentScene()
    {
        return escenaActual;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
}