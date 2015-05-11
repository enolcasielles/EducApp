package com.enolcasielles.educados.games;

import android.graphics.Rect;

import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Implementa la logica de los juegos de tipo Huecos
 * @author Enol Casielles
 *
 */
public class HuecosGame extends Game {

	public HuecosGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio) {
		super(parser, "juegoHuecos", scene, espacio);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void iniciaObjetos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalizar() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean puedeFinalizar() {
		// TODO Auto-generated method stub
		return false;
	}

}
