package com.android.orc.ocrapplication.result;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.android.orc.cloudvision.CVRequest;
import com.android.orc.cloudvision.CVResponse;
import com.android.orc.cloudvision.CloudVision;
import com.android.orc.ocrapplication.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ResultActivity extends AppCompatActivity implements CloudVision.Callback{
    private final static String apiKey = "AIzaSyA7NoRiu-JttOEg2pJVGuw2jEnalNHRDKY";


    CVRequest.ImageContext.LatLongRect latLongRect;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String name = intent.getStringExtra("DAO");

        textView = findViewById(R.id.tv_menu);
        textView.setText(name);

//        showLoading();
//        startDetect();
    }

    private void startDetect() {

        Intent intent = getIntent();
        String data = intent.getStringExtra("BitmapImage");

        textView = findViewById(R.id.tv_menu);
        textView.setText(data);

        CVRequest request = createCVRequest(data);
        CloudVision.runImageDetection(apiKey, request, this);

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
            if (response.isLabelAvailable()) {

            }
        }
    }

    public void showLoading() {
//        lvLabel.setVisibility(View.GONE);
//        pbLabel.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
//        lvLabel.setVisibility(View.VISIBLE);
//        pbLabel.setVisibility(View.GONE);
    }


    @Override
    public void onImageDetectionSuccess(boolean isSuccess, int statusCode, Headers headers, CVResponse cvResponse) {
        setCVResponse(cvResponse);
    }

    @Override
    public void onImageDetectionFailure(Throwable t) {

    }
}
