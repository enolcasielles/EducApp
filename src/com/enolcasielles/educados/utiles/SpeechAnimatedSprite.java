package com.enolcasielles.educados.utiles;

import java.util.HashMap;
import java.util.Locale;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

public class SpeechAnimatedSprite extends AnimatedSprite  {
	
	private final String ID_SPEAK = "1";
	
	private TextToSpeech tts;
	private boolean puedeSpeech;
	private HashMap<String, String> hashAudio;
	private UtteranceProgressListener listener; 
	
	public SpeechAnimatedSprite(Activity activity, float x, float y, Scene scene, ITiledTextureRegion textura, VertexBufferObjectManager vbom) {
		super(x, y, textura, vbom);
		
		tts = new TextToSpeech(activity.getApplicationContext(), 
			      new TextToSpeech.OnInitListener() {
			      @Override
			      public void onInit(int status) {
			         if(status != TextToSpeech.ERROR){
			        	 Locale locSpanish = new Locale("spa", "ES");
			             tts.setLanguage(locSpanish);
			             puedeSpeech = true;   //Cuando sea llamado y si no tiene ningun error se podra ejecutar el procesado
			            }				
			         }
		});
		
		puedeSpeech = false;
		
		this.hashAudio = new HashMap<String, String>();
		
		//Asocio el listener al objeto
		tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
			@Override
			public void onUtteranceCompleted(String utteranceId) {
				Log.i("Enol","Estoy aqui");
				SpeechAnimatedSprite.this.stopAnimation();
				puedeSpeech = true;	
			}
		});

		
	}
	
	
	public void speakText(String text) {
		if (puedeSpeech) {
			this.hashAudio.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "english");
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, this.hashAudio);
			this.animate(200);
			puedeSpeech=false;
		}
	}

}
