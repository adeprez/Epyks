package com.deprezal.epyks;

import org.rajawali3d.Object3D;

public interface ARObject {
	Object3D as3D();
	void onAdded(ARRenderer renderer);
	void onRemoved();
	void onEnter();
	void onLeave();
	void onAction();
	ARObject getLookingAt();
}
