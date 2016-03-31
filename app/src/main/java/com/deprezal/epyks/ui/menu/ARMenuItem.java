package com.deprezal.epyks.ui.menu;

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
		setColor(.5f, .5f, .9f, .85f);
	}

	@Override
	public void onLeave() {
		setColor(0, 0, .2f, .5f);
	}
}
