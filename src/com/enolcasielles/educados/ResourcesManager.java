package com.enolcasielles.educados;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.R.integer;



public class ResourcesManager{
	
    //---------------------------------------------
    // CONSTANTS
    //---------------------------------------------
	private final String RUTA_SONIDO_BOTON = "Sonidos/boton.wav";
	private final String RUTA_SONIDO_CARGAR = "Sonidos/cargar.mp3";
	private final String RUTA_SONIDO_SALTAR = "Sonidos/saltar.mp3";
	private final String RUTA_MUSICA = "Musica/musica.wav";
	
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCIA = new ResourcesManager();
    
    public Engine engine;
    public GameActivity actividad;
    public ZoomCamera camara;
    public VertexBufferObjectManager vbom;
    
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------

    
    //Menu   
    private TexturePackTextureRegionLibrary texturePackLibraryMenu;
	private TexturePack texturePackMenu;
	public ITextureRegion[] texturasMenu;
	public static final int MENU_BACKGROUND_ID = 0;
	public static final int MENU_CREDITOS_ID = 1;
	public static final int MENU_INFO_ID = 2;
	public static final int MENU_MUNDO_MATE_ID = 4;
	public static final int MENU_MUNDO_LENGUA_ID = 3;
	public static final int MENU_MUNDO_NATU_ID = 5;
	public static final int MENU_REGISTRO_ID = 6;
	public static final int MENU_SETTINGS_ID = 7;
    
	//World 
    private TexturePackTextureRegionLibrary texturePackLibraryWorld;
	private TexturePack texturePackWorld;
	public ITextureRegion[] texturasWorld;
	public static final int WORLD_BARCO_ID = 0;
	public static final int WORLD_NIVEL_ID = 1;
	public static final int WORLD_WORLD_ID = 2;
	
	//Teoria
    private TexturePackTextureRegionLibrary texturePackLibraryTeoria;
	private TexturePack texturePackTeoria;
	public ITextureRegion[] texturasTeoria;
	public ITiledTextureRegion[] texturasTiledTeoria;
	public static final int TEORIA_BACKGROUND_ID = 0;
	public static final int TEORIA_BOTONATRAS_ID = 1;
	public static final int TEORIA_BOTONJUGAR_ID = 2;
	public static final int TEORIA_BOTONMENU_ID = 3;
	public static final int TEORIA_BOTONSIGUIENTE_ID = 4;
	public static final int TEORIA_CONTENIDO_ID = 5;
	public static final int TEORIA_INDICADOR_ID = 6;
	public static final int TEORIA_LORO_ID = 7;
	public static final int TEORIA_TITULO_ID = 8;
	
	
	//Evaluacion
	public ITextureRegion[] texturasEvaluacion;
	
	
	//---------------------------------------------
	// SOUND
	//---------------------------------------------
	public Sound sonidoBoton;
	public Sound sonidoCargar;
	public Sound sonidoSaltar;
	public Music musica;
	
	
	
	//---------------------------------------------
	// FONTS
	//---------------------------------------------
	public Font fuenteGame;
	public Font fuenteMenu;
	public Font fuenteLoading;
	public Font fuenteEvaluacion;
	
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
	
