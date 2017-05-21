package privateresearch.emotionrecognition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessPhoto extends AppCompatActivity {

    public Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_photo);

        Intent intent = getIntent();
        image  = intent.getParcelableExtra("Photo");
    }

    public static void main(String a[]){
        try{
            String pythonScript = "label_image.py";
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
