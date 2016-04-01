package com.deprezal.epyks.ui.menu;

import android.view.animation.BounceInterpolator;

import com.deprezal.epyks.ui.ARLabel;

public class ARMenuItem extends ARLabel {

	public ARMenuItem(String name, float width, float height) {
		super(name, width, height);
	}

	@Override
	public void onAction() {
		setColor(0, 0, 0, .5f);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		setColor(.5f, .5f, .9f, .85f);
		scale(1.1, 500, new BounceInterpolator(), false);
	}

	@Override
	public void onLeave() {
		super.onLeave();
		setColor(0, 0, .2f, .5f);
		scale(.9, 500, new BounceInterpolator(), false);
	}
}
