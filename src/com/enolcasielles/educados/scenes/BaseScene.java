package com.enolcasielles.educados.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

import com.enolcasielles.educados.ResourcesManager;
import com.enolcasielles.educados.SceneManager.SceneType;


public abstract class BaseScene extends Scene
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    public Engine engine;
    public Activity activity;
    public ResourcesManager resourcesManager;
    public VertexBufferObjectManager vbom;
    public ZoomCamera camera;
    
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.actividad;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camara;
        createScene();
        //Preparo escena para que sus TouchAreas sigan recibiendo eventos si se sale de ellos
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract void disposeScene();
    
    public abstract SceneType getSceneType();
  
}