package privateresearch.emotionrecognition;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.path;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {


    private static final int PICK_PHOTO_FOR_AVATAR = 1;
    private int MY_PERMISSIONS_REQUEST_CAMERA;
    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    private static boolean cameraPermission;
    private static boolean readStorage;
    private int RESULT_LOAD_IMAGE;
    private int PICK_IMAGE_REQUEST;
    private ImageView imageView;
    ImagePicker imagePicker;
    CameraImagePicker imagePicker2;
    private ImagePickerCallback imagePickerCallback;
    public String outputPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Toast toast = Toast.makeText(getApplicationContext(), "Toast!", Toast.LENGTH_SHORT);
        toast.show();*/
    }

    /** Called when the user presses the "Use Camera" button **/
    public void pictureUseCamera (View view) {
        Intent intent = new Intent(this, TakePhoto.class);
        /**
        private boolean checkCameraHardware(Context context) {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                // this device has a camera
                return true;
            } else {
                // no camera on this device
                return false;
            }
        }*/
        checkCameraPermission();

        openApp(this, "org.tensorflow.demo");
        /**
        CameraImagePicker imagePicker2 = new CameraImagePicker(this);
        imagePicker2.setImagePickerCallback(new ImagePickerCallback(){
            @Override
            public void onImagesChosen(List<ChosenImage> images) {
                // Display images
            }

            @Override
            public void onError(String message) {
                // Do error handling
            }}
        );**/
// imagePicker.shouldGenerateMetadata(false); // Default is true
// imagePicker.shouldGenerateThumbnails(false); // Default is true
        outputPath = imagePicker2.pickImage();
    }

    public void pictureFromFile (View view) {
        imagePicker = new ImagePicker(this);
        imagePicker.setImagePickerCallback(new ImagePickerCallback(){
            @Override
            public void onError(String message) {
                // Do error handling
            }

            @Override
            public void onImagesChosen(List<ChosenImage> images) {
                // Display images
            }
        }
        );
// imagePicker.allowMultiple(); // Default is false
// imagePicker.shouldGenerateMetadata(false); // Default is true
// imagePicker.shouldGenerateThumbnails(false); // Default is true
        imagePicker.pickImage();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                if(imagePicker == null) {
                    imagePicker = new ImagePicker(this);
                    
                    imagePicker.setImagePickerCallback(imagePickerCallback);
                }
                imagePicker.submit(data);
            }
            if(requestCode == Picker.PICK_IMAGE_CAMERA) {
                if(imagePicker2 == null) {
                    imagePicker2 = new CameraImagePicker(this);
                    imagePicker2.reinitialize(outputPath);
                    // OR in one statement
                    // imagePicker = new CameraImagePicker(Activity.this, outputPath);
                    imagePicker2.setImagePickerCallback(imagePickerCallback);
                }
                imagePicker2.submit(data);
            }
        }
    }














    public static void openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();

            Intent i = manager.getLaunchIntentForPackage(packageName);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);

        }



    public void checkFileReadPermission() {
        int permissionCheckReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckReadPermission == PERMISSION_GRANTED) {
            readStorage = true;

        } else if (permissionCheckReadPermission != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }
    }

    public void checkCameraPermission() {
        int permissionCheckCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissionCheckCamera == PERMISSION_GRANTED) {
            cameraPermission = true;

        } else if (permissionCheckCamera != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        }

    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                cameraPermission = false;
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
        outState.putString("picker_path", outputPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // After Activity recreate, you need to re-initialize these
        // two values to be able to re-initialize CameraImagePicker
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("picker_path")) {
                outputPath = savedInstanceState.getString("picker_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}
