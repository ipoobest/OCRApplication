package com.android.orc.ocrapplication.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.orc.ocrapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class CameraActivity extends AppCompatActivity
        implements TextureView.SurfaceTextureListener{

    private static final String TAG = CameraActivity.class.getSimpleName();

    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    private Button buttonCapture;
    private Button buttonFocus;
    private ToggleButton toggleButtonFlash;
    private TextureView textureViewCamera;

    private Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initInstance();
    }

    private void initInstance() {
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonFocus = findViewById(R.id.buttonFocus);
        toggleButtonFlash = findViewById(R.id.toggleButtonFlash);
        textureViewCamera = findViewById(R.id.textureViewCamera);

        buttonCapture.setOnClickListener(view -> takePicture());
        buttonFocus.setOnClickListener(view -> refocus());
        toggleButtonFlash.setOnCheckedChangeListener((buttonView, isChecked) -> toggleNegativeColor(isChecked));
        textureViewCamera.setSurfaceTextureListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (textureViewCamera.isAvailable()) {
            setupCamera(textureViewCamera.getWidth(), textureViewCamera.getHeight());
            startCameraPreview(textureViewCamera.getSurfaceTexture());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textureViewCamera.setSurfaceTextureListener(null);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        setupCamera(width, height);
        startCameraPreview(surfaceTexture);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        stopCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    private void setupCamera(int width, int height) {
        camera = CameraUtil.openCamera(cameraId);
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size bestPreviewSize = CameraUtil.getBestPreviewSize(parameters.getSupportedPreviewSizes(), width, height);
        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        Camera.Size bestPictureSize = CameraUtil.getBestPictureSize(parameters.getSupportedPictureSizes());
        parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);
        if (CameraUtil.isContinuousFocusModeSupported(parameters.getSupportedFocusModes())) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        camera.setParameters(parameters);
        camera.setDisplayOrientation(CameraUtil.getCameraDisplayOrientation(this, cameraId));
        textureViewCamera.setTransform(CameraUtil.getCropCenterScaleMatrix(width, height, bestPreviewSize.width, bestPreviewSize.height));
    }

    private void startCameraPreview(SurfaceTexture surfaceTexture) {
        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error start camera preview: " + e.getMessage());
        }
    }

    private void stopCamera() {
        try {
            camera.stopPreview();
            camera.release();
        } catch (Exception e) {
            Log.e(TAG, "Error stop camera preview: " + e.getMessage());
        }
    }

    private void takePicture() {
        camera.takePicture(this::playShutterSound,
                null,
                null,
                (data, camera) -> {
                    File file = CameraUtil.savePicture(data);
                    int orientation = CameraUtil.getCameraDisplayOrientation(this, cameraId);
                    CameraUtil.setImageOrientation(file, orientation);
                    CameraUtil.updateMediaScanner(this, file);
                });
    }


    private void refocus() {
        camera.autoFocus((success, camera) -> playFocusSound());
    }

    private void toggleNegativeColor(boolean isTurnOn) {
        Camera.Parameters parameters = camera.getParameters();
        List<String> supportedColorEffectList = parameters.getSupportedColorEffects();
        if (supportedColorEffectList != null && supportedColorEffectList.contains(Camera.Parameters.EFFECT_NEGATIVE)) {
            if (isTurnOn) {
                parameters.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
            } else {
                parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
            }
        } else {
            Toast.makeText(this, R.string.negative_color_effect_unavailable, Toast.LENGTH_SHORT).show();
        }
        camera.setParameters(parameters);
    }

    private void playShutterSound() {
        MediaActionSound sound = new MediaActionSound();
        sound.play(MediaActionSound.SHUTTER_CLICK);
    }

    private void playFocusSound() {
        MediaActionSound sound = new MediaActionSound();
        sound.play(MediaActionSound.FOCUS_COMPLETE);
    }
}