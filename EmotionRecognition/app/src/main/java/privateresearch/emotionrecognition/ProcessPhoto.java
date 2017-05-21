package privateresearch.emotionrecognition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.utils.IntentUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import static android.R.id.message;

public class ProcessPhoto extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_photo);


        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;
            }
        });

//        imageSwitcher.setImageResource(image);
    }


    public void onImagesChosen(List<ChosenImage> images) {
        // Display Images
        Toast toast = Toast.makeText(getApplicationContext(), "Toast!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onError(String message) {
        // Handle error
    }


    /**
    private void handleMultipleShares() {
        if (type.startsWith("image")) {
            ImagePicker picker = new ImagePicker(this);
            picker.setImagePickerCallback(this);
            picker.submit(IntentUtils.getPickerIntentForSharing(getIntent()));
        }
    }**/



    public static void main(String a[]){
        try{
            String pythonScript = "C:\\Users\\Omkar\\AppData\\Local\\Programs\\Python\\Python35\\label_image.py";
            ProcessBuilder pb = new ProcessBuilder("python", pythonScript);
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine()).intValue();
            System.out.println("value is : "+ret);

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
