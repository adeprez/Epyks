package com.deprezal.epyks;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import org.rajawali3d.vr.VRActivity;

@SuppressWarnings("deprecation")
public class ARActivity extends VRActivity implements SurfaceTexture.OnFrameAvailableListener, SurfaceHolder.Callback {
	private ARRenderer renderer;
	private Camera camera;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        renderer = new ARRenderer(this);
		getSurfaceView().getHolder().addCallback(this);
        setRenderer(renderer);
        setConvertTapIntoTrigger(true);
    }

    /**
     * Called when the Cardboard trigger is pulled.
     */
    @Override
    public void onCardboardTrigger() {
		renderer.userAction();
    }

	@Override
	protected void onStop() {
		if(camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		super.onStop();
	}

	@Override
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
		renderer.invalidateCameraTexture();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		camera.setDisplayOrientation(90);
		renderer.setCamera(camera, this);
		camera.startPreview();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
