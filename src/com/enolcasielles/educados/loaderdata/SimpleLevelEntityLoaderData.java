package com.enolcasielles.educados.loaderdata;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.enolcasielles.educados.loaderdata.*;

/**
 * (c) 2012 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 16:13:09 - 19.04.2012
 */
public class SimpleLevelEntityLoaderData implements IEntityLoaderData {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final VertexBufferObjectManager mVertexBufferObjectManager;

	// ===========================================================
	// Constructors
	// ===========================================================

	public SimpleLevelEntityLoaderData(final VertexBufferObjectManager pVertexBufferObjectManager) {
		this.mVertexBufferObjectManager = pVertexBufferObjectManager;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return this.mVertexBufferObjectManager;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
