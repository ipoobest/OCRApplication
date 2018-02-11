package com.android.orc.ocrapplication.camera;


import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.orc.cloudvision.CVRequest;
import com.android.orc.cloudvision.CVResponse;
import com.android.orc.cloudvision.CloudVision;
import com.android.orc.ocrapplication.BuildConfig;
import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dashboard.DashBoardActivity;
import com.android.orc.ocrapplication.description.DescriptionActivity;
import com.android.orc.ocrapplication.result.ResultActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Headers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CloudVision.Callback{

    private final static String apiKey = "AIzaSyA7NoRiu-JttOEg2pJVGuw2jEnalNHRDKY";
    private static final int REQUEST_TAKE_PHOTO = 1;
    CVRequest.ImageContext.LatLongRect latLongRect;



    Button btnTakePhoto;
    Button btnProcessPhoto;
    ImageView ivPreview;
    String mCurrentPhotoPath;
    Bitmap bitmap;
    Intent CropIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initInstances();
        CameraActivityPermissionsDispatcher.startCameraWithCheck(this);
    }

    private void initInstances() {

        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnProcessPhoto = findViewById(R.id.btn_process_photo);
        ivPreview = findViewById(R.id.ivPreview);

        btnTakePhoto.setOnClickListener(this);
        btnProcessPhoto.setOnClickListener(this);
    }

    /////////////////////
    // OnClickListener //
    /////////////////////

    @Override
    public void onClick(View view) {
        if (view == btnTakePhoto) {
            CameraActivityPermissionsDispatcher.startCameraWithCheck(this);
        } else if (view == btnProcessPhoto) {

            startDetect();
//            Intent intent = new Intent(CameraActivity.this,
//                    ResultActivity.class);



//            Toast.makeText(this, data, Toast.LENGTH_LONG).show();
//            intent.putExtra("BitmapImage", data);
//            startActivity(intent);
        }
    }

    private void startDetect() {
        String data = CloudVision.convertBitmapToBase64String(bitmap);


        CVRequest request = createCVRequest(data);
        CloudVision.runImageDetection(apiKey, request, this);
    }

    ////////////
    // Camera //
    ////////////

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void startCamera() {
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Access to External Storage is required")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK
               ) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            getContentResolver().notifyChange(Uri.parse(mCurrentPhotoPath), null);
            ContentResolver cr = getContentResolver();
            // Show the thumbnail on ImageView
            Uri imageUri = result.getUri();
            File file = new File(imageUri.getPath());

            try {
                InputStream ims = new FileInputStream(file);
                ivPreview.setImageBitmap(BitmapFactory.decodeStream(ims));

                 bitmap = MediaStore.Images.Media.getBitmap(cr,imageUri);

//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

//                bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);



                //CODE BELOW USE WITH VISION CLOUD
//                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(file,imageUri);
            } catch (FileNotFoundException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(com.android.orc.ocrapplication.camera.CameraActivity.this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CameraActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }




    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(createImageFile());
                Uri photoURI = FileProvider.getUriForFile(com.android.orc.ocrapplication.camera.CameraActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                CropImage.activity(photoURI).start(this);




                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    @Override
    public void onImageDetectionSuccess(boolean isSuccess, int statusCode, Headers headers, CVResponse cvResponse) {
        setCVResponse(cvResponse);

    }

    @Override
    public void onImageDetectionFailure(Throwable t) {

    }

    private CVRequest createCVRequest(String data) {

        List<String> languageHints = new ArrayList<>();
        languageHints.add("th");

        CVRequest.Image image = new CVRequest.Image(data);
        CVRequest.ImageContext imageContext = new CVRequest.ImageContext(languageHints, latLongRect);
        CVRequest.Feature feature = new CVRequest.Feature(CVRequest.FeatureType.TEXT_DETECTION, 1);
        List<CVRequest.Feature> featureList = new ArrayList<>();
        featureList.add(feature);
        List<CVRequest.Request> requestList = new ArrayList<>();
        requestList.add(new CVRequest.Request(image, imageContext, featureList));
        return new CVRequest(requestList);


    }

    private void setCVResponse(CVResponse cvResponse) {
        if (cvResponse != null && cvResponse.isResponsesAvailable()) {
            CVResponse.Response response = cvResponse.getResponse(0);
            if (response.isTextAvailable()) {
                List<CVResponse.EntityAnnotation> testDao = response.getTexts();
                String data = testDao.get(0).getDescription();


                Intent intent = new Intent(CameraActivity.this,
                        DescriptionActivity.class);
                intent.putExtra("DAO", data);
                startActivity(intent);

//                textView.setText(testDao.get(0).getDescription());
//                LabelAdapter adapter = new LabelAdapter(response.getTexts());
//                lvLabel.setAdapter(adapter);
//                hideLoading();
            }
        }
    }
}