    public void loadMenuResources() {
    	
    	//--------------------------------------------------
    	//LOAD GRAPHICS
    	//--------------------------------------------------
    	
    	//Parseo el fichero
    	try {
    		  texturePackMenu = new TexturePackLoader(actividad.getTextureManager(), "gfx/menu/")
    		      	.loadFromAsset(actividad.getAssets(), "textura_menu.xml");
    		  texturePackMenu.loadTexture();
    		  texturePackLibraryMenu = texturePackMenu.getTexturePackTextureRegionLibrary();
    	} catch (final TexturePackParseException e)  {
    	      Debug.e(e);
    	}

    	
    	texturasMenu = new ITextureRegion[8];
    	texturasMenu[MENU_BACKGROUND_ID] = texturePackLibraryMenu.get(MENU_BACKGROUND_ID);
    	texturasMenu[MENU_MUNDO_MATE_ID] = texturePackLibraryMenu.get(MENU_MUNDO_MATE_ID);
    	texturasMenu[MENU_MUNDO_LENGUA_ID] = texturePackLibraryMenu.get(MENU_MUNDO_LENGUA_ID);
    	texturasMenu[MENU_MUNDO_NATU_ID] = texturePackLibraryMenu.get(MENU_MUNDO_NATU_ID);
    	texturasMenu[MENU_SETTINGS_ID] = texturePackLibraryMenu.get(MENU_SETTINGS_ID);
    	texturasMenu[MENU_REGISTRO_ID] = texturePackLibraryMenu.get(MENU_REGISTRO_ID);
    	texturasMenu[MENU_INFO_ID] = texturePackLibraryMenu.get(MENU_INFO_ID);
    	texturasMenu[MENU_CREDITOS_ID] = texturePackLibraryMenu.get(MENU_CREDITOS_ID);
    	
    	//--------------------------------------------------
    	//LOAD FONTS
    	//--------------------------------------------------
    	FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
    	final ITexture fontTextureMenu = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteMenu = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureMenu,
    				actividad.getAssets(), "Droid.ttf", 40, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteMenu.load();  //La cargamos
    	
    	//--------------------------------------------------
    	//LOAD AUDIO
    	//--------------------------------------------------
    
    }
    
    public void unloadMenuResources() {
    	texturePackMenu.unloadTexture();
    	texturePackLibraryMenu = null;
    	for (int i=0 ; i<texturasMenu.length ; i++) {
    		texturasMenu[i]= null; 
    	}
    	fuenteMenu.unload();
    	fuenteMenu = null;
    	
    }
    
    public void loadWorldResources() {
    	
    	//--------------------------------------------------
    	//LOAD GRAPHICS
    	//--------------------------------------------------
    	
    	//Parseo el fichero
    	try {
    		  texturePackWorld = new TexturePackLoader(actividad.getTextureManager(), "gfx/world/")
    		      	.loadFromAsset(actividad.getAssets(), "textura_world.xml");
    		  texturePackWorld.loadTexture();
    		  texturePackLibraryWorld = texturePackWorld.getTexturePackTextureRegionLibrary();
    	} catch (final TexturePackParseException e)  {
    	      Debug.e(e);
    	}

    	texturasWorld = new ITextureRegion[3];
    	texturasWorld[WORLD_BARCO_ID] = texturePackLibraryWorld.get(WORLD_BARCO_ID);
    	texturasWorld[WORLD_NIVEL_ID] = texturePackLibraryWorld.get(WORLD_NIVEL_ID);
    	texturasWorld[WORLD_WORLD_ID] = texturePackLibraryWorld.get(WORLD_WORLD_ID);
    	
    	//--------------------------------------------------
    	//LOAD FONTS
    	//--------------------------------------------------
    	
    	
    	//--------------------------------------------------
    	//LOAD AUDIO
    	//--------------------------------------------------
    	
    }
    
    public void unloadWorldResources() {
    	texturePackWorld.unloadTexture();
    	texturePackLibraryWorld = null;
    	for (int i=0 ; i<texturasWorld.length ; i++) {
    		texturasWorld[i]= null; 
    	}
    }
    
