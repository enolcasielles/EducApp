package com.enolcasielles.educados.ventanas;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.Constants;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.utiles.SpriteButton;
import com.enolcasielles.educados.utiles.SpriteButton.OnSpriteButtonCallback;
import com.enolcasielles.educados.ventanas.ContenidoVentana.OnContenidoCallback;


/**
 * Clase que sirve de contenedor de objetos Contenido. Define una ventana con una animacion por 
 * defecto para mostrarse en la escena. Define un boton que cerrara la misma. Da la posibiilidad de
 * mostrar unos botones de navegacion en su parte inferior que permitira al usuario nevegar entre los
 * distintos contenidos. 
 * 
 * @author Enol Casielles
 *
 */
public class Ventana extends Rectangle implements OnContenidoCallback{

	//Contantes que definen la posicion de la ventana
	private static final float POS_X = 185;
	private static final float POS_Y = 50;
	private static final float ANCHO = 350;
	private static final float ALTO = 380;
	
	//Constantes que definen las dimensiones del boton cerrar
	private static final float CERRAR_X = 0;
	private static final float CERRAR_Y = 0;
	private static final float CERRAR_ANCHO = 50;
	private static final float CERRAR_ALTO = 50;
	
	//Constantes que definen las dimensiones del boton atras
	private static final float ATRAS_X = 0;
	private static final float ATRAS_ALTO = 50;
	private static final float ATRAS_Y = Ventana.ALTO - Ventana.ATRAS_ALTO;
	private static final float ATRAS_ANCHO = 100;
	
	//Constantes que definen las dimensiones del boton adelante
	private static final float ADELANTE_ANCHO = 100;
	private static final float ADELANTE_X = Ventana.ANCHO - Ventana.ADELANTE_ANCHO;
	private static final float ADELANTE_ALTO = 50;
	private static final float ADELANTE_Y = Ventana.ALTO - Ventana.ATRAS_ALTO;
	
	//La escena en la que se añade
	private BaseScene scene;
	
	//Contenedor de los contenidos
	private ArrayList<ContenidoVentana> contenidos;
	private int contenidoActual, contenidoAnterior;
	
	//Variables para el boton de cerrar y los botones de navegacion
	private Sprite botonCerrar, botonNavegaAdelante, botonNavegaAtras;
	
	//Variable que define si mostrar botones de navegacion o no
	private boolean hayBotonesNavegacion;
	
	//Objeto delegado
	private final OnVentanaCallback delegado;
	
	
	/**
	 * Contruye la ventana y se añade a la escena con la animacion implementada
	 * 
	 * @param scene La escena a la que añadirse
	 * @param botonCerrarTextura La textura para el boton de cerrarse. Ver dimensiones en constantes. Se reescalara a estas
	 * @param botonAtrasTextura La textura para el boton de navegacion hacia atras. Ver dimensiones. Se reescalara a estas
	 * @param botonAdelanteTextura La textura para el boton de navegar hacia adelante. Ver dimensiones. Se reescalara a estas
	 * @param contenidos El contenedor de contentenidos que llevara la ventana. El orden del mismo define el orden de navegacion
	 * @param botonesNavegacion true si se quiere mostrar botones o false si se quieres dejar ocultos
	 */
	public Ventana(BaseScene scene, ITextureRegion botonCerrarTextura, ITextureRegion botonAtrasTextura,
			ITextureRegion botonAdelanteTextura, final ArrayList<ContenidoVentana> contenidos,
			boolean botonesNavegacion, final OnVentanaCallback delegado) {
		super(POS_X,Constants.ALTO_CAMARA,ANCHO,ALTO,scene.vbom);
		
		//Almaceno la escena
		this.scene = scene;
		
		//Almaceno el delegado
		this.delegado = delegado;
		
		this.hayBotonesNavegacion = botonesNavegacion;
		
		//Almaceno los contenidos
		this.contenidos = contenidos;
		contenidoActual = 0;  //Apunto al primer contenido 
		
		//Añado los contenidos a la ventana, desactivo su control, los hago invisibles y me declaro como su delegado
		for (ContenidoVentana contenido : contenidos) {
			this.attachChild(contenido);
			contenido.setPosition(0.0f, CERRAR_ALTO);
			contenido.desactivaControl();
			contenido.setVisible(false);
			contenido.setDelegado(this);
		}
		
		//Muestro el primer contenido
		ContenidoVentana contenido = this.contenidos.get(contenidoActual);
		contenido.activaControl();
		contenido.setVisible(true);
		

		//Configuro botones
		botonCerrar = new SpriteButton(CERRAR_X,CERRAR_Y,CERRAR_ANCHO,CERRAR_ALTO,
				botonCerrarTextura,scene.vbom, new OnSpriteButtonCallback() {
					@Override
					public void botonPulsado() {
						if (delegado!=null) delegado.onClose();
					}
				}
		);
		this.attachChild(botonCerrar);
		
		
		if (botonesNavegacion) {
			botonNavegaAtras = new SpriteButton(ATRAS_X, ATRAS_Y, ATRAS_ANCHO,ATRAS_ALTO,
					botonAtrasTextura,scene.vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							retrocedePagina();
						}
					}
			);
			this.attachChild(botonNavegaAtras);
			
