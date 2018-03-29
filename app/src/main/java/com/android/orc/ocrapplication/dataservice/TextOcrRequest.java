package com.android.orc.ocrapplication.dataservice;

import android.widget.TextView;

import com.android.orc.cloudvision.CVRequest;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by j.poobest on 19/2/2018 AD.
 */

public class TextOcrRequest {
    public TextOcrRequest(List<TextRequest> requests) {
    }

    public static class TextRequest {
        TextView textRequest;

        public TextRequest(TextView textRequest) {
             textRequest = this.textRequest;
        }


    }
}