    public void loadTeoriaResources() {
    	
    	//--------------------------------------------------
    	//LOAD GRAPHICS
    	//--------------------------------------------------
    	
    	//Parseo el fichero
    	try {
    		  texturePackTeoria = new TexturePackLoader(actividad.getTextureManager(), "gfx/teoria/")
    		      	.loadFromAsset(actividad.getAssets(), "textura_teoria.xml");
    		  texturePackTeoria.loadTexture();
    		  texturePackLibraryTeoria = texturePackTeoria.getTexturePackTextureRegionLibrary();
    	} catch (final TexturePackParseException e)  {
    	      Debug.e(e);
    	}
    			
		
    	texturasTeoria = new ITextureRegion[9];
    	texturasTiledTeoria = new ITiledTextureRegion[9];
    	
    	//Animados
    	texturasTiledTeoria[TEORIA_LORO_ID] = texturePackLibraryTeoria.get(TEORIA_LORO_ID, 2, 1);
    	texturasTiledTeoria[TEORIA_BOTONATRAS_ID] = texturePackLibraryTeoria.get(TEORIA_BOTONATRAS_ID, 1, 2);
    	texturasTiledTeoria[TEORIA_BOTONMENU_ID] = texturePackLibraryTeoria.get(TEORIA_BOTONMENU_ID, 1, 2);
    	texturasTiledTeoria[TEORIA_BOTONJUGAR_ID] = texturePackLibraryTeoria.get(TEORIA_BOTONJUGAR_ID, 1, 2);
    	texturasTiledTeoria[TEORIA_BOTONSIGUIENTE_ID] = texturePackLibraryTeoria.get(TEORIA_BOTONSIGUIENTE_ID, 1, 2);

    	//Estaticos
    	texturasTeoria[TEORIA_INDICADOR_ID] = texturePackLibraryTeoria.get(TEORIA_INDICADOR_ID);
    	texturasTeoria[TEORIA_BACKGROUND_ID] = texturePackLibraryTeoria.get(TEORIA_BACKGROUND_ID);
    	texturasTeoria[TEORIA_CONTENIDO_ID] = texturePackLibraryTeoria.get(TEORIA_CONTENIDO_ID);
    	texturasTeoria[TEORIA_TITULO_ID] = texturePackLibraryTeoria.get(TEORIA_TITULO_ID);
    	
    	
    	//--------------------------------------------------
    	//LOAD FONTS
    	//--------------------------------------------------
		FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
		//Cargamos la fuente del game
    	final ITexture fontTextureGame = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteGame = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureGame,
    				actividad.getAssets(), "Droid.ttf", 25, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteGame.load();  //La cargamos
    	
    	//--------------------------------------------------
    	//LOAD AUDIO
    	//--------------------------------------------------
    	//Cargamos los sonidos
    	try {
    		sonidoSaltar = SoundFactory.createSoundFromAsset(actividad.getEngine().getSoundManager(), 
    				actividad, RUTA_SONIDO_SALTAR);
    		sonidoSaltar.setLooping(false);  
    		sonidoCargar = SoundFactory.createSoundFromAsset(actividad.getEngine().getSoundManager(), 
    				actividad, RUTA_SONIDO_CARGAR);
    		sonidoCargar.setLooping(false);
    		sonidoCargar.setVolume(0.5f);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	//Cargamos la musica
    	try {
    		musica = MusicFactory.createMusicFromAsset(actividad.getEngine().getMusicManager(), 
    				actividad, RUTA_MUSICA);
    		musica.setLooping(true);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}	
    }
    
    
    public void unloadTeoriaResources() {
    	texturePackTeoria.unloadTexture();
    	texturePackLibraryTeoria = null;
    	for (int i=0 ; i<texturasTeoria.length ; i++) {
    		texturasTeoria[i]= null; 
    	}
  
    	
    	fuenteGame.unload();
    	fuenteGame = null;
 
    	sonidoCargar = null;
    	sonidoSaltar = null;
    	musica = null;
    	
    }
    
    
    
    
    
    public void loadEvaluacionResources() {
    	//--------------------------------------------------
    	//LOAD FONTS
    	//--------------------------------------------------
		FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
		//Cargamos la fuente del game
    	final ITexture fontTextureGame = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteEvaluacion = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureGame,
    				actividad.getAssets(), "Droid.ttf", 25, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteEvaluacion.load();  //La cargamos
    }
    
    
    public void unloadEvaluacionResources() {
    	fuenteEvaluacion.unload();
    	fuenteEvaluacion = null;
    }
    

    
    
    //Incia el manejador
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void iniciar(Engine engine, GameActivity activity, ZoomCamera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().actividad = activity;
        getInstance().camara = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    //Obtiene una instacia del manejador
    public static ResourcesManager getInstance()
    {
        return INSTANCIA;
    }
}
