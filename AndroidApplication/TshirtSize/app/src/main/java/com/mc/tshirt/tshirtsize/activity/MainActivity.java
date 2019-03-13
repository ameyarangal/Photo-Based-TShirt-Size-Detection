package com.mc.tshirt.tshirtsize.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mc.tshirt.tshirtsize.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = MainActivity.class.getName();
    private Context mContext;
    private TextView mStepsTextView;
    private Button mStartCameraButton;
    private Button mGetImageButton;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 101;
    private static final int REQUEST_READ_IMAGE = 102;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 20;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 30;

    //    private ImageView mTempImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    private void initializeView(){
        mContext = this;
        mStepsTextView = findViewById(R.id.steps_tv);
        mStartCameraButton = findViewById(R.id.start_camera_btn);
        mGetImageButton = findViewById(R.id.get_img_btn);
//        mTempImageView = findViewById(R.id.temp_iv);


        mStepsTextView.setText("Want to measure your T-shirt Size?\n" +
                " Upload a picture of your shirt and find its size.\n" +
                "\nSteps : \n\n" + "1. Take a photo of the backside of a plain T-shirt.\n" +
                "2. Place one Dollar currency note above the neck region.\n" +
                "3. Fold the sleeves inside if they are too long.\n" +
                "4. Make sure that the background is plain.\n" +
                "\nOR \n\n" +
                "Upload an existing photo that satisfies the above requirements.");

        mStartCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPhotoPath = null;
                if (isCameraPermissionGranted()) {
                    dispatchTakePictureIntent();
                }
            }
        });

        mGetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPhotoPath = null;
                if (isReadStoragePermissionGranted()) {
                    if (isWriteStoragePermissionGranted()) {
                        goToGetImageActivity();
                    }
                }
            }
        });
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public  boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    private void goToGetImageActivity(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_READ_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    isWriteStoragePermissionGranted();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mContext, "Permission to read images not granted", Toast.LENGTH_LONG).show();

                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    goToGetImageActivity();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mContext, "Permission to write images not granted", Toast.LENGTH_LONG).show();

                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dispatchTakePictureIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mContext, "Permission to capture images not granted", Toast.LENGTH_LONG).show();

                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.i(TAG, "current abs path = " + mCurrentPhotoPath);
//            Toast.makeText(mContext, "path = " + mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
            goToPreviewActivity(mCurrentPhotoPath);

        } else if (requestCode == REQUEST_READ_IMAGE && resultCode == RESULT_OK){
            Uri uri;
            if (intent != null) {
                uri = intent.getData();
//                Log.i(TAG, "uri path = " + uri.getPath());
//                    mCurrentPhotoPath = PathUtil.getPath(mContext, uri);
                try {
                    Bitmap bitmap = getBitmapFromUri(uri);

//                    mTempImageView.setImageBitmap(bitmap);

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp_img.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    mCurrentPhotoPath = file.getAbsolutePath();

                    Log.i(TAG, "current abs path = " + mCurrentPhotoPath);
//                    Toast.makeText(mContext, "path = " + mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
                    goToPreviewActivity(mCurrentPhotoPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void goToPreviewActivity(String mCurrentImagePath){
        if (null != mCurrentPhotoPath && !mCurrentPhotoPath.isEmpty()){
            Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
            intent.putExtra("image_path",mCurrentImagePath);
            startActivity(intent);
        }
    }
}
