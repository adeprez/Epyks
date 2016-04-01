package com.deprezal.epyks;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
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
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, new BaseLoaderCallback(this) {
			@Override
			public void onManagerConnected(int status) {
				switch(status) {
					case LoaderCallbackInterface.SUCCESS:
						//TODO: have fun with openCV
						break;
					case LoaderCallbackInterface.INIT_FAILED:
						Log.e("openCV", "Init Failed");
						break;
					case LoaderCallbackInterface.INSTALL_CANCELED:
						Log.i("openCV", "Install Cancelled");
						break;
					case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
						Log.e("openCV", "Incompatible Version");
						break;
					case LoaderCallbackInterface.MARKET_ERROR:
						Log.e("openCV", "Market Error");
						break;
					default:
						Log.i("openCV", "OpenCV Manager Install");
						super.onManagerConnected(status);
						break;
				}
			}
		});
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
