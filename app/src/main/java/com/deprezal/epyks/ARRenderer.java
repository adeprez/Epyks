package com.deprezal.epyks;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;

import com.deprezal.epyks.ui.menu.ARMenu;
import com.google.vrtoolkit.cardboard.HeadTransform;

import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.AFrameTask;
import org.rajawali3d.vr.renderer.VRRenderer;

import java.util.ArrayList;
import java.util.List;

public class ARRenderer extends VRRenderer {
	private final List<ARObject> objects;
	private final Vector3 forwardVec;
	private StreamingTexture cameraTexture;
	private boolean shouldUpdateCamera;
	private ARMenu menu;


	public ARRenderer(Context context) {
        super(context);
		objects = new ArrayList<>();
		forwardVec = new Vector3();
    }

	public void invalidateCameraTexture() {
		shouldUpdateCamera = true;
	}

	@SuppressWarnings("deprecation")
	public void setCamera(final Camera camera, final SurfaceTexture.OnFrameAvailableListener listener) {
		internalOfferTask(new AFrameTask() {
			@Override
			protected void doTask() {
				cameraTexture = new StreamingTexture("camera", camera, listener);
				CameraPlane screen = new CameraPlane(camera);
				Material m = new Material();
				m.setColorInfluence(0);
				try {
					m.addTexture(cameraTexture);
				} catch(ATexture.TextureException e) {
					Log.e("err", "Add camera texture", e);
				}
				screen.setMaterial(m);
				getCurrentScene().addChildAt(screen, 0);
			}
		});
	}

	public boolean add(ARObject object) {
		if(getCurrentScene().addChild(object.as3D()) && objects.add(object)) {
			object.onAdded(this);
			return true;
		}
		return false;
	}

	public boolean remove(ARObject object) {
		if(getCurrentScene().removeChild(object.as3D()) && objects.remove(object)) {
			object.onRemoved();
			return true;
		}
		return false;
	}

	public void userAction() {
		if (menu != null) internalOfferTask(new AFrameTask() {
			@Override
			protected void doTask() {
				menu.toggle(ARRenderer.this);
			}
		});
	}

	public Vector3 getInFrontOf(float distance) {
		distance = -distance;
		return new Vector3(forwardVec.x * distance, forwardVec.y * distance, forwardVec.z * distance);
	}

	public Vector3 getForwardVec() {
		return forwardVec;
	}

    @Override
    public void initScene() {

		DirectionalLight light = new DirectionalLight(0.2f, -1f, 0f);

		light.setPower(.7f);
		getCurrentScene().addLight(light);

		light = new DirectionalLight(0.2f, 1f, 0f);
		light.setPower(1f);
		getCurrentScene().addLight(light);

		getCurrentCamera().setFarPlane(100);

		getCurrentScene().setBackgroundColor(0);

		menu = new ARMenu("Hello", "World");
    }

	@Override
	public void onNewFrame(HeadTransform headTransform) {
		super.onNewFrame(headTransform);
		mHeadViewQuaternion.fromMatrix(mHeadViewMatrix);
		mHeadViewQuaternion.inverse();
		forwardVec.setAll(0, 0, 1);
		forwardVec.transform(mHeadViewQuaternion);
	}

	@Override
    public void onRender(long elapsedTime, double deltaTime) {
		super.onRender(elapsedTime, deltaTime);
		if(shouldUpdateCamera && cameraTexture != null) {
			shouldUpdateCamera = false;
			cameraTexture.update();
		}
    }

	@Override
	public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

	}

	@Override
	public void onTouchEvent(MotionEvent event) {

	}
}
