package com.enolcasielles.educados.scenes;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.SingleValueSpanEntityModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 23:13:01 - 19.03.2010
 */
public class ZoomModifier extends SingleValueSpanEntityModifier {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private ZoomCamera camera;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ZoomModifier(final float pDuration, final float pFromAlpha, final float pToAlpha, final ZoomCamera camera) {
		this(pDuration, pFromAlpha, pToAlpha, null, EaseLinear.getInstance(), camera);
	}

	public ZoomModifier(final float pDuration, final float pFromAlpha, final float pToAlpha, final IEaseFunction pEaseFunction, final ZoomCamera camera) {
		this(pDuration, pFromAlpha, pToAlpha, null, pEaseFunction, camera);
	}

	public ZoomModifier(final float pDuration, final float pFromAlpha, final float pToAlpha, final IEntityModifierListener pEntityModifierListener, final ZoomCamera camera) {
		super(pDuration, pFromAlpha, pToAlpha, pEntityModifierListener, EaseLinear.getInstance());
		this.camera = camera;
	}

	public ZoomModifier(final float pDuration, final float pFromAlpha, final float pToAlpha, final IEntityModifierListener pEntityModifierListener, final IEaseFunction pEaseFunction, final ZoomCamera camera) {
		super(pDuration, pFromAlpha, pToAlpha, pEntityModifierListener, pEaseFunction);
		this.camera = camera;
	}

	protected ZoomModifier(final ZoomModifier pAlphaModifier) {
		super(pAlphaModifier);
	}

	@Override
	public ZoomModifier deepCopy(){
		return new ZoomModifier(this);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onSetInitialValue(final IEntity pEntity, final float pZoom) {
		camera.setZoomFactor(pZoom);
	}

	@Override
	protected void onSetValue(final IEntity pEntity, final float pPercentageDone, final float pZoom) {
		camera.setZoomFactor(pZoom);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}