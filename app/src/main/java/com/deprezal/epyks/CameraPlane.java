package com.deprezal.epyks;

import org.rajawali3d.Object3D;
import org.rajawali3d.cameras.Camera;
import org.rajawali3d.cameras.Camera2D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.math.Matrix4;
import org.rajawali3d.postprocessing.passes.EffectPass;

public class CameraPlane extends Object3D {
	private Camera2D mCamera;
	private Matrix4 mVPMatrix;
	private EffectPass mEffectPass;

	@SuppressWarnings("deprecation")
	public CameraPlane(android.hardware.Camera camera) {
		android.hardware.Camera.Size s = camera.getParameters().getPreviewSize();
		float ratio = (float) s.width/s.height;
		mCamera = new Camera2D();
		mCamera.setProjectionMatrix(0, 0);
		mVPMatrix = new Matrix4();

		float[] vertices = new float[] {
				-.5f * ratio, .5f, 0,
				.5f * ratio, .5f, 0,
				.5f * ratio, -.5f, 0,
				-.5f * ratio, -.5f, 0
		};
		float[] textureCoords = new float[] {
				0, 0, 1, 0, 1, 1, 0, 1
		};
		float[] normals = new float[] {
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
				0, 0, 1
		};
		int[] indices = new int[] { 0, 2, 1, 0, 3, 2 };

		setData(vertices, normals, textureCoords, null, indices, true);

		mEnableDepthTest = false;
		mEnableDepthMask = false;
	}

	public void render(Camera camera, final Matrix4 vpMatrix, final Matrix4 projMatrix,
					   final Matrix4 vMatrix, final Matrix4 parentMatrix, Material sceneMaterial) {
		final Matrix4 pMatrix = mCamera.getProjectionMatrix();
		final Matrix4 viewMatrix = mCamera.getViewMatrix();
		mVPMatrix.setAll(pMatrix).multiply(viewMatrix);
		super.render(mCamera, mVPMatrix, projMatrix, viewMatrix, null, sceneMaterial);
	}

	@Override
	protected void setShaderParams(Camera camera) {
		super.setShaderParams(camera);
		if(mEffectPass != null)
			mEffectPass.setShaderParams();
	}

	public void setEffectPass(EffectPass effectPass) {
		mEffectPass = effectPass;
	}
}
