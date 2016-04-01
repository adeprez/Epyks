package com.deprezal.epyks.ui;

import android.util.Log;
import android.view.animation.Interpolator;

import com.deprezal.epyks.ARObject;
import com.deprezal.epyks.ARRenderer;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.IAnimationListener;
import org.rajawali3d.animation.ScaleAnimation3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Plane;

public class ARComponent extends Plane implements ARObject, IAnimationListener {
	protected boolean animated, active, interactingWith;
	protected ARRenderer renderer;
	protected ARContainer parent;
	protected Material material;
	protected float distance;

	public ARComponent(float width, float height) {
		super(width, height, 1, 1);
		distance = 2f;
		material = new Material();
		setMaterial(material);
		material.setDiffuseMethod(new DiffuseMethod.Lambert());
		material.enableLighting(true);
	}

	public double getBottom() {
		return getY() - mHeight;
	}

	public boolean isActive() {
		return active;
	}

	public void setColor(float red, float green, float blue, float alpha) {
		if(alpha < 1)
			setTransparent(true);
		material.setColor(new float[]{red, green, blue, alpha});
	}

	public void setFrontOfCamera() {
		setPosition(renderer.getInFrontOf(distance));
		setLookAt(renderer.getForwardVec());
	}

	public void addTexture(ATexture texture) {
		try {
			material.addTexture(texture);
		} catch(ATexture.TextureException e) {
			Log.e("err", "texture", e);
		}
	}

	public boolean open(ARRenderer renderer) {
		if(!active && !animated) {
			if(renderer.add(this)) {
				active = true;
				return true;
			}
		}
		return false;
	}

	public boolean close() {
		if(active && removeFromRenderer()) {
			active = false;
			return true;
		}
		return false;
	}

	public boolean removeFromRenderer() {
		return renderer.remove(this);
	}

	public boolean toggle(ARRenderer renderer) {
		return active ? close() : open(renderer);
	}

	public boolean isAdded() {
		return renderer != null;
	}

	public ARContainer getARParent() {
		return parent;
	}

	public float getHeight() {
		return mHeight;
	}

	public float getWidth() {
		return mWidth;
	}

	public boolean isOver() {
		return renderer.getOver() == this;
	}

	public boolean isInteractingWith() {
		return interactingWith;
	}

	public ScaleAnimation3D scale(double zoom, int duration, Interpolator interpolator, boolean listen) {
		ScaleAnimation3D anim = new ScaleAnimation3D(new Vector3(zoom, zoom, zoom));
		if(listen) {
			animated = true;
			anim.registerListener(this);
		}
		anim.setDurationMilliseconds(duration);
		anim.setTransformable3D(as3D());
		anim.setRepeatMode(Animation.RepeatMode.NONE);
		anim.setInterpolator(interpolator);
		anim.play();
		renderer.getCurrentScene().registerAnimation(anim);
		return anim;
	}

	@Override
	public void onAdded(ARRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void onRemoved() {
	}

	@Override
	public void onEnter() {
		interactingWith = true;
		if(parent != null && !parent.isInteractingWith())
			parent.onEnter();
	}

	@Override
	public void onLeave() {
		interactingWith = false;
	}

	@Override
	public void onAction() {
	}

	@Override
	public ARObject getLookingAt() {
		return this;
	}

	@Override
	public void stillLookingAt(int time) {
	}

	@Override
	public Object3D as3D() {
		return this;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		renderer.remove(this);
		animated = false;
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationUpdate(Animation animation, double interpolatedTime) {
	}
}
