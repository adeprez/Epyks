package com.deprezal.epyks.ui.menu;

import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.deprezal.epyks.ARRenderer;
import com.deprezal.epyks.ui.ARContainer;
import com.deprezal.epyks.ui.layout.ARLignLayout;

import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.ScaleAnimation3D;
import org.rajawali3d.math.vector.Vector3;

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
	public boolean removeFromRenderer() {
		animated = true;
		Animation3D anim = new ScaleAnimation3D(new Vector3(1/ZOOM, 1/ZOOM, 1/ZOOM));
		anim.setDurationMilliseconds(200);
		anim.setTransformable3D(this);
		anim.setRepeatMode(Animation.RepeatMode.NONE);
		anim.setInterpolator(new DecelerateInterpolator(.5f));
		anim.registerListener(this);
		anim.play();
		renderer.getCurrentScene().registerAnimation(anim);
		return true;
	}

	@Override
	public void onAdded(ARRenderer renderer) {
		super.onAdded(renderer);
		Animation3D anim = new ScaleAnimation3D(new Vector3(ZOOM, ZOOM, ZOOM));
		anim.setDurationMilliseconds(1000);
		anim.setTransformable3D(this);
		anim.setRepeatMode(Animation.RepeatMode.NONE);
		anim.setInterpolator(new BounceInterpolator());
		anim.play();
		renderer.getCurrentScene().registerAnimation(anim);
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
