package privateresearch.emotionrecognition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessPhoto extends AppCompatActivity {

    public Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_photo);

        Intent intent = getIntent();
        image  = intent.getParcelableExtra("Photo");

        saveBitmap(image);
    }

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
    public static void saveBitmap(Bitmap photo) {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("Android/data/privateresearch.emotionrecognition/files/Pictures/photo.png");
            photo.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
