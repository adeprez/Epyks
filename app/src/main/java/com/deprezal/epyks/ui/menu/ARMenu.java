package com.deprezal.epyks.ui.menu;

import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.deprezal.epyks.ARRenderer;
import com.deprezal.epyks.ui.ARContainer;
import com.deprezal.epyks.ui.layout.ARLignLayout;

public class ARMenu extends ARContainer<ARMenuItem> {
	private static final float ZOOM = 10;

	public ARMenu(int count) {
		super(6/ZOOM, ARLignLayout.getHeight(count, 1)/ZOOM, new ARLignLayout(ARLignLayout.DEFAULT_V_GAP/ZOOM, 1/ZOOM));
		setColor(.1f, .3f, .7f, .75f);
		distance = 8f;
	}

	public ARMenu(String... menus) {
		this(menus.length);
		for(final String s : menus)
			add(new ARMenuItem(s, 5/ZOOM, 1/ZOOM));
	}

	@Override
	public void onLeave() {
		super.onLeave();
		close();
	}

	@Override
	public boolean removeFromRenderer() {
		scale(1/ZOOM, 200, new DecelerateInterpolator(.5f), true);
		return true;
	}

	@Override
	public void onAdded(ARRenderer renderer) {
		super.onAdded(renderer);
		scale(ZOOM, 1000, new BounceInterpolator(), false);
	}

	@Override
	public boolean open(ARRenderer renderer) {
		if (super.open(renderer)) {
			setFrontOfCamera();
			return true;
		}
		return false;
	}

}
