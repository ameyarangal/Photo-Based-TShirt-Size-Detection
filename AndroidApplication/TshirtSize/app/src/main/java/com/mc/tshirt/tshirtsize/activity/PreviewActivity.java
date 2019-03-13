package com.mc.tshirt.tshirtsize.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mc.tshirt.tshirtsize.R;
import com.mc.tshirt.tshirtsize.ds.MultipartUtility;
import com.mc.tshirt.tshirtsize.ds.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PreviewActivity extends AppCompatActivity {
    private static final String TAG = PreviewActivity.class.getName();
    private ImageView mShowPictureImageView;
    private Button mUploadImageButton;
    private String mCurrentPhotoPath;
//    private Switch mApiSwitch;
    private String calledURL;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initializeView();
    }

    private void initializeView(){
        mContext = this;
        mCurrentPhotoPath = getIntent().getStringExtra("image_path");
//        mApiSwitch = findViewById(R.id.api_swh);
        mUploadImageButton = findViewById(R.id.upload_image_btn);
        mShowPictureImageView = findViewById(R.id.show_picture_iv);

        calledURL = "http://mobilecomputing1.azurewebsites.net/get_size";
//        mApiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    calledURL = "http://mobilecomputing1.azurewebsites.net/get_size";
//                } else {
//                    calledURL = "http://192.168.1.70:1777/get_size";
//                }
//            }
//        });
//
//        mApiSwitch.setChecked(true);

        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utilities.isNetworkAvailable(mContext)) {
                    new UploadImagesAsyncTask(mCurrentPhotoPath, calledURL).execute();
                }
            }
        });


        File image = new File(mCurrentPhotoPath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        mShowPictureImageView.setImageBitmap(bitmap);


    }


    private class UploadImagesAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        private String mPhotoFilePath;
        private String mCalledURL;

        UploadImagesAsyncTask(String mPhotoFilePath, String mCalledURL){
            this.mPhotoFilePath = mPhotoFilePath;
            this.mCalledURL = mCalledURL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PreviewActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            return uploadMedia(mPhotoFilePath, mCalledURL);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            if (response != null) {
//                Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG).show();
                Log.i(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Tshirtsize").equalsIgnoreCase("S")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("M")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("L")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("XL")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("XXL")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("XXXL")
                            || jsonObject.getString("Tshirtsize").equalsIgnoreCase("XXXXL")){
                        goToSuggestionsActivity(jsonObject.getString("Tshirtsize"), mPhotoFilePath);
                    } else {
                        openDialog("Tshirt Not Detected. Please Retake Image.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                openDialog("Image sending failed.");
            }
        }
    }


    private String uploadMedia(String mPhotoFilePath, String requestURL) {
        try {

            String charset = "UTF-8";
            File uploadFile1 = new File(mPhotoFilePath);
//            String requestURL = "http://192.168.1.70:1777/get_size";

            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addFilePart("file", uploadFile1);
            Log.v("rht", "before response");
//            multipart.addFormField("orientation", String.valueOf(orientation));

            String response = multipart.finish();

            Log.v("rht", "SERVER REPLIED:" + response);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openDialog(final String mMessageString) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_dialog);
        TextView mMessageContentView = dialog.findViewById(R.id.message_dialog_tv);
        mMessageContentView.setText(mMessageString);
        dialog.show();
        Button mBackToDashboardButton = dialog.findViewById(R.id.dialog_done_btn);
        mBackToDashboardButton.setVisibility(View.VISIBLE);

        mBackToDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void goToSuggestionsActivity(String mTshirtSize, String mCurrentPhotoPath){
        Intent intent = new Intent(PreviewActivity.this, SuggestionsActivity.class);
        intent.putExtra("tshirt_size", mTshirtSize);
        intent.putExtra("file_path", mCurrentPhotoPath);
        startActivity(intent);
    }
}