			botonNavegaAdelante = new SpriteButton(ADELANTE_X,ADELANTE_Y,ADELANTE_ANCHO,ADELANTE_ALTO,
					botonAdelanteTextura,scene.vbom, new OnSpriteButtonCallback() {
						@Override
						public void botonPulsado() {
							avanzaPagina();
						}
					}
			);
			this.attachChild(botonNavegaAdelante);
			
		}
		
		//Añado la ventana a la escena
		scene.attachChild(this);
		
	}
	
	
	/**
	 * Elimina la ventana
	 */
	public void dispose() {
		//Elimino el boton de cerrar
		botonCerrar.dispose();
		botonCerrar.detachSelf();
		
		//Elimino los botones de navegacion
		botonNavegaAdelante.dispose();
		botonNavegaAdelante.detachSelf();
		botonNavegaAtras.dispose();
		botonNavegaAtras.detachSelf();
		
		//Elimino los contenidos
		for(ContenidoVentana contenido : contenidos) {
			contenido.dispose();
			contenido.detachSelf();
		}
		
	}
	
	
	/**
	 * Metodo que muestra la ventana
	 */
	public void show() {
		this.registerEntityModifier(new MoveYModifier(0.2f, Constants.ALTO_CAMARA, POS_Y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				// TODO Auto-generated method stub
				super.onModifierFinished(pItem);
				delegado.onOpen();
			}
		});
		scene.registerTouchArea(botonCerrar);
		if (hayBotonesNavegacion) {
			scene.registerTouchArea(botonNavegaAdelante);
			scene.registerTouchArea(botonNavegaAtras);
		}
	}
	
	
	/**
	 * Metodo que oculta la ventana
	 */
	public void hide() {
		this.registerEntityModifier(new MoveYModifier(0.2f, POS_Y,Constants.ALTO_CAMARA) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				delegado.onFinishClose();
			}
		});
		scene.unregisterTouchArea(botonCerrar);
		if (hayBotonesNavegacion) {
			scene.unregisterTouchArea(botonNavegaAdelante);
			scene.unregisterTouchArea(botonNavegaAtras);
		}
	}
	
	
	
	/**
	 * Interface que define el protocolo a implementar por el objeto delegado al que se le informara de las accioens que ocurran
	 * @author Enol Casielles
	 *
	 */
	public interface OnVentanaCallback {
		
		/**
		 * Sera ejecutado cuando la ventana se haya abierto
		 */
		public abstract void onOpen();
		
		/**
		 * Sera ejecutado cundo el usuario pulse el boton de cerrar
		 */
		public abstract void onClose();
		
		/**
		 * Sera ejecutado cuando finalice de cerrarse la ventana
		 */
		public abstract void onFinishClose();
	}
	
	
	
	//---------------------------------------------------
	//				INTERFACE METHODS
	//---------------------------------------------------
	@Override
	public void contenidoOcultado() {
		contenidos.get(contenidoActual).setVisible(true);  //Hago visible el contenido actual
		contenidos.get(contenidoActual).mostrar(); //Y muestro el actual
		contenidos.get(contenidoActual).activaControl();
		contenidos.get(contenidoAnterior).setVisible(false); //Hago invisible el anterior
	};
	
	
	@Override
	public void retrocedePagina() {
		if (contenidoActual <= 0) return;
		contenidoAnterior = contenidoActual; //Apunto el contenido anterior
		contenidoActual--;  //Actualizo el contenido actual
		contenidos.get(contenidoAnterior).ocultar(); //Oculto el anterior
		contenidos.get(contenidoAnterior).desactivaControl();  //Desactivo el control en el anterio
	}
	
	
	@Override
	public void avanzaPagina() { 
		if (contenidoActual >= contenidos.size()-1) return;
		contenidoAnterior = contenidoActual;  //Apunto el contenido anterior
		contenidoActual++;  //Actualizo el contenido actual
		contenidos.get(contenidoAnterior).ocultar(); //Oculto el anterior
		contenidos.get(contenidoAnterior).desactivaControl();  //Desactivo el control en el anterio
	}

}
